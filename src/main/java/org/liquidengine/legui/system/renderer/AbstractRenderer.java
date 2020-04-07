package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.system.context.Context;

/**
 * Base of main renderer which called by renderer thread.
 */
public abstract class AbstractRenderer implements Renderer {

    protected abstract void preRender(Context context);

    protected abstract void postRender(Context context);

    public void render(Frame display, Context context) {
        preRender(context);
        for (Layer layer : display.getAllLayers()) {
            LayerContainer container = layer.getContainer();
            RendererProvider.getInstance().getComponentRenderer(container.getClass()).render(container, context);
        }
        postRender(context);
    }

}
