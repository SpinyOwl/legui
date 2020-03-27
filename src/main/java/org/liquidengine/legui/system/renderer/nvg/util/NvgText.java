package org.liquidengine.legui.system.renderer.nvg.util;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.style.font.TextDirection;
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

    public static final double THRESHOLD = 0.001;
    public static final float _90 = (float) (Math.PI / 2);
    public static final float _270 = (float) (3 * Math.PI / 2);

    private NvgText() {
    }

    public static void drawTextLineToRect(long nvg, Vector4fc rect, boolean hideOverflow,
                                          HorizontalAlign horizontalAlign, VerticalAlign verticalAlign,
                                          float fontSize, String font, String textToRender, Vector4f fontColor) {
        drawTextLineToRect(nvg, rect, hideOverflow, horizontalAlign, verticalAlign, fontSize, font, textToRender, fontColor, TextDirection.HORIZONTAL);
    }

    public static void drawTextLineToRect(long nvg, Vector4fc rect, boolean hideOverflow,
                                          HorizontalAlign horizontalAlign, VerticalAlign verticalAlign,
                                          float fontSize, String font, String textToRender, Vector4f fontColor, TextDirection direction) {
        if (textToRender.length() == 0) {
            return;
        }
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
                    int rows = nnvgTextBreakLines(nvg, startPointer, endPointer, direction == TextDirection.HORIZONTAL ? rect.z() : rect.w(), memAddress(buffer), 1);
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

                    nvgSave(nvg);
                    nvgBeginPath(nvg);

                    nvgFillColor(nvg, textColor);
                    if (direction == TextDirection.VERTICAL_TOP_DOWN) {
                        nvgTranslate(nvg, rect.x() + rect.z(), rect.y());
                        nvgRotate(nvg, _90);
                        nnvgText(nvg, rect.w() * horizontalAlign.index / 2f, rect.z() * verticalAlign.index / 2f, rowStart, rowEnd);
                    } else if (direction == TextDirection.VERTICAL_DOWN_TOP) {
                        nvgTranslate(nvg, rect.x(), rect.y() + rect.w());
                        nvgRotate(nvg, _270);
                        nnvgText(nvg, rect.w() * horizontalAlign.index / 2f, rect.z() * verticalAlign.index / 2f, rowStart, rowEnd);
                    } else {
                        nvgTranslate(nvg, rect.x(), rect.y());
                        nnvgText(nvg, textPosition.x - rect.x(), ((int)textPosition.y - rect.y()), rowStart, rowEnd);
                    }
                    nvgRestore(nvg);
                }
            }
        } finally {
            if (byteText != null) {
                MemoryUtil.memFree(byteText);
            }
        }
    }

    public static void textAlign(long context, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        int nvgHorizontalAlign = 0;
        int nvgVerticalAlign = 0;
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
