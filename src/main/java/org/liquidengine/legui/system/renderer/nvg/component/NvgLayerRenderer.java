package org.liquidengine.legui.system.renderer.nvg.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class NvgLayerRenderer extends NvgComponentRenderer<LayerContainer> {
    @Override
    protected void renderComponent(LayerContainer layer, Context context, long nanovg) {
        List<Component> all       = layer.getChilds();
        for (Component child : all) {
            RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
        }
    }
}
