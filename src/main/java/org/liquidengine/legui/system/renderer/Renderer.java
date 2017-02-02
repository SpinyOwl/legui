package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.system.context.Context;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class Renderer {
    protected Context context;

    public Renderer(Context context) {
        this.context = context;
    }

    public abstract void initialize();

    public abstract void preRender();

    public abstract void postRender();

    public void render(Frame display) {
        preRender();
        for (Layer layer : display.getAllLayers()) {
            ComponentContainer layerFrame = layer.getLayerFrame();
            RendererProvider.getInstance().getComponentRenderer(LayerFrame.class).render(layerFrame, context);
        }
        postRender();
    }

    public abstract void destroy();

    public Context getContext() {
        return context;
    }
}
