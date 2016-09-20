package org.liquidengine.legui.render.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public class NvgRendererProvider extends LeguiRendererProvider {
    private Map<Class<? extends Component>, LeguiComponentRenderer> rendererMap = new ConcurrentHashMap<>();
    private NvgDefaultRenderer renderer = new NvgDefaultRenderer();

    @Override
    public LeguiComponentRenderer getRenderer(Component component) {
        return rendererMap.getOrDefault(component.getClass(), renderer);
    }

    public void registerRenderer(Class<? extends Component> clazz, NvgLeguiComponentRenderer renderer) {
        rendererMap.put(clazz, renderer);
    }
}
