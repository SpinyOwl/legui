package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
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
            float width = size.x;
            float height = size.y;

            Vector4f cornerRadius = getBorderRadius(slider);
            renderBackground(slider, context, nanovg);

            final float minValue = slider.getMinValue();
            final float maxValue = slider.getMaxValue();
            final float difference = maxValue - minValue;
            final float value = slider.getValue() - minValue;
            final boolean vertical = Orientation.VERTICAL.equals(slider.getOrientation());
            final Vector4f sliderInactiveColor = slider.getSliderColor();
            final Vector4f sliderColor = slider.getSliderActiveColor();
            final float sliderSize = slider.getSliderSize();

            final float lineStartX;
            final float lineEndX;
            final float lineStartY;
            final float lineEndY;
            final float sliderX;
            final float sliderY;
            final float sliderHalfSize = sliderSize / 2f;
            final float percentage = value / difference;
            if (vertical) {
                lineStartY = y + sliderHalfSize;
                lineEndY = y + height - sliderHalfSize;
                final float sizeY = lineEndY - lineStartY;
                lineStartX = x + (width) / 2f;
                lineEndX = lineStartX;
                sliderX = lineStartX;
                sliderY = lineStartY + sizeY * percentage;
            } else {
                lineStartX = x + sliderHalfSize;
                lineEndX = x + width - sliderHalfSize;
                final float sizeX = lineEndX - lineStartX;
                lineStartY = y + (height) / 2f;
                lineEndY = lineStartY;
                sliderY = lineStartY;
                sliderX = lineStartX + sizeX * percentage;
            }

            // draw inactive color
            drawLine(nanovg, sliderInactiveColor, lineStartX, lineStartY, lineEndX, lineEndY, SLIDER_WIDTH);

            // draw slider button
            Vector2f sliderButtonSize = new Vector2f(sliderSize);
            Vector2f sliderPos = new Vector2f(sliderX - sliderHalfSize, sliderY - sliderHalfSize);
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
