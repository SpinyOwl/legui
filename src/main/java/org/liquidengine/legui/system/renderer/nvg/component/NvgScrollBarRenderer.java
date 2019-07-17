package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.component.optional.Orientation.VERTICAL;
import static org.liquidengine.legui.style.color.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.liquidengine.legui.util.TextUtil.cpToStr;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

/**
 * Created by ShchAlexander on 12.02.2017.
 */
public class NvgScrollBarRenderer extends NvgDefaultComponentRenderer<ScrollBar> {

    // TODO: It would be nice to add Icon here to render arrows.
    private static final String B = cpToStr(0xE5CF);
    private static final String L = cpToStr(0xE5CB);
    private static final String R = cpToStr(0xE5CC);
    private static final String T = cpToStr(0xE5CE);

    @Override
    public void renderSelf(ScrollBar scrollBar, Context context, long nanovg) {
        createScissor(nanovg, scrollBar);
        {
            nvgSave(nanovg);
            Vector2f pos = scrollBar.getAbsolutePosition();
            Vector2f size = scrollBar.getSize();
            Vector4f backgroundColor = new Vector4f(scrollBar.getStyle().getBackground().getColor());

            float arrowSize = scrollBar.getArrowSize();

            boolean arrowsEnabled = scrollBar.isArrowsEnabled();
            float diff = arrowsEnabled ? arrowSize : 0;

            float offset = 1f;

            boolean vertical = VERTICAL.equals(scrollBar.getOrientation());

            // draw background
            renderBackground(scrollBar, context, nanovg);

            // draw scroll bar back
            {
                Vector2f scrollBarPos = new Vector2f();
                Vector2f scrollBarSize = new Vector2f();
                if (vertical) {
                    scrollBarPos.set(pos.x, pos.y + diff);
                    scrollBarSize.set(size.x, size.y - 2 * diff);
                } else {
                    scrollBarPos.set(pos.x + diff, pos.y);
                    scrollBarSize.set(size.x - 2 * diff, size.y);
                }
                NvgShapes.drawRect(nanovg, scrollBarPos, scrollBarSize, scrollBar.getStyle().getBackground().getColor(),
                                   arrowsEnabled ? new Vector4f(0) : getBorderRadius(scrollBar));
            }
            // draw arrows
            drawArrows(nanovg, scrollBar, pos, size);

            // draw scroll button
            drawScrollButton(nanovg, pos, size, scrollBar, diff, offset, vertical);
        }
        resetScissor(nanovg);
    }

    /**
     * Used to render arrows.
     *
     * @param nanovg nanovg.
     * @param scrollBar scrollbar.
     * @param pos scrollbar position.
     * @param size scrollbar size.
     */
    private void drawArrows(long nanovg, ScrollBar scrollBar, Vector2f pos, Vector2f size) {
        boolean arrowsEnabled = scrollBar.isArrowsEnabled();
        if (arrowsEnabled) {
            Vector4f arrowColor = scrollBar.getArrowColor();
            String firstArrowIcon;
            String secondArrowIcon;
            Vector2f arrowBgSize = new Vector2f();
            Vector2f arrow2pos = new Vector2f();
            float arrowSize = scrollBar.getArrowSize();
            Vector4f cornerRadius = getBorderRadius(scrollBar);
            boolean vertical = VERTICAL.equals(scrollBar.getOrientation());
            if (vertical) {
                firstArrowIcon = T;
                secondArrowIcon = B;
                arrowBgSize.set(size.x, arrowSize);
                arrow2pos.set(pos.x, pos.y + size.y - arrowSize);
            } else {
                firstArrowIcon = L;
                secondArrowIcon = R;
                arrowBgSize.set(arrowSize, size.y);
                arrow2pos.set(pos.x + size.x - arrowSize, pos.y);
            }
            // first arrow bg
            NvgShapes.drawRect(nanovg, pos, arrowBgSize, arrowColor, cornerRadius);
            // second arrow bg
            NvgShapes.drawRect(nanovg, arrow2pos, arrowBgSize, arrowColor, cornerRadius);

            float fontSize;
            if (vertical) {
                fontSize = arrowSize > arrowBgSize.x ? arrowBgSize.x : arrowSize;
            } else {
                fontSize = arrowSize > arrowBgSize.y ? arrowBgSize.y : arrowSize;
            }

            {
                Vector4f color = oppositeBlackOrWhite(arrowColor);

                Vector4f firstArrowBounds = new Vector4f(pos.x, pos.y, arrowBgSize.x, arrowBgSize.y);
                Vector4f secondArrowBounds = new Vector4f(arrow2pos.x, arrow2pos.y, arrowBgSize.x, arrowBgSize.y);

                NvgText.drawTextLineToRect(nanovg, firstArrowBounds, false, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, fontSize,
                                           FontRegistry.MATERIAL_ICONS_REGULAR, firstArrowIcon, color);
                NvgText.drawTextLineToRect(nanovg, secondArrowBounds, false, HorizontalAlign.CENTER, VerticalAlign.MIDDLE, fontSize,
                                           FontRegistry.MATERIAL_ICONS_REGULAR, secondArrowIcon, color);
            }
        }
    }

    /**
     * Used to draw scroll button.
     *
     * @param nanovg nanovg.
     * @param pos pos.
     * @param size size.
     * @param scrollBar scrollBar.
     * @param diff diff.
     * @param offset offset.
     * @param vertical vertical.
     */
    private void drawScrollButton(long nanovg, Vector2f pos, Vector2f size, ScrollBar scrollBar, float diff, float offset, boolean vertical) {
        float scrollBarSize = (vertical ? size.y : size.x) - 2 * diff;
        float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
        float barSize = scrollBarSize * scrollBar.getVisibleAmount() / valueRange;
        if (barSize < ScrollBar.MIN_SCROLL_SIZE) {
            barSize = ScrollBar.MIN_SCROLL_SIZE;
        }
        float scrollPosAccordingToScrollBounds = diff + (scrollBarSize - barSize) * scrollBar.getCurValue() / valueRange;

        Vector2f scrollPos = new Vector2f();
        Vector2f scrollSize = new Vector2f();
        if (vertical) {
            scrollPos.set(pos.x + offset, pos.y + offset + scrollPosAccordingToScrollBounds);
            scrollSize.set(size.x - 2 * offset, barSize - 2 * offset);
        } else {
            scrollPos.set(pos.x + offset + scrollPosAccordingToScrollBounds, pos.y + offset);
            scrollSize.set(barSize - 2 * offset, size.y - 2 * offset);
        }

        NvgShapes.drawRect(nanovg, scrollPos, scrollSize, scrollBar.getScrollColor(), getBorderRadius(scrollBar));
    }
}
