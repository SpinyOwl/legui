package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.system.renderer.ComponentRenderer;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.comp.NvgDefaultRenderer;
import org.liquidengine.legui.system.renderer.nvg.comp.NvgLayerRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgRendererProvider extends RendererProvider {
    private Map<Class<? extends Component>, NvgComponentRenderer<? extends Component>> componentRendererMap = new ConcurrentHashMap<>();
    private NvgComponentRenderer defaultRenderer = new NvgDefaultRenderer();

    public NvgRendererProvider() {
        componentRendererMap.put(Layer.class, new NvgLayerRenderer());
    }

    @Override
    public <C extends Component> ComponentRenderer getComponentRenderer(Class<C> componentClass) {
        return componentRendererMap.getOrDefault(componentClass, defaultRenderer);
    }

    @Override
    public List<ComponentRenderer> getComponentRenderers() {
        return new ArrayList<>(componentRendererMap.values());
    }
}
