package org.liquidengine.legui.renderer;

import org.liquidengine.legui.component.Component;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class LeguiRendererProvider {
    private static class RPH {
        public static LeguiRendererProvider I = new DefaultRendererProvider();
    }

    public static void setRendererProvider(LeguiRendererProvider provider) {
        RPH.I = provider;
    }

    public static LeguiRendererProvider getInstance() {
        return RPH.I;
    }

    public abstract <C extends Component>LeguiComponentRenderer getRenderer(Class<C> componentClass);

}
