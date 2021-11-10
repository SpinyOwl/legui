package com.spinyowl.legui.system.renderer;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.image.Image;
import com.spinyowl.legui.style.Border;
import com.spinyowl.legui.system.renderer.nvg.NvgRendererProvider;
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

  <C extends Component, R extends ComponentRenderer<C>> void addComponentRenderer(
      Class<C> componentClass, R renderer);

  <C extends Border, R extends BorderRenderer<C>> void addBorderRenderer(Class<C> borderClass,
      R renderer);

  <I extends Icon, R extends IconRenderer<I>> void addIconRenderer(Class<I> iconClass, R renderer);

  <I extends Image, R extends ImageRenderer<I>> void addImageRenderer(Class<I> imageClass,
      R renderer);

  List<ComponentRenderer> getComponentRenderers();

  class RPH {

    private static RendererProvider instance = NvgRendererProvider.getInstance();

    private RPH() {
    }
  }


}
