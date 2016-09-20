package org.liquidengine.legui.render;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.render.nvg.NvgRendererProvider;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public abstract class LeguiRendererProvider {

    public static LeguiRendererProvider getProvider() {
        return PH.PROVIDER;
    }

    public static void setProvider(LeguiRendererProvider provider) {
        PH.PROVIDER = provider;
    }

    public abstract LeguiComponentRenderer getRenderer(Component component);

    private static final class PH {
        private static LeguiRendererProvider PROVIDER = new NvgRendererProvider();
    }
}
