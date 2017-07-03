package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.image.Image;

import java.util.List;

/**
 * LeguiRenderer provider. Used to provide specific renderers for main renderer.
 */
public abstract class LeguiRendererProvider {
    public static void setRendererProvider(LeguiRendererProvider provider) {
        RPH.I = provider;
    }

    public static LeguiRendererProvider getInstance() {
        return RPH.I;
    }

    public abstract <C extends Component> LeguiComponentRenderer getComponentRenderer(Class<C> componentClass);

    public abstract <C extends Border> LeguiBorderRenderer getBorderRenderer(Class<C> borderClass);

    public abstract <I extends Icon> LeguiIconRenderer getIconRenderer(Class<I> iconClass);

    public abstract <I extends Image> LeguiImageRenderer getImageRenderer(Class<I> imageClass);

    public abstract List<LeguiComponentRenderer> getComponentRenderers();

    private static class RPH {
        private static LeguiRendererProvider I = null;
    }


}
