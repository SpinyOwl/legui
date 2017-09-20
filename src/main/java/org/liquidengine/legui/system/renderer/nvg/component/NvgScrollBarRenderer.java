package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.color.ColorUtil.oppositeBlackOrWhite;
import static org.liquidengine.legui.component.optional.Orientation.VERTICAL;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil.rgba;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.alignTextInBox;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createBounds;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.TextUtil.cpToStr;
import static org.lwjgl.nanovg.NanoVG.nnvgText;
import static org.lwjgl.nanovg.NanoVG.nnvgTextBreakLines;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgSave;
import static org.lwjgl.system.MemoryUtil.memAddress;

import java.nio.ByteBuffer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;
import org.lwjgl.system.MemoryUtil;

/**
 * Created by ShchAlexander on 12.02.2017.
 */
public class NvgScrollBarRenderer extends NvgComponentRenderer<ScrollBar> {

    // TODO: It would be nice to add Icon here to render arrows.
    private static final String B = cpToStr(0xE5CF);
    private static final String L = cpToStr(0xE5CB);
    private static final String R = cpToStr(0xE5CC);
    private static final String T = cpToStr(0xE5CE);

    @Override
    public void renderComponent(ScrollBar scrollBar, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, scrollBar, () -> {
            nvgSave(nanovg);
            Vector2f pos = scrollBar.getScreenPosition();
            Vector2f size = scrollBar.getSize();
            Vector4f backgroundColor = new Vector4f(scrollBar.getBackgroundColor());

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

            float cornerRadius = scrollBar.getCornerRadius();

            // draw background
            NvgShapes.drawRect(nanovg, pos, size, backgroundColor, cornerRadius);

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
                NvgShapes.drawRect(nanovg, scrollBarPos, scrollBarSize, scrollBarBackgroundColor, arrowsEnabled ? 0 : cornerRadius);
            }
            // draw arrows
            {
                if (arrowsEnabled) {
                    Vector4f arrowColor = scrollBar.getArrowColor();
                    String first, second;
                    Vector2f arrowBgSize = new Vector2f();
                    Vector2f secondArrowBgPos = new Vector2f();
                    if (vertical) {
                        first = T;
                        second = B;
                        arrowBgSize.set(size.x, arrowSize);
                        secondArrowBgPos.set(pos.x, pos.y + size.y - arrowSize);
                    } else {
                        first = L;
                        second = R;
                        arrowBgSize.set(arrowSize, size.y);
                        secondArrowBgPos.set(pos.x + size.x - arrowSize, pos.y);
                    }
                    // first arrow bg
                    NvgShapes.drawRect(nanovg, pos, arrowBgSize, arrowColor, cornerRadius);
                    // second arrow bg
                    NvgShapes.drawRect(nanovg, secondArrowBgPos, arrowBgSize, arrowColor, cornerRadius);

                    float fontSize;
                    if (vertical) {
                        fontSize = arrowSize > arrowBgSize.x ? arrowBgSize.x : arrowSize;
                    } else {
                        fontSize = arrowSize > arrowBgSize.y ? arrowBgSize.y : arrowSize;
                    }
                    String font = FontRegistry.MATERIAL_ICONS_REGULAR;
                    HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
                    VerticalAlign verticalAlign = VerticalAlign.MIDDLE;

                    {
                        nvgFontSize(nanovg, fontSize);
                        nvgFontFace(nanovg, font);
                        alignTextInBox(nanovg, horizontalAlign, verticalAlign);

                        Vector4f blackOrWhite = oppositeBlackOrWhite(arrowColor);
                        drawArr(nanovg, pos.x, pos.y, arrowBgSize.x, arrowBgSize.y, first, blackOrWhite, fontSize, horizontalAlign, verticalAlign);
                        drawArr(nanovg, secondArrowBgPos.x, secondArrowBgPos.y, arrowBgSize.x, arrowBgSize.y, second, blackOrWhite, fontSize, horizontalAlign,
                            verticalAlign);
                    }
                }
            }

            // draw scroll button
            {
                float scrollBarSize = (vertical ? size.y : size.x) - 2 * diff;
                float scrollBarPercentageSize = visibleAmount / (maxValue - minValue);
                float barSize = scrollBarSize * scrollBarPercentageSize;
                if (barSize < ScrollBar.MIN_SCROLL_SIZE) {
                    barSize = ScrollBar.MIN_SCROLL_SIZE;
                }
                float rangeToScroll = scrollBarSize - barSize;
                float scrollPosAccordingToScrollBounds = diff + rangeToScroll * curValue / (maxValue - minValue);

                Vector2f scrollPos = new Vector2f();
                Vector2f scrollSize = new Vector2f();
                if (vertical) {
                    scrollPos.set(pos.x + offset, pos.y + offset + scrollPosAccordingToScrollBounds);
                    scrollSize.set(size.x - 2 * offset, barSize - 2 * offset);
                } else {
                    scrollPos.set(pos.x + offset + scrollPosAccordingToScrollBounds, pos.y + offset);
                    scrollSize.set(barSize - 2 * offset, size.y - 2 * offset);
                }

                NvgShapes.drawRect(nanovg, scrollPos, scrollSize, scrollColor, cornerRadius);
            }

            // draw border
            renderBorder(scrollBar, context);

        });
    }

    private void drawArr(long context, float x1, float y1, float w1, float h1, String first, Vector4f blackOrWhite, float fontSize,
        HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
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

                NVGColor colorA = NVGColor.calloc();
                nvgBeginPath(context);
                nvgFillColor(context, rgba(blackOrWhite, colorA));
                nnvgText(context, bounds[0], bounds[1], row.start(), row.end());
                colorA.free();
            }
            buffer.free();
        } finally {
            if (byteText != null) {
                MemoryUtil.memFree(byteText);
            }
        }
    }
}
