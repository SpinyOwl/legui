package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by Aliaksandr_Shcherbin on 2/15/2017.
 */
public class NvgContainerRenderer extends NvgComponentRenderer<Container> {

    @Override
    protected void renderComponent(Container component, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, component, () ->
            NvgShapes.drawRect(nanovg, component.getAbsolutePosition(), component.getSize(), component.getBackgroundColor(), component.getCornerRadius())
        );

        List<Component> all = component.getChilds();
        for (Component child : all) {
            RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
        }

        renderBorderWScissor(component, context, nanovg);
    }
}
