package org.liquidengine.legui.system.renderer.nvg.comp;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class NvgLayerRenderer extends NvgComponentRenderer<Layer> {
    @Override
    protected void renderComponent(Layer layer, Context context, long nanovg) {
        if (!layer.isVisible()) return;
        List<Component> all       = layer.getChilds();
        for (Component child : all) {
            RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
        }
    }
}
