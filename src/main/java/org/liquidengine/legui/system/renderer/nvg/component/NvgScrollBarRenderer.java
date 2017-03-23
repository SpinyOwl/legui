package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.liquidengine.legui.color.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.component.optional.Orientation.VERTICAL;
import static org.liquidengine.legui.system.renderer.nvg.util.NVGUtils.rgba;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.liquidengine.legui.util.TextUtil.cpToStr;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

/**
 * Created by ShchAlexander on 12.02.2017.
 */
public class NvgScrollBarRenderer extends NvgComponentRenderer<ScrollBar> {
    // TODO: It would be nice to add Icon here to render arrows.
    private static final String   B      = cpToStr(0xE5CF);
    private static final String   L      = cpToStr(0xE5CB);
    private static final String   R      = cpToStr(0xE5CC);
    private static final String   T      = cpToStr(0xE5CE);
    private              NVGColor colorA = NVGColor.malloc();

    @Override
    public void renderComponent(ScrollBar scrollBar, Context leguiContext, long context) {
        createScissor(context, scrollBar);
        {
            nvgSave(context);
            Vector2f pos             = scrollBar.getScreenPosition();
            Vector2f size            = scrollBar.getSize();
            float    x               = pos.x;
            float    y               = pos.y;
            float    w               = size.x;
            float    h               = size.y;
            Vector4f backgroundColor = new Vector4f(scrollBar.getBackgroundColor());

            float visibleAmount = scrollBar.getVisibleAmount();

            float minValue  = scrollBar.getMinValue();
            float maxValue  = scrollBar.getMaxValue();
            float arrowSize = scrollBar.getArrowSize();

            boolean arrowsEnabled = scrollBar.isArrowsEnabled();
            float   diff          = arrowsEnabled ? arrowSize : 0;

            float offset = 1f;

            float    curValue                 = scrollBar.getCurValue();
            boolean  vertical                 = VERTICAL.equals(scrollBar.getOrientation());
            Vector4f scrollBarBackgroundColor = scrollBar.getBackgroundColor();
            Vector4f scrollColor              = scrollBar.getScrollColor();

            float cornerRadius = scrollBar.getCornerRadius();

            // draw background
            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, cornerRadius);
            nvgFillColor(context, rgba(backgroundColor, colorA));
            nvgFill(context);

            // draw scroll bar back
            drawScrollBackground(scrollBar, context, x, y, w, h, arrowsEnabled, diff, vertical, scrollBarBackgroundColor, cornerRadius);

            // draw scroll button
            drawScrollButton(scrollBar, context, x, y, w, h, visibleAmount, minValue, maxValue, diff, offset, curValue, vertical, scrollColor, cornerRadius);

            // draw border
            renderBorder(scrollBar, leguiContext);

        }
        resetScissor(context);
    }

    private void drawScrollBackground(ScrollBar scrollBar, long context, float x, float y, float w, float h, boolean arrowsEnabled, float diff, boolean vertical, Vector4f scrollBarBackgroundColor, float cornerRadius) {
        float lx,
                ly,
                wx,
                hy;
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
            drawArrows(context, scrollBar, x, y, w, h);
        }
    }

    private void drawScrollButton(ScrollBar scrollBar,
                                  long context,
                                  float x, float y, float w, float h,
                                  float visibleAmount, float minValue,
                                  float maxValue, float diff, float offset,
                                  float curValue, boolean vertical,
                                  Vector4f scrollColor, float cornerRadius) {
        float scrollBarSize           = (vertical ? scrollBar.getSize().y : scrollBar.getSize().x) - 2 * diff;
        float scrollBarPercentageSize = visibleAmount / (maxValue - minValue);
        float barSize                 = scrollBarSize * scrollBarPercentageSize;
        if (barSize < ScrollBar.MIN_SCROLL_SIZE) barSize = ScrollBar.MIN_SCROLL_SIZE;
        float rangeToScroll                    = scrollBarSize - barSize;
        float scrollPosAccordingToScrollBounds = diff + rangeToScroll * curValue / (maxValue - minValue);

        float xx,
                yy,
                ww,
                hh;
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

    private void drawArrows(long context, ScrollBar scrollBar, float x, float y, float w, float h) {
        boolean  vertical   = VERTICAL.equals(scrollBar.getOrientation());
        Vector4f arrowColor = scrollBar.getArrowColor();
        float    arrowSize  = scrollBar.getArrowSize();
        float x1,
                y1,
                w1,
                h1,
                x2,
                y2,
                w2,
                h2;
        String first,
                second;
        if (vertical) {
            x1 = x;
            y1 = y;
            w1 = w;
            h1 = arrowSize;
            x2 = x;
            y2 = y + h - arrowSize;
            w2 = w;
            h2 = arrowSize;
            first = T;
            second = B;
        } else {
            x1 = x;
            y1 = y;
            w1 = arrowSize;
            h1 = h;
            x2 = x + w - arrowSize;
            y2 = y;
            w2 = arrowSize;
            h2 = h;
            first = L;
            second = R;
        }
        Vector4f blackOrWhite = oppositeBlackOrWhite(arrowColor);
        // first arrow bg
        drawRectangle(context, arrowColor, x1, y1, w1, h1);
        // second arrow bg
        drawRectangle(context, arrowColor, x2, y2, w2, h2);


        float fontSize;
        if (vertical) {
            fontSize = arrowSize > w1 ? w1 : arrowSize;
        } else {
            fontSize = arrowSize > h1 ? h1 : arrowSize;
        }
        String          font            = FontRegister.MATERIAL_ICONS_REGULAR;
        HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
        VerticalAlign   verticalAlign   = VerticalAlign.MIDDLE;

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
                long end   = start + byteText.remaining() - 1;
                nnvgTextBreakLines(context, start, end, w1, memAddress(buffer), 1);
                NVGTextRow row    = buffer.get(0);
                float[]    bounds = createBounds(x1, y1, w1, h1, horizontalAlign, verticalAlign, row.width(), fontSize);
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
