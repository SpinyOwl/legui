package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil.fillNvgColorWithRGBA;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Renderer for Slider components.
 *
 * @param <T> component type.
 */
public class NvgSliderRenderer<T extends Slider> extends NvgDefaultComponentRenderer<T> {

    /**
     * Slider width.
     */
    public static final float SLIDER_WIDTH = 4.0f;

    /**
     * Used to render slider component.
     *
     * @param slider slider to render.
     * @param context context.
     * @param nanovg nanoVG context.
     */
    @Override
    public void renderSelf(T slider, Context context, long nanovg) {
        createScissor(nanovg, slider);
        {
            nvgSave(nanovg);
            Vector2f pos = slider.getAbsolutePosition();
            Vector2f size = slider.getSize();
            float x = pos.x;
            float y = pos.y;
            float w = size.x;
            float h = size.y;

            float value = slider.getValue();
            boolean vertical = Orientation.VERTICAL.equals(slider.getOrientation());
            Vector4f sliderInactiveColor = slider.getSliderColor();
            Vector4f sliderColor = slider.getSliderActiveColor();
            Vector4f cornerRadius = slider.getStyle().getBorderRadius();
            float sliderSize = slider.getSliderSize();

            renderBackground(slider, context, nanovg);

            float lx,
                rx,
                ty,
                by,
                px,
                py;
            if (vertical) {
                px = lx = rx = x + (w) / 2f;
                ty = y + sliderSize / 2f;
                by = y + h - sliderSize / 2f;
                py = by - (by - ty) * value / 100f;
            } else {
                py = ty = by = y + (h) / 2f;
                lx = x + sliderSize / 2f;
                rx = x + w - sliderSize / 2f;
                px = lx + (rx - lx) * value / 100f;
            }

            // draw inactive color
            drawLine(nanovg, sliderInactiveColor, lx, by, rx, ty, SLIDER_WIDTH);

            // draw active part
            drawLine(nanovg, sliderColor, lx, by, px, py, SLIDER_WIDTH);

            // draw slider button
            Vector2f sliderButtonSize = new Vector2f(sliderSize);
            Vector2f sliderPos = new Vector2f(px - sliderSize / 2f, py - sliderSize / 2f);
            NvgShapes.drawRect(nanovg, sliderPos, sliderButtonSize, sliderColor, cornerRadius);
            NvgShapes.drawRectStroke(nanovg, sliderPos, sliderButtonSize, sliderInactiveColor, 1, cornerRadius);
        }
        resetScissor(nanovg);
    }

    /**
     * Used to render line.
     *
     * @param context nanoVG context.
     * @param color color to render
     * @param x1 left x
     * @param x2 right x
     * @param y2 top y
     * @param y1 bottom y
     * @param width line width
     */
    private void drawLine(long context, Vector4f color, float x1, float y1, float x2, float y2, float width) {
        try (
                NVGColor colorA = NVGColor.calloc()
        ) {
            NvgColorUtil.fillNvgColorWithRGBA(color, colorA);
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, width);
            nvgStrokeColor(context, colorA);
            nvgBeginPath(context);
            nvgMoveTo(context, x1, y1);
            nvgLineTo(context, x2, y2);
            nvgStroke(context);
        }
    }

}
