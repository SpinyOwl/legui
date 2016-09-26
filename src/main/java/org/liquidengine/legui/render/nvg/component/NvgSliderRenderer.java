package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 7/18/2016.
 */
public class NvgSliderRenderer extends NvgLeguiComponentRenderer {

    private NVGColor colorA = NVGColor.malloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            nvgSave(context);
            Vector2f pos = calculatePosition(component);
            Vector2f size = component.getSize();
            float x = pos.x;
            float y = pos.y;
            float w = size.x;
            float h = size.y;
            Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());

            Slider slider = (Slider) component;
            float value = slider.getValue();
            boolean vertical = Orientation.VERTICAL.equals(slider.getOrientation());
            Vector4f sliderInactiveColor = slider.getSliderColor();
            Vector4f sliderColor = slider.getSliderActiveColor();
            float cornerRadius = component.getCornerRadius();

            nvgBeginPath(context);
            nvgRoundedRect(context, x, y, w, h, cornerRadius);
            nvgFillColor(context, rgba(backgroundColor, colorA));
            nvgFill(context);

            float lx, rx, ty, by, px, py;
            if (vertical) {
                px = lx = rx = x + w / 2f;
                ty = y;
                by = y + h;
                py = by - (by - ty) * value / 100f;
            } else {
                py = ty = by = y + h / 2f;
                lx = x;
                rx = x + w;
                px = lx + (rx - lx) * value / 100f;
            }

            float sliderWidth = 4.0f;

            // draw inactive color
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, sliderWidth);
            nvgStrokeColor(context, rgba(sliderInactiveColor, colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, lx + 0.5f, by + 0.5f);
            nvgLineTo(context, rx - 0.5f, ty - 0.5f);
            nvgStroke(context);

            // draw active part
            nvgLineCap(context, NVG_ROUND);
            nvgLineJoin(context, NVG_ROUND);
            nvgStrokeWidth(context, sliderWidth);
            nvgStrokeColor(context, rgba(sliderColor, colorA));
            nvgBeginPath(context);
            nvgMoveTo(context, lx + 0.5f, by + 0.5f);
            nvgLineTo(context, px - 0.5f, py - 0.5f);
            nvgStroke(context);

            float sliderSize = slider.getSliderSize();
            // draw slider button
            float xx = px - sliderSize / 2f;
            float yy = py - sliderSize / 2f;

            nvgBeginPath(context);
            nvgRoundedRect(context, xx, yy, sliderSize, sliderSize, cornerRadius);
            nvgFillColor(context, rgba(sliderColor, colorA));
            nvgFill(context);

            NvgRenderUtils.drawRectStroke(context, xx + 0.5f, yy + 0.5f, sliderSize, sliderSize, sliderInactiveColor, cornerRadius, 1);
//            NvgRenderUtils.drawRectStroke(context, xx + 1.5f, yy + 1.5f, sliderSize - 1.5f, sliderSize - 1.5f, new Vector4f(0, 0, 0, 0.5f), cornerRadius, 1);

            Border border = component.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }
        }
        resetScissor(context);
    }

}
