package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memUTF8;

/**
 * Created by ShchAlexander on 19.09.2017.
 */
public class NvgText {

    private NvgText() {
    }

    public static void drawTextLineToRect(long nvg, TextState text, Vector2fc pos, Vector2fc size, boolean hideOverflow) {
        if (text.length() == 0) {
            return;
        }
        Vector4f rect = new Vector4f(pos, size.x(), size.y());
        drawTextLineToRect(nvg, text, rect, hideOverflow);
    }

    public static void drawTextLineToRect(long nvg, TextState text, Vector4fc rect, boolean hideOverflow) {
        drawTextLineToRect(nvg, rect, hideOverflow, text.getHorizontalAlign(), text.getVerticalAlign(),
                text.getFontSize(), text.getFont(), text.getText(), text.getTextColor());
    }

    public static void drawTextLineToRect(long nvg, Vector4fc rect, boolean hideOverflow, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign,
                                          float fontSize, String font, String textToRender, Vector4f fontColor) {
        nvgFontSize(nvg, fontSize);
        nvgFontFace(nvg, font);
        textAlign(nvg, horizontalAlign, verticalAlign);

        ByteBuffer byteText = null;
        try {
            byteText = memUTF8(textToRender, false);
            long startPointer = memAddress(byteText);
            long endPointer = startPointer + byteText.remaining();

            long rowStart = startPointer;
            long rowEnd = endPointer;
            if (hideOverflow) {
                try (NVGTextRow.Buffer buffer = NVGTextRow.calloc(1)) {
                    int rows = nnvgTextBreakLines(nvg, startPointer, endPointer, rect.z(), memAddress(buffer), 1);
                    if (rows != 0) {
                        NVGTextRow row = buffer.get(0);
                        rowStart = row.start();
                        rowEnd = row.end();
                    }
                }
            }

            Vector2f textPosition = new Vector2f(
                    rect.x() + rect.z() * horizontalAlign.index / 2f,
                    rect.y() + rect.w() * verticalAlign.index / 2f
            );

            if (rowStart != 0 || rowEnd != 0) {
                try (NVGColor textColor = NVGColor.calloc()) {
                    NvgColorUtil.fillNvgColorWithRGBA(fontColor, textColor);
                    nvgBeginPath(nvg);
                    nvgFillColor(nvg, textColor);
                    nnvgText(nvg, textPosition.x, textPosition.y, rowStart, rowEnd);
                }
            }
        } finally {
            if (byteText != null) {
                MemoryUtil.memFree(byteText);
            }
        }
    }

    public static void textAlign(long context, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        int nvgHorizontalAlign = NVG_ALIGN_LEFT;
        int nvgVerticalAlign = NVG_ALIGN_MIDDLE;
        // @formatter:off
        switch (horizontalAlign) {
            case LEFT:
                nvgHorizontalAlign = NVG_ALIGN_LEFT;
                break;
            case RIGHT:
                nvgHorizontalAlign = NVG_ALIGN_RIGHT;
                break;
            case CENTER:
                nvgHorizontalAlign = NVG_ALIGN_CENTER;
                break;
        }
        switch (verticalAlign) {
            case TOP:
                nvgVerticalAlign = NVG_ALIGN_TOP;
                break;
            case BOTTOM:
                nvgVerticalAlign = NVG_ALIGN_BOTTOM;
                break;
            case MIDDLE:
                nvgVerticalAlign = NVG_ALIGN_MIDDLE;
                break;
            case BASELINE:
                nvgVerticalAlign = NVG_ALIGN_BASELINE;
                break;
        }
        // @formatter:on
        nvgTextAlign(context, nvgHorizontalAlign | nvgVerticalAlign);
    }
}
