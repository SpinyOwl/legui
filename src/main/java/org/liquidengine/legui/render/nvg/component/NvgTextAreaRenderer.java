package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.ColorConstants;
import org.liquidengine.legui.util.NVGUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Alexander on 09.10.2016.
 */
public class NvgTextAreaRenderer extends NvgLeguiComponentRenderer {
    public static final Vector4f BLUE = ColorConstants.lightBlue();
    public static final Vector4f BLACK = ColorConstants.black();
    public static final Vector4f RED = ColorConstants.red();
    private NVGColor colorA = NVGColor.calloc();
    private final int maxGlyphCount = 2048;
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            TextArea agui = (TextArea) component;
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();
            float br = agui.getCornerRadius();
            Vector4f bc = new Vector4f(agui.getBackgroundColor());

            drawBackground(context, pos.x, pos.y, size.x, size.y, br, bc);

            TextState textState = agui.getTextState();
            renderTextMultilineToBounds(context,
                    pos.x, pos.y, size.x, size.y,
                    textState.getFontSize(),
                    textState.getFont(),
                    textState.getTextColor(),
                    NVGColor.calloc(),
                    textState.getText(),
                    textState.getHorizontalAlign(),
                    textState.getVerticalAlign(), agui.getCaretPosition());

            Border border = agui.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }
        }
        resetScissor(context);
    }

    private void renderTextMultilineToBounds(long context,
                                             float x, float y, float w, float h,
                                             float fontSize,
                                             String font,
                                             Vector4f textColor,
                                             NVGColor nvgColor,
                                             String text,
                                             HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, int caretPosition) {
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);

        ByteBuffer byteText = MemoryUtil.memUTF8(text);
        alignTextInBox(context, horizontalAlign, verticalAlign);

        float[] lineh = {0};
        nvgTextMetrics(context, null, null, lineh);

        long start = memAddress(byteText);
        long end = start + byteText.remaining();

        int maxRows = (int) Math.floor((double) h / lineh[0]);

        NVGTextRow.Buffer buffer = NVGTextRow.create(maxRows);
        int nrows = 0;
        long glyphAddress = memAddress(glyphs);
        int cnt = 0;
        int rownum = 0;
        int glyphCount = 0;
        NVGTextRow nvgTextRow = null;

        while (cnt < caretPosition && (nrows = nnvgTextBreakLines(context, start, end, w, memAddress(buffer), maxRows)) != 0) {
            for (int i = 0; i < nrows && cnt < caretPosition; i++) {
                nvgTextRow = buffer.get(i);
                glyphCount = nnvgTextGlyphPositions(context, 0, 0, nvgTextRow.start(), nvgTextRow.end(), glyphAddress, maxGlyphCount);
                cnt += glyphCount;
                if (cnt < caretPosition) rownum++;
            }
            start = buffer.get(nrows - 1).next();
        }

        float caretx;

        int cpos = caretPosition + glyphCount - cnt;
        if (cpos < glyphCount) {
            caretx = glyphs.get(cpos).x();
        } else {
            if (glyphCount > 0) caretx = glyphs.get(glyphCount - 1).maxx();
            else caretx = x + horizontalAlign.index * w / 2;
        }

        int rowNum = 0;
        NVGColor textColorN = textColor.w == 0 ? rgba(0.0f, 0.0f, 0.0f, 1f, nvgColor) : rgba(textColor, nvgColor);
        start = memAddress(byteText);
        end = start + byteText.remaining();
        nrows = nnvgTextBreakLines(context, start, end, w, memAddress(buffer), maxRows);
        for (int i = 0; i < nrows; i++) {
            NVGTextRow row = buffer.get(i);
            float[] bounds = createBounds(x, y, w, h, horizontalAlign, verticalAlign, row.width(), fontSize, rowNum, nrows);
            if (rowNum == rownum) {
                drawRectangle(context, BLUE, bounds[4], bounds[5], bounds[6], bounds[7]);
            }
            nvgBeginPath(context);
            nvgFillColor(context, textColorN);
            nnvgText(context, bounds[0], bounds[1], row.start(), row.end());


            // draw caret
            if (rowNum == rownum) {
                if (nvgTextRow != null) {
                    drawRectangle(context, RED, x + caretx, bounds[5], 1, bounds[7]);
                }
            }
            rowNum++;
        }


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
}
