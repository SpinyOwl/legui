package org.liquidengine.legui.util;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Shcherbin Alexander on 6/3/2016.
 */
public final class NvgRenderUtils {
    private NvgRenderUtils() {
    }

    public static void renderTextStateMultilineToBounds(long nvgContext, Vector2f pos, Vector2f size, TextState textState) {

        renderTextMultilineToBounds(nvgContext,
                pos.x, pos.y, size.x, size.y,
                textState.getFontSize(),
                textState.getFont(),
                textState.getTextColor(),
                NVGColor.calloc(),
                textState.getText(),
                textState.getHorizontalAlign(),
                textState.getVerticalAlign());

    }

    public static void renderTextStateLineToBounds(long nvgContext, Vector2f pos, Vector2f size, TextState textState) {
        renderTextStateLineToBounds(nvgContext, pos, size, textState, true);
    }

    public static void renderTextStateLineToBounds(long nvgContext, Vector2f pos, Vector2f size, TextState textState, boolean hide) {
        Vector4f pad = textState.getPadding();
        String font = textState.getFont() == null ? FontRegister.DEFAULT : textState.getFont();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign verticalAlign = textState.getVerticalAlign();
        NvgRenderUtils.renderTextLineToBounds(nvgContext,
            /* X position             */ pos.x + 0.5f + pad.x,
            /* Y position             */ pos.y + pad.y,
            /* Width                  */ size.x - pad.x - pad.z,
            /* Height                 */ size.y - pad.y - pad.w,
            /* font size              */ textState.getFontSize(),
            /* font name              */font,
            /* text color             */ textState.getTextColor(),
            /* text to render         */ textState.getText(),
            /* horizontal alignment   */ horizontalAlign,
            /* vertical alignment     */ verticalAlign,
            /* hide out of bound text */ hide);
    }

    /**
     * Used to renderNvg textState to rectangle bounds
     *
     * @param context         nanovg context
     * @param x               x position of rectangle
     * @param y               y position of rectangle
     * @param w               width of rectangle
     * @param h               height of rectangle
     * @param fontSize        titleFont size
     * @param font            titleFont name which contains in titleFont register
     * @param textColor       textState color
     * @param text            textState
     * @param horizontalAlign horizontal align
     * @param verticalAlign   vertical align
     * @param hide            true if need to hide out of bounds textState
     */
    public static void renderTextLineToBounds(long context,
                                              float x, float y, float w, float h,
                                              float fontSize,
                                              String font,
                                              Vector4f textColor,
                                              String text,
                                              HorizontalAlign horizontalAlign,
                                              VerticalAlign verticalAlign, boolean hide) {
        NVGColor colorA = NVGColor.create();
        renderTextLineToBounds(context, x, y, w, h, fontSize, font, textColor, colorA, text, horizontalAlign, verticalAlign, hide);
    }


    /**
     * Used to renderNvg textState to rectangle bounds
     *
     * @param context         nanovg context
     * @param x               x position of rectangle
     * @param y               y position of rectangle
     * @param w               width of rectangle
     * @param h               height of rectangle
     * @param fontSize        titleFont size
     * @param font            titleFont name which contains in titleFont register
     * @param textColor       textState color
     * @param nvgColor        nvg textState color
     * @param text            textState
     * @param horizontalAlign horizontal align
     * @param verticalAlign   vertical align
     * @param hide            true if need to hide out of bounds textState
     */
    public static void renderTextLineToBounds(long context,
                                              float x, float y, float w, float h,
                                              float fontSize,
                                              String font,
                                              Vector4f textColor,
                                              NVGColor nvgColor,
                                              String text,
                                              HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, boolean hide) {
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);

        ByteBuffer byteText = MemoryUtil.memUTF8(text);
        alignTextInBox(context, horizontalAlign, verticalAlign);

        if (hide) {
            long start = memAddress(byteText);
            long end = start + byteText.remaining();

            NVGTextRow.Buffer buffer = NVGTextRow.create(1);
            nnvgTextBreakLines(context, start, end, w, memAddress(buffer), 1);
            NVGTextRow row = buffer.get(0);
            float[] bounds = createBounds(x, y, w, h, horizontalAlign, verticalAlign, row.width(), fontSize);

            nvgBeginPath(context);
            NVGColor textColorN = textColor.w == 0 ? rgba(0.0f, 0.0f, 0.0f, 1f, nvgColor) : rgba(textColor, nvgColor);
            nvgFillColor(context, textColorN);
            nnvgText(context, bounds[0], bounds[1], row.start(), row.end());
        } else {
            float[] bounds = calculateTextBoundsRect(context, x, y, w, h, text, 0, horizontalAlign, verticalAlign);

            NVGColor textColorN = textColor.w == 0 ? rgba(0.0f, 0.0f, 0.0f, 1f, nvgColor) : rgba(textColor, nvgColor);
            nvgFillColor(context, textColorN);
            nvgText(context, bounds[0], bounds[1], byteText, 0);
        }

    }

    /**
     * Used to renderNvg textState to rectangle bounds
     *
     * @param context         nanovg context
     * @param x               x position of rectangle
     * @param y               y position of rectangle
     * @param w               width of rectangle
     * @param h               height of rectangle
     * @param fontSize        titleFont size
     * @param font            titleFont name which contains in titleFont register
     * @param textColor       textState color
     * @param nvgColor        nvg textState color
     * @param text            textState
     * @param horizontalAlign horizontal align
     * @param verticalAlign   vertical align
     */
    public static void renderTextMultilineToBounds(long context,
                                                   float x, float y, float w, float h,
                                                   float fontSize,
                                                   String font,
                                                   Vector4f textColor,
                                                   NVGColor nvgColor,
                                                   String text,
                                                   HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);

        ByteBuffer byteText = MemoryUtil.memUTF8(text);
        alignTextInBox(context, horizontalAlign, verticalAlign);

        float[] lineh = {0};
        nvgTextMetrics(context, null, null, lineh);

        long start = memAddress(byteText);
        long end = start + byteText.remaining();

        int maxRows = (int) Math.ceil((double) h / lineh[0]);

        NVGTextRow.Buffer buffer = NVGTextRow.create(maxRows);
        int nrows;
        int rowNum = 0;
        while ((nrows = nnvgTextBreakLines(context, start, end, w, memAddress(buffer), maxRows)) != 0) {
            for (int i = 0; i < nrows; i++) {
                NVGTextRow row = buffer.get(i);
                float[] bounds = createBounds(x, y, w, h, horizontalAlign, verticalAlign, row.width(), fontSize, rowNum, nrows);
                nvgBeginPath(context);
                NVGColor textColorN = textColor.w == 0 ? rgba(0.0f, 0.0f, 0.0f, 1f, nvgColor) : rgba(textColor, nvgColor);
                nvgFillColor(context, textColorN);
                nnvgText(context, bounds[0], bounds[1], row.start(), row.end());

//                y += lineh[0];
                rowNum++;
            }
            start = buffer.get(nrows - 1).next();
        }
    }

    public static void drawRectangle(long context, Vector4f color, float x, float y, float w, float h) {
        nvgBeginPath(context);
        nvgFillColor(context, rgba(color, NVGColor.create()));
        nvgRect(context, x, y, w, h);
        nvgFill(context);
    }

    /**
     * Used to renderNvg textState to rectangle bounds
     *
     * @param context         nanovg context
     * @param x               x position of rectangle
     * @param y               y position of rectangle
     * @param w               width of rectangle
     * @param h               height of rectangle
     * @param fontSize        titleFont size
     * @param font            titleFont name which contains in titleFont register
     * @param textColor       textState color
     * @param text            textState
     * @param horizontalAlign horizontal align
     * @param verticalAlign   vertical align
     */
    public static void renderTextLineToBounds(long context, float x, float y, float w, float h, float fontSize,
                                              String font, Vector4f textColor, String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        renderTextLineToBounds(context, x, y, w, h, fontSize, font, textColor, text, horizontalAlign, verticalAlign, true);
    }


    public static float[] calculateTextBoundsRect(long context, float x, float y, float w, float h, String text, long end, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        float bounds[] = new float[4];
        nvgTextBounds(context, x, y, text, end, bounds);
        return createBounds(w, h, horizontalAlign, verticalAlign, bounds);
    }

    public static float[] calculateTextBoundsRect(long context, float x, float y, float w, float h, ByteBuffer text, long end, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        float bounds[] = new float[4];
        nvgTextBounds(context, x, y, text, end, bounds);
        return createBounds(w, h, horizontalAlign, verticalAlign, bounds);
    }


    public static float[] createBounds(float x, float y, float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float width, float fontSize, int rowIndex, int nrows) {
        return createBounds(x, y + rowIndex * fontSize, w, h - (nrows - 1) * fontSize, horizontalAlign, verticalAlign, width, fontSize);
    }

    public static float[] createBounds(float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float[] bounds) {
        float ww = bounds[2] - bounds[0];
        float hh = bounds[3] - bounds[1];
        return createBounds(w, h, horizontalAlign, verticalAlign, bounds, ww, hh);
    }

    public static float[] createBounds(float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float[] bounds, float ww, float hh) {
        int hp = horizontalAlign == HorizontalAlign.LEFT ? 0 : horizontalAlign == HorizontalAlign.CENTER ? 1 : 2;
        int vp = verticalAlign == VerticalAlign.TOP ? 0 : verticalAlign == VerticalAlign.MIDDLE ? 1 : verticalAlign == VerticalAlign.BOTTOM ? 2 : 3;
        float x1 = bounds[0] + (w + ww) * 0.5f * hp;

        float baseline = (vp > 2 ? hh / 4.0f : 0);
        float vv = (vp == 3 ? 1 : vp);
        float y1 = bounds[1] + (h + hh) * 0.5f * vv + (vp > 2 ? (+baseline) : 0);
        return new float[]{x1, y1, ww, hh,
                x1 - (ww * 0.5f * hp), y1 - (hh * 0.5f * vv) - baseline, ww, hh};
    }

    public static float[] createBounds(float x, float y, float w, float h, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, float tw, float th) {
        int hp = horizontalAlign == HorizontalAlign.LEFT ? 0 : horizontalAlign == HorizontalAlign.CENTER ? 1 : 2;
        int vp = verticalAlign == VerticalAlign.TOP ? 0 : verticalAlign == VerticalAlign.MIDDLE ? 1 : verticalAlign == VerticalAlign.BOTTOM ? 2 : 3;

        float x1 = x + w * 0.5f * hp;

        float baseline = (vp > 2 ? th / 4.0f : 0);
        float vv = (vp == 3 ? 1 : vp);
        float y1 = y + h * 0.5f * vv + (vp > 2 ? (+baseline) : 0);
        return new float[]{x1, y1, tw, th,
                x1 - (tw * 0.5f * hp), y1 - (th * 0.5f * vv) - baseline, tw, th};
    }


    public static void alignTextInBox(long context, HorizontalAlign hAlig, VerticalAlign vAlig) {
        int hAlign = hAlig == HorizontalAlign.CENTER ? NVG_ALIGN_CENTER : hAlig == HorizontalAlign.LEFT ? NVG_ALIGN_LEFT : NVG_ALIGN_RIGHT;
        int vAlign = vAlig == VerticalAlign.TOP ? NVG_ALIGN_TOP : vAlig == VerticalAlign.BOTTOM ?
                NVG_ALIGN_BOTTOM : vAlig == VerticalAlign.MIDDLE ? NVG_ALIGN_MIDDLE : NVG_ALIGN_BASELINE;
        nvgTextAlign(context, hAlign | vAlign);
    }

    public static void drawRectStroke(long context, float x, float y, float w, float h, Vector4f strokeColor, float borderRadius, float strokeWidth) {
        nvgBeginPath(context);
        nvgStrokeWidth(context, strokeWidth);
        nvgRoundedRect(context, x, y, w, h, borderRadius);
        NVGColor color = NVGColor.create();
        nvgStrokeColor(context, rgba(strokeColor, color));
        nvgStroke(context);
    }

    public static void dropShadow(long context, float x, float y, float w, float h, float cornerRadius, Vector4f shadowColor) {
        NVGPaint shadowPaint = NVGPaint.create();
        NVGColor colorA = NVGColor.create();
        NVGColor colorB = NVGColor.create();

        nvgBoxGradient(context, x, y + 2, w, h, cornerRadius * 2, 10, rgba(shadowColor, colorA), rgba(0, 0, 0, 0, colorB), shadowPaint);
        nvgBeginPath(context);
        nvgRect(context, x - 10, y - 10, w + 20, h + 30);
        nvgRoundedRect(context, x, y, w, h, cornerRadius);
        nvgPathWinding(context, NVG_HOLE);
        nvgFillPaint(context, shadowPaint);
        nvgFill(context);
    }


    /**
     * Creates scissor for provided component by it's parent components
     *
     * @param context nanovg context
     * @param gui     {@link Component}
     */
    public static void createScissor(long context, Component gui) {
        Component parent = gui.getParent();
        createScissorByParent(context, parent);
    }

    /**
     * Creates scissor by provided component and it's parent components
     *
     * @param context nanovg context
     */
    public static void createScissorByParent(long context, Component parent) {
        if (parent != null) {
            Vector2f p = calculatePosition(parent);
            Vector2f s = new Vector2f(parent.getSize());
            nvgScissor(context, p.x, p.y, s.x, s.y);

            while ((parent = parent.getParent()) != null) {
                p = calculatePosition(parent);
                s = parent.getSize();
                nvgIntersectScissor(context, p.x, p.y, s.x, s.y);
            }
        }
    }

    /**
     * Resets scissor
     *
     * @param context
     */
    public static void resetScissor(long context) {
        nvgResetScissor(context);
    }

    public static void renderBorder(Component component, LeguiContext leguiContext) {
        Border border = component.getBorder();
        if (border != null) {
            if (border.isEnabled()) {
                border.render(leguiContext, component);
            }
        }
    }
}
