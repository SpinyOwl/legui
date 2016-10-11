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
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGGlyphPosition;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Alexander on 09.10.2016.
 */
public class NvgTextAreaRenderer extends NvgLeguiComponentRenderer {
    public static final Vector4f BLUE = ColorConstants.lightBlue();
    public static final Vector4f BLACK = ColorConstants.black();
    public static final Vector4f RED = ColorConstants.red();
    private final int maxGlyphCount = 2048;
    private NVGColor colorA = NVGColor.calloc();
    private NVGGlyphPosition.Buffer glyphs = NVGGlyphPosition.create(maxGlyphCount);
    private NVGColor color = NVGColor.calloc();

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
            Vector4f p = textState.getPadding();

//            renaderTextMultilineToBounds(context, pos.x, pos.y, size.x, size.y, textState.getFontSize(), textState.getFont(),
//                    textState.getTextColor(), color, textState.getText(), textState.getHorizontalAlign(), textState.getVerticalAlign(), agui.getCaretPosition());

            Border border = agui.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }

            nvgIntersectScissor(context, pos.x + p.x, pos.y + p.y, size.x - (p.x + p.z), size.y - (p.y + p.w));
            renderText(context, agui, pos, size, textState, agui.getCaretPosition());


        }
        resetScissor(context);
    }

    private void renderText(long context, TextArea agui, Vector2f pos, Vector2f size, TextState textState, int caretPosition) {
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        int caretLine = 0;
        int caretOffset = 0;
        for (String line : lines) {
            int newOffset = caretOffset + line.length();
            if (newOffset >= caretPosition) {
                break;
            }
            caretLine++;
            caretOffset = newOffset + 1;
        }

        int lineCaretPosition = caretPosition - caretOffset;

        Vector4f p = textState.getPadding();
        float x = pos.x + p.x;
        float y = pos.y + p.y;
        float w = size.x - p.x - p.z;
        float h = size.y - p.y - p.w;
        float fontSize = textState.getFontSize();
        int lineCount = lines.length;

        int maxLines = (int) Math.floor(h / fontSize);
        int topIndex = caretLine;
        int botIndex = caretLine;
        int diff = 0;

        while (diff < maxLines - 1 && !(topIndex == 0 && botIndex == lineCount)) {
            if (topIndex > 0) topIndex--;
            if (botIndex < lineCount-1) botIndex++;
            diff = botIndex - topIndex;
        }

        String font = textState.getFont();
        Vector4f textColor = textState.getTextColor();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign verticalAlign = textState.getVerticalAlign();
        alignTextInBox(context, horizontalAlign, verticalAlign);
        for (int i = topIndex; i <= botIndex; i++) {
            String line = lines[i];
            float y1 = y + (i-topIndex) * fontSize;

            if (i == caretLine) {
                float caretx = 0;
                float bounds[] = NvgRenderUtils.calculateTextBoundsRect(context, x, y1, w, fontSize, line, 0, horizontalAlign, verticalAlign);
                ByteBuffer textBytes = MemoryUtil.memUTF8(line);
                int ng = nnvgTextGlyphPositions(context, bounds[0], bounds[1], memAddress(textBytes), 0, memAddress(glyphs), maxGlyphCount);
                if (lineCaretPosition < ng) {
                    try {
                        caretx = glyphs.get(lineCaretPosition).x();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(lineCaretPosition);
                        e.printStackTrace();
                    }
                } else {
                    if (ng > 0)
                        caretx = glyphs.get(ng - 1).maxx();
                    else {
                        caretx = x + horizontalAlign.index * w / 2f;
                    }
                }

                drawRectangle(context, BLUE, x, y1, w, fontSize);
                drawRectangle(context, RED, caretx, y1, 1, fontSize);
            }


            renderTextLineToBounds(context, x, y1, w, fontSize, fontSize, font, textColor, line, horizontalAlign, verticalAlign, false);
        }

    }

    private void renaderTextMultilineToBounds(long context, float x, float y, float w, float h,
                                              float fontSize, String font, Vector4f textColor, NVGColor nvgColor,
                                              String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, int caretPosition) {
        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);
        alignTextInBox(context, horizontalAlign, verticalAlign);

        ByteBuffer byteText = MemoryUtil.memUTF8(text);

        float[] lineh = {0};
        nvgTextMetrics(context, null, null, lineh);

        long start = memAddress(byteText);
        long end = start + byteText.remaining();

        int maxRows = (int) Math.floor((double) h / lineh[0]);

        NVGTextRow.Buffer buffer = NVGTextRow.create(maxRows);
        int nrows = 0;
        long glyphAddress = memAddress(glyphs);
        int caretOffset = 0;
        int rownum = 0;
        int rowGlyphCount = 0;
        NVGTextRow nvgTextRow = null;
        float[] bounds = new float[8];
        long diffBetweenRows = 0;
//        NvgRenderUtils.alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
        while (caretOffset < caretPosition && (nrows = nnvgTextBreakLines(context, start, end, w, memAddress(buffer), maxRows)) != 0) {
            for (int i = 0; i < nrows && caretOffset < caretPosition; i++) {
                nvgTextRow = buffer.get(i);
                bounds = createBounds(x, y, w, h, horizontalAlign, verticalAlign, nvgTextRow.width(), fontSize);
                rowGlyphCount = nnvgTextGlyphPositions(context, bounds[0], bounds[1], nvgTextRow.start(), nvgTextRow.end(), glyphAddress, maxGlyphCount);
                diffBetweenRows = i < nrows - 1 ? buffer.get(i + 1).start() - buffer.get(i).end() : 0;
                caretOffset += rowGlyphCount + diffBetweenRows;
                if (caretOffset <= caretPosition) {
                    rownum++;
                }
            }
            start = buffer.get(nrows - 1).next();
        }


        NVGColor textColorN = textColor.w == 0 ? rgba(0.0f, 0.0f, 0.0f, 1f, nvgColor) : rgba(textColor, nvgColor);
        start = memAddress(byteText);
        end = start + byteText.remaining();
        nrows = nnvgTextBreakLines(context, start, end, w, memAddress(buffer), maxRows);
        for (int i = 0; i < nrows; i++) {
            NVGTextRow row = buffer.get(i);
            bounds = createBounds(x, y, w, h, horizontalAlign, verticalAlign, row.width(), fontSize, i, nrows);
            if (i == rownum) {
                drawRectangle(context, BLUE, bounds[4], bounds[5], bounds[6], bounds[7]);
            }
            nvgBeginPath(context);
            nvgFillColor(context, textColorN);
            nnvgText(context, bounds[0], bounds[1], row.start(), row.end());
            diffBetweenRows = i < nrows - 1 ? buffer.get(i + 1).start() - buffer.get(i).end() : 0;


            // draw caret
            if (i == rownum) {
                float caretx;
                int cpos = caretPosition + rowGlyphCount + (int) diffBetweenRows - caretOffset;
                if (cpos < rowGlyphCount) {
                    caretx = glyphs.get(cpos).x();
                } else if (cpos < rowGlyphCount + diffBetweenRows) {
                    caretx = bounds[4] + bounds[6];
                } else if (cpos == rowGlyphCount + diffBetweenRows) {
                    caretx = bounds[4];
                } else {
                    if (rowGlyphCount > 0) caretx = glyphs.get(rowGlyphCount - 1).maxx();
                    else caretx = x + horizontalAlign.index * w / 2;
                }
                if (nvgTextRow != null) {
                    drawRectangle(context, RED, caretx, bounds[5], 1, bounds[7]);
                }
            }
        }

//        nvgBeginPath(context);
//        nvgFillColor(context, textColorN);
//        nvgTextBox(context, x, y, w, text, 0);


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
