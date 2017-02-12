package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NVGUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgDefaultComponentRenderer extends NvgComponentRenderer {

    @Override
    protected void renderComponent(Component component, Context context, long nanovg) {
        NvgRenderUtils.createScissor(nanovg, component);
        {
            Vector2f p = component.getScreenPosition();
            Vector2f s = component.getSize();

            Vector4f color = component.getBackgroundColor();
            float    x     = p.x;
            float    y     = p.y;
            float    w     = s.x;
            float    h     = s.y;


            if (component instanceof Controller) {
                Controller controller = (Controller) component;
                if (controller.isPressed()) {
                    drawRectBackground(nanovg, new Vector4f(color).div(2), x, y, w, h);
                } else if (controller.isHovered()) {
                    drawRectBackground(nanovg, new Vector4f(color).div(4).mul(3), x, y, w, h);
                } else {
                    drawRectBackground(nanovg, color, x, y, w, h);
                }
            } else {
                drawRectBackground(nanovg, color, x, y, w, h);
            }

            renderBorder(component, context);

        }
        NvgRenderUtils.resetScissor(nanovg);

        if (component instanceof Container) {
            Container       container = (Container) component;
            List<Component> all       = container.getChilds();
            for (Component child : all) {
                RendererProvider.getInstance().
                        getComponentRenderer(child.getClass()).render(child, context);
            }
        }
    }

    private void drawRectBackground(long nanovg, Vector4f color, float x, float y, float w, float h) {
        NVGColor nvgColor = NVGColor.calloc();
        NVGUtils.rgba(color, nvgColor);
        nvgBeginPath(nanovg);
        nvgFillColor(nanovg, nvgColor);
        nvgRect(nanovg, x, y, w, h);
        nvgFill(nanovg);
        nvgColor.free();
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
