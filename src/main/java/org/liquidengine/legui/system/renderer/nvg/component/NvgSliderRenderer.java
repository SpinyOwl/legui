package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.NVG_ROUND;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Renderer for Slider components.
 */
public class NvgSliderRenderer extends NvgDefaultComponentRenderer<Slider> {

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
    public void renderSelf(Slider slider, Context context, long nanovg) {
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
            NvgShapes.drawLine(nanovg, SLIDER_WIDTH, sliderInactiveColor, NVG_ROUND, lineStartX, lineStartY, lineEndX, lineEndY);

            // draw slider button
            Vector2f sliderButtonSize = new Vector2f(sliderSize);
            Vector2f sliderPos = new Vector2f(sliderX - sliderHalfSize, sliderY - sliderHalfSize);
            NvgShapes.drawRect(nanovg, sliderPos, sliderButtonSize, sliderColor, cornerRadius);
            NvgShapes.drawRectStroke(nanovg, sliderPos, sliderButtonSize, sliderInactiveColor, 1, cornerRadius);
        }
        resetScissor(nanovg);
    }

}
