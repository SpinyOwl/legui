package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;

import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgDefaultComponentRenderer extends NvgComponentRenderer {

    @Override
    protected void renderComponent(Component component, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, component, () -> {
            Vector2f pos = component.getScreenPosition();
            Vector2f size = component.getSize();
            Vector4f color = component.getBackgroundColor();
            float cornerRadius = component.getCornerRadius();

            if (component.isPressed()) {
                NvgShapes.drawRect(nanovg, pos, size, new Vector4f(color).div(2), cornerRadius);
            } else if (component.isHovered()) {
                NvgShapes.drawRect(nanovg, pos, size, new Vector4f(color).div(4).mul(3), cornerRadius);
            } else {
                NvgShapes.drawRect(nanovg, pos, size, color, cornerRadius);
            }
        });

        if (component instanceof Container) {
            Container container = (Container) component;
            List<Component> all = container.getChilds();
            for (Component child : all) {
                RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
            }
        }

        renderBorderWScissor(component, context, nanovg);
    }

}
