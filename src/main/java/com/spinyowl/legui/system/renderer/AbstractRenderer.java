package com.spinyowl.legui.system.renderer;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.system.context.Context;

/**
 * Base of main renderer which called by renderer thread.
 */
public abstract class AbstractRenderer implements Renderer {

  protected abstract void preRender(Context context);

  protected abstract void postRender(Context context);

  public void render(Frame display, Context context) {
    preRender(context);
    for (Layer layer : display.getAllLayers()) {
      RendererProvider.getInstance().getComponentRenderer(layer.getClass()).render(layer, context);
    }
    postRender(context);
  }

}
