package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NVGUtils;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.ColorUtil.blackOrWhite;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

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
            boolean vertical = Orientation.VERTICAL.equals(scrollBar.getOrientation());
            Vector4f scrollBarBackgroundColor = scrollBar.getBackgroundColor();
            Vector4f scrollColor = scrollBar.getScrollColor();

            float cornerRadius = component.getCornerRadius();

            // draw background
            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, cornerRadius);
            nvgFillColor(context, NVGUtils.rgba(backgroundColor, colorA));
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
                nvgFillColor(context, NVGUtils.rgba(scrollBarBackgroundColor, colorA));
                nvgFill(context);

                if (arrowsEnabled) {
                    Vector4f arrowColor = scrollBar.getArrowColor();
                    Vector4f blackOrWhite = blackOrWhite(arrowColor);
                    drawArrows(context, arrowSize, vertical, lx, ly, wx, hy, arrowColor, blackOrWhite);
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
                nvgFillColor(context, NVGUtils.rgba(scrollColor, colorA));
                nvgFill(context);
            }

            // draw border
            renderBorder(component, leguiContext);

        }
        resetScissor(context);
    }

    private void drawArrows(long context, float arrowSize, boolean vertical, float lx, float ly, float wx, float hy, Vector4f arrowColor, Vector4f blackOrWhite) {
        if (vertical) {
            float y1 = ly - arrowSize;
            float y2 = ly + hy;
            drawRectangle(context, arrowColor, lx, y1, wx, arrowSize);
            drawRectangle(context, arrowColor, lx, y2, wx, arrowSize);
            NvgRenderUtils.renderTextLineToBounds(context, lx + 2.5f, y1, wx, arrowSize, arrowSize > wx ? wx : arrowSize, FontRegister
                    .MATERIAL_ICONS_REGULAR, blackOrWhite, T, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
            NvgRenderUtils.renderTextLineToBounds(context, lx + 2.5f, y2, wx, arrowSize, arrowSize > wx ? wx : arrowSize, FontRegister
                    .MATERIAL_ICONS_REGULAR, blackOrWhite, B, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
        } else {
            float x1 = lx - arrowSize;
            float x2 = lx + wx;
            drawRectangle(context, arrowColor, x1, ly, arrowSize, hy);
            drawRectangle(context, arrowColor, x2, ly, arrowSize, hy);
            NvgRenderUtils.renderTextLineToBounds(context, x1 + 2.5f, ly, arrowSize, hy, arrowSize > hy ? hy : arrowSize, FontRegister
                    .MATERIAL_ICONS_REGULAR, blackOrWhite, L, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
            NvgRenderUtils.renderTextLineToBounds(context, x2 + 2.5f, ly, arrowSize, hy, arrowSize > hy ? hy : arrowSize, FontRegister
                    .MATERIAL_ICONS_REGULAR, blackOrWhite, R, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, false);
        }
    }

}
