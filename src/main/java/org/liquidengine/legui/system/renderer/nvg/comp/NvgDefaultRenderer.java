package org.liquidengine.legui.system.renderer.nvg.comp;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtil;
import org.liquidengine.legui.system.renderer.nvg.util.NvgUtil;
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgDefaultRenderer extends NvgComponentRenderer {

    @Override
    protected void renderComponent(Component component, Context context, long nanovg) {
        if(!component.isVisible()) return;
        NvgRenderUtil.createScissor(nanovg, component);
        {
            Vector2f p = component.getScreenPosition();
            Vector2f s = component.getSize();

            Vector4f color = component.getBackgroundColor();

            NVGColor nvgColor = NVGColor.calloc();
            NvgUtil.rgba(color, nvgColor);

            nvgBeginPath(nanovg);
            nvgFillColor(nanovg, nvgColor);
            nvgRect(nanovg, p.x, p.y, s.x, s.y);
            nvgFill(nanovg);
            nvgColor.free();
        }
        NvgRenderUtil.resetScissor(nanovg);

        if (component instanceof Container) {
            Container       container = (Container) component;
            List<Component> all       = container.getChilds();
            for (Component child : all) {
                RendererProvider.getInstance().
                        getComponentRenderer(child.getClass()).render(child, context);
            }
        }
    }
}
