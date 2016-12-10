package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.component.optional.Orientation.VERTICAL;
import static org.liquidengine.legui.util.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class NvgScrollBarRenderer extends NvgLeguiComponentRenderer {
    private static final String B = Util.cpToStr(0xE5CF);
    private static final String L = Util.cpToStr(0xE5CB);
    private static final String R = Util.cpToStr(0xE5CC);
    private static final String T = Util.cpToStr(0xE5CE);
    private NVGColor colorA = NVGColor.malloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            nvgSave(context);
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();
            float x = pos.x;
            float y = pos.y;
            float w = size.x;
            float h = size.y;
            Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());

            ScrollBar scrollBar = (ScrollBar) component;
            float visibleAmount = scrollBar.getVisibleAmount();

            float minValue = scrollBar.getMinValue();
            float maxValue = scrollBar.getMaxValue();
            float arrowSize = scrollBar.getArrowSize();

            boolean arrowsEnabled = scrollBar.isArrowsEnabled();
            float diff = arrowsEnabled ? arrowSize : 0;

            float offset = 1f;

            float curValue = scrollBar.getCurValue();
            boolean vertical = VERTICAL.equals(scrollBar.getOrientation());
            Vector4f scrollBarBackgroundColor = scrollBar.getBackgroundColor();
            Vector4f scrollColor = scrollBar.getScrollColor();

            float cornerRadius = component.getCornerRadius();

            // draw background
            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, cornerRadius);
            nvgFillColor(context, rgba(backgroundColor, colorA));
            nvgFill(context);

            // draw scroll bar back
            {
                float lx, ly, wx, hy;
                if (vertical) {
                    lx = x;
                    ly = y + diff;
                    wx = w;
                    hy = h - 2 * diff;
                } else {
                    lx = x + diff;
                    ly = y;
                    wx = w - 2 * diff;
                    hy = h;
                }

                nvgBeginPath(context);
                nvgRoundedRect(context, lx, ly, wx, hy, arrowsEnabled ? 0 : cornerRadius);
                nvgFillColor(context, rgba(scrollBarBackgroundColor, colorA));
                nvgFill(context);

                if (arrowsEnabled) {
                    drawArrows(context, scrollBar, pos, size);
                }
            }

            // draw slider button
            {
                float scrollBarSize = (vertical ? scrollBar.getSize().y : scrollBar.getSize().x) - 2 * diff;
                float scrollBarPercentageSize = visibleAmount / (maxValue - minValue);
                float barSize = scrollBarSize * scrollBarPercentageSize;
                if (barSize < ScrollBar.MIN_SCROLL_SIZE) barSize = ScrollBar.MIN_SCROLL_SIZE;
                float rangeToScroll = scrollBarSize - barSize;
                float scrollPosAccordingToScrollBounds = diff + rangeToScroll * curValue / (maxValue - minValue);

                float xx, yy, ww, hh;
                if (vertical) {
                    xx = x + offset;
                    yy = y + offset + scrollPosAccordingToScrollBounds;
                    ww = w - 2 * offset;
                    hh = barSize - 2 * offset;
                } else {
                    xx = x + offset + scrollPosAccordingToScrollBounds;
                    yy = y + offset;
                    ww = barSize - 2 * offset;
                    hh = h - 2 * offset;
                }

                nvgBeginPath(context);
                nvgRoundedRect(context, xx, yy, ww, hh, cornerRadius);
                nvgFillColor(context, rgba(scrollColor, colorA));
                nvgFill(context);
            }

            // draw border
            renderBorder(component, leguiContext);

        }
        resetScissor(context);
    }

    private void drawArrows(long context, ScrollBar scrollBar, Vector2f pos, Vector2f size) {
        boolean vertical = VERTICAL.equals(scrollBar.getOrientation());
        Vector4f arrowColor = scrollBar.getArrowColor();
        float arrowSize = scrollBar.getArrowSize();
        float x1, y1, w1, h1, x2, y2, w2, h2;
        String first, second;
        if (vertical) {
            x1 = pos.x;
            y1 = pos.y;
            w1 = size.x;
            h1 = arrowSize;
            x2 = pos.x;
            y2 = pos.y + size.y - arrowSize;
            w2 = size.x;
            h2 = arrowSize;
            first = T;
            second = B;
        } else {
            x1 = pos.x;
            y1 = pos.y;
            w1 = arrowSize;
            h1 = size.y;
            x2 = pos.x + size.x - arrowSize;
            y2 = pos.y;
            w2 = arrowSize;
            h2 = size.y;
            first = L;
            second = R;
        }
        Vector4f blackOrWhite = oppositeBlackOrWhite(arrowColor);
        // first arrow bg
        drawRectangle(context, arrowColor, x1, y1, w1, h1);
        // second arrow bg
        drawRectangle(context, arrowColor, x2, y2, w2, h2);


        float fontSize = arrowSize > h1 ? h1 : arrowSize;
        String font = FontRegister.MATERIAL_ICONS_REGULAR;
        HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
        VerticalAlign verticalAlign = VerticalAlign.MIDDLE;

        {
            nvgFontSize(context, fontSize);
            nvgFontFace(context, font);
            alignTextInBox(context, horizontalAlign, verticalAlign);

            drawArr(context, x1, y1, w1, h1, first, blackOrWhite, fontSize, horizontalAlign, verticalAlign);
            drawArr(context, x2, y2, w2, h2, second, blackOrWhite, fontSize, horizontalAlign, verticalAlign);
        }
    }

    private void drawArr(long context, float x1, float y1, float w1, float h1, String first, Vector4f blackOrWhite, float fontSize, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        ByteBuffer byteText = null;
        try {
            byteText = MemoryUtil.memUTF8(first);
            NVGTextRow.Buffer buffer = NVGTextRow.calloc(1);
            {
                long start = memAddress(byteText);
                long end = start + byteText.remaining() - 1;
                nnvgTextBreakLines(context, start, end, w1, memAddress(buffer), 1);
                NVGTextRow row = buffer.get(0);
                float[] bounds = createBounds(x1, y1, w1, h1, horizontalAlign, verticalAlign, row.width(), fontSize);
                nvgBeginPath(context);
                nvgFillColor(context, rgba(blackOrWhite, colorA));
                nnvgText(context, bounds[0], bounds[1], row.start(), row.end());
            }
            buffer.free();
        } finally {
            if (byteText != null) {
                MemoryUtil.memFree(byteText);
            }
        }
    }

}
