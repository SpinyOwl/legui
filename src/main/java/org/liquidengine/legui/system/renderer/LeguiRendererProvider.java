package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class LeguiRendererProvider {
    public static void setRendererProvider(LeguiRendererProvider provider) {
        RPH.I = provider;
    }

    public static LeguiRendererProvider getInstance() {
        return RPH.I;
    }

    public abstract <C extends Component> LeguiComponentRenderer getComponentRenderer(Class<C> componentClass);

    public abstract List<LeguiComponentRenderer> getComponentRenderers();

    private static class RPH {
        private static LeguiRendererProvider I = null;
    }


}
