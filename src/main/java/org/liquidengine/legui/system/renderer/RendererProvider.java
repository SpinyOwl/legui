package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.style.Border;
import org.liquidengine.legui.system.renderer.nvg.NvgRendererProvider;

import java.util.List;

/**
 * Renderer provider. Used to provide specific renderers for main renderer.
 */
public interface RendererProvider {

    static void setRendererProvider(RendererProvider provider) {
        RPH.instance = provider;
    }

    static RendererProvider getInstance() {
        return RPH.instance;
    }

    <C extends Component> ComponentRenderer getComponentRenderer(Class<C> componentClass);

    <C extends Border> BorderRenderer getBorderRenderer(Class<C> borderClass);

    <I extends Icon> IconRenderer getIconRenderer(Class<I> iconClass);

    <I extends Image> ImageRenderer getImageRenderer(Class<I> imageClass);

    <C extends Component, R extends ComponentRenderer<C>> void addComponentRenderer(Class<C> componentClass, R renderer);

    <C extends Border, R extends BorderRenderer<C>> void addBorderRenderer(Class<C> borderClass, R renderer);

    <I extends Icon, R extends IconRenderer<I>> void addIconRenderer(Class<I> iconClass, R renderer);

    <I extends Image, R extends ImageRenderer<I>> void addImageRenderer(Class<I> imageClass, R renderer);

    List<ComponentRenderer> getComponentRenderers();

    class RPH {
        private static RendererProvider instance = NvgRendererProvider.getInstance();
        private RPH() {}
    }


}
