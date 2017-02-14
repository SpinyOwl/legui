package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.util.NVGUtils.rgba;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 12.02.2017.
 */
public class NvgSliderRenderer extends NvgComponentRenderer<Slider> {
    private NVGColor colorA = NVGColor.malloc();

    @Override
    public void renderComponent(Slider slider, Context leguiContext, long context) {
        createScissor(context, slider);
        {
            nvgSave(context);
            Vector2f pos             = slider.getScreenPosition();
            Vector2f size            = slider.getSize();
            float    x               = pos.x;
            float    y               = pos.y;
            float    w               = size.x;
            float    h               = size.y;
            Vector4f backgroundColor = new Vector4f(slider.getBackgroundColor());

            float    value               = slider.getValue();
            boolean  vertical            = Orientation.VERTICAL.equals(slider.getOrientation());
            Vector4f sliderInactiveColor = slider.getSliderColor();
            Vector4f sliderColor         = slider.getSliderActiveColor();
            float    cornerRadius        = slider.getCornerRadius();
            float    sliderSize          = slider.getSliderSize();

            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, cornerRadius);
            nvgFillColor(context, rgba(backgroundColor, colorA));
            nvgFill(context);

            float lx, rx, ty, by, px, py;
            if (vertical) {
                px = lx = rx = x + w / 2f;
                ty = y + sliderSize / 2f;
                by = y + h - sliderSize / 2f;
                py = by - (by - ty) * value / 100f;
            } else {
                py = ty = by = y + h / 2f;
                lx = x + sliderSize / 2f;
                rx = x + w - sliderSize / 2f;
                px = lx + (rx - lx) * value / 100f;
            }

            float sliderWidth = 4.0f;

            // draw inactive color
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, sliderWidth);
            nvgStrokeColor(context, rgba(sliderInactiveColor, colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, lx, by);
            nvgLineTo(context, rx, ty);
            nvgStroke(context);

            // draw active part
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, sliderWidth);
            nvgStrokeColor(context, rgba(sliderColor, colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, lx, by);
            nvgLineTo(context, px, py);
            nvgStroke(context);

            // draw slider button
            float xx = px - sliderSize / 2f;
            float yy = py - sliderSize / 2f;

            nvgBeginPath(context);
            nvgRoundedRect(context, xx, yy, sliderSize, sliderSize, cornerRadius);
            nvgFillColor(context, rgba(sliderColor, colorA));
            nvgFill(context);

            NvgRenderUtils.drawRectStroke(context, xx + 0.5f, yy + 0.5f, sliderSize, sliderSize, sliderInactiveColor, cornerRadius, 1);

            renderBorder(slider, leguiContext);
        }
        resetScissor(context);
    }

}
