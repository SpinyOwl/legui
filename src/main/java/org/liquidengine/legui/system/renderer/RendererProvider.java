package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
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

    public abstract List<ComponentRenderer> getComponentRenderers();

    private static class RPH {
        private static RendererProvider I = null;
    }


}
