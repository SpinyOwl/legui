package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NVGUtils;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.component.theme.Theme.DEFAULT_THEME;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Shcherbin Alexander on 5/20/2016.
 */
public class NvgTextInputRenderer extends NvgLeguiComponentRenderer {
    private final Vector4f caretColor = new Vector4f(0, 0, 0, 0.5f);
    private final int maxGlyphCount = 1024;
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);
    private NVGColor colorA = NVGColor.create();
    private long glyphAddress = memAddress(glyphs);

    @Override
    public void initialize() {

    }

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            TextInput agui = (TextInput) component;
            Vector2f pos = Util.calculatePosition(component);
            float x = pos.x;
            float y = pos.y;
            float w = component.getSize().x;
            float h = component.getSize().y;
            float br = agui.getCornerRadius();
            boolean enabled = agui.isEnabled();

            TextState textState = agui.getTextState();
            HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
            VerticalAlign verticalAlign = textState.getVerticalAlign();

            Vector4f bc = new Vector4f(agui.getBackgroundColor());
            Vector4f strokeColorDark = new Vector4f(DEFAULT_THEME.getStrokeColorDark());
            Vector4f strokeColorLight = new Vector4f(DEFAULT_THEME.getStrokeColorLight());

            float fontSize = textState.getFontSize();
            String font = textState.getFont() == null ? FontRegister.DEFAULT : textState.getFont();
            Vector4f textColor = textState.getTextColor();
            Vector4f pad = new Vector4f(textState.getPadding());
            float tx = x + pad.x;
            float ty = y + pad.y;
            float tw = w - pad.x - pad.z;
            float th = h - pad.y - pad.w;

            if (enabled && agui.isFocused()) {
                bc.w *= 1.1f;
            } else if (!enabled) {
                bc.w *= 0.5f;
            }
            if (!agui.isEditable()) {
                bc.w *= 0.3f;
            }

            drawBackground(context, x, y, w, h, br, bc);
//            drawBorder(context, x, y, w, h, br, strokeColorDark, strokeColorLight);

            Border border = agui.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }

            String text;
            int caretPos = agui.getCaretPosition();
            nvgFontFace(context, font);
            nvgFontSize(context, fontSize);
            int renderBounds[] = new int[2];

            String textToDraw = null;
            try {
                text = updateRenderedText(agui, context, tw, renderBounds);
                textToDraw = text.substring(renderBounds[0], renderBounds[1]);
            } catch (Throwable e) {
            }
            if (textToDraw == null) return;
            ByteBuffer byteText = MemoryUtil.memUTF8(textToDraw);

            // draw text
            NvgRenderUtils.alignTextInBox(context, horizontalAlign, verticalAlign);
            float bounds[] = NvgRenderUtils.calculateTextBoundsRect(context, tx, ty, tw, th, textToDraw, 0, horizontalAlign, verticalAlign);
            nvgBeginPath(context);
            NVGColor textColorN = textColor.w == 0 ? NVGUtils.rgba(0.0f, 0.0f, 0.0f, 1f, colorA) : NVGUtils.rgba(textColor, colorA);
            nvgFillColor(context, textColorN);
            nvgText(context, bounds[0], bounds[1], textToDraw, 0);

            int ng = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(byteText), 0, glyphAddress, maxGlyphCount);
            // draw caret
            if (agui.isFocused()) {
                calcMouseCaretPos(leguiContext, bounds, ng, agui, renderBounds);
                drawCaret(context, horizontalAlign, bc, tx, tw, bounds, byteText, caretPos - renderBounds[0], ng);
            }
        }
        resetScissor(context);
    }


    public String updateRenderedText(TextInput agui, long context, float tw, int[] renderBounds) {
        String text = agui.getTextState().getText();
        ByteBuffer byteText = MemoryUtil.memUTF8(text);
        long glyphAddress = memAddress(glyphs);
        glyphs.clear();
        NvgRenderUtils.alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
        int nGlypths = nnvgTextGlyphPositions(context, 0, 0, memAddress(byteText), 0, glyphAddress, maxGlyphCount);
        int caretPos = agui.getCaretPosition();

        int l = 0, r = nGlypths > 0 ? nGlypths : -1;

        // get text to render depending on cursor position
        int textBounds[] = new int[2];
        if (agui.isFocused()) {

            if (nGlypths == 0) {
                renderBounds[0] = 0;
                renderBounds[1] = 0;
                return text;
            }

            float wid = 0;
            int lb = caretPos;
            int rb = caretPos;
            while (wid <= tw && !(lb == 0 && rb == nGlypths)) {
                if (lb > 0) lb--;
                if (rb < nGlypths) rb++;

                float left = glyphs.get(lb).x();
                float right = (rb == nGlypths ? glyphs.get(rb - 1).maxx() : glyphs.get(rb).x());
                wid = right - left;
            }
            renderBounds[0] = lb;
            renderBounds[1] = rb;
        } else {
            getTextToDrawBounds(tw, nGlypths, r, textBounds);
            renderBounds[0] = textBounds[0];
            renderBounds[1] = textBounds[1] + 1;
        }
        return text;
    }


    private void drawCaret(long context, HorizontalAlign horizontalAlign, Vector4f bc, float tx, float tw, float[] bounds, ByteBuffer byteText,
                           int currCaretPos, int ng) {
        float caretx;

        if (currCaretPos < ng) {
            caretx = glyphs.get(currCaretPos).x();
        } else {
            if (ng > 0)
                caretx = glyphs.get(ng - 1).maxx();
            else {
                caretx = tx + horizontalAlign.index * tw / 2f;
            }
        }
        Util.negativeColor(bc, caretColor);
        caretColor.w = (float) Math.abs(GLFW.glfwGetTime() % 1 * 2 - 1);

        NvgRenderUtils.drawRectangle(context, caretColor, (int) caretx, bounds[5], 1f, bounds[7]);
    }

    private void calcMouseCaretPos(LeguiContext renderContext, float[] bounds, int ng, TextInput gui, int[] textBounds) {
//        float mouseCaretx = 0;
        Vector2f cursorPosition = renderContext.getCursorPosition();

        double xpo = cursorPosition.x;
        float mx = (float) xpo;
        float fl = bounds[4];
        float fr = bounds[6] + bounds[4];
        int newCPos = 0;
        if (mx <= fl) {
            newCPos = 0;
        } else if (mx >= fr) {
            newCPos = ng;
        } else {
            int upper = ng - 1;
            for (int i = 0; i < upper; newCPos = i++) {
                float x = glyphs.get(i).x();
                float maxx = glyphs.get(i + 1).x();

                if (mx >= x && mx <= maxx) {
                    if (maxx - mx > mx - x) {
                        newCPos = i;
                    } else {
                        newCPos = i + 1;
                    }
                    break;
                }
            }

            // check also last symbol
            if (upper > 0) {
                float x = glyphs.get(upper).x();
                float maxx = glyphs.get(upper).maxx();

                if (mx >= x && mx <= maxx) {
                    if (maxx - mx > mx - x) {
                        newCPos = upper;
                    } else {
                        newCPos = upper + 1;
                    }
                }
            }
        }
        gui.setMouseCaretPosition(newCPos + textBounds[0]);
    }

    private void drawBackground(long context, float x, float y, float w, float h, float br, Vector4f bc) {
        if (bc.w != 0) {
            nvgSave(context);
            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, br);
            nvgFillColor(context, NVGUtils.rgba(bc, colorA));
            nvgFill(context);
        }
    }

    @Override
    public void destroy() {

    }

    private void getTextToDrawBounds(float tw, int nGlypths, int r, int[] textBounds) {
        textBounds[0] = 0;
        textBounds[1] = getR(tw, nGlypths);
    }

    private int getR(float tw, int nGlypths) {
        int low = 0, high = nGlypths - 1, mid = 0;
        while (high - low > 1) {
            mid = (low + high) >>> 1;
            if (tw < glyphs.get(mid).maxx()) {
                high = mid;
            } else {
                low = mid;
            }
        }
        int r;
        if (glyphs.get(mid).maxx() > tw) r = low;
        else r = high;
        return r;
    }

}
