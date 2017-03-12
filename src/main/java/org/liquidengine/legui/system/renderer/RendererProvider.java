package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;

import java.util.List;

/**
 * Renderer provider. Used to provide specific renderers for main renderer.
 */
public abstract class RendererProvider {
    public static void setRendererProvider(RendererProvider provider) {
        RPH.I = provider;
    }

    public static RendererProvider getInstance() {
        return RPH.I;
    }

    public abstract <C extends Component> ComponentRenderer getComponentRenderer(Class<C> componentClass);

    public abstract <C extends Border> BorderRenderer getBorderRenderer(Class<C> borderClass);

    public abstract <C extends Icon> IconRenderer getIconRenderer(Class<C> iconClass);

    public abstract List<ComponentRenderer> getComponentRenderers();

    private static class RPH {
        private static RendererProvider I = null;
    }


}
