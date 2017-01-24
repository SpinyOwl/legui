package org.liquidengine.legui.renderer;

import org.liquidengine.legui.component.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public class DefaultRendererProvider extends LeguiRendererProvider {
    private Map<Class<? extends Component>, LeguiComponentRenderer<? extends Component>> rendererMap = new ConcurrentHashMap<>();

    @Override
    public <C extends Component> LeguiComponentRenderer getRenderer(Class<C> componentClass) {
        return rendererMap.get(componentClass);
    }

    public <C extends Component> void registerRenderer(Class<C> componentClass, LeguiComponentRenderer<C> renderer) {
        if (componentClass != null && renderer != null) {
            rendererMap.put(componentClass, renderer);
        }
    }
}
