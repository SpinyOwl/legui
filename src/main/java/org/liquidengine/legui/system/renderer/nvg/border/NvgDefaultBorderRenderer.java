package org.liquidengine.legui.system.renderer.nvg.border;

import org.joml.Vector2f;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NVGUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgDefaultBorderRenderer extends NvgBorderRenderer {
    @Override
    protected void renderBorder(Border border, Component component, Context context, long nanovg) {
        if (!component.isVisible()) return;
        // render simple rectangle border
        Vector2f position = component.getScreenPosition();
        Vector2f size = component.getSize();

        float x = position.x;
        float y = position.y;
        float w = size.x;
        float h = size.y;


        drawRectStroke(component, nanovg, x, y, w, h, new SimpleLineBorder(ColorConstants.black(), 1));

        if (component instanceof Controller) {
            Controller controller = (Controller) component;
            if (controller.isFocused()) {
                drawRectStroke(component, nanovg, x - 1, y - 1, w + 2, h + 2, new SimpleLineBorder(ColorConstants.red(), 2));
            }
        }
    }

    private void drawRectStroke(Component component, long nanovg, float x, float y, float w, float h, SimpleLineBorder b) {
        NVGColor nvgColor;
        nvgColor = NVGColor.calloc();
        nvgBeginPath(nanovg);
        nvgStrokeWidth(nanovg, b.getThickness());
        nvgRoundedRect(nanovg, x, y, w, h, component.getCornerRadius());
        nvgStrokeColor(nanovg, NVGUtils.rgba(b.getColor(), nvgColor));
        nvgStroke(nanovg);
        nvgColor.free();
    }
}
