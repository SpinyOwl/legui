package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.LayerFrame;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class LeguiRenderer {
    protected LeguiContext context;

    public LeguiRenderer(LeguiContext context) {
        this.context = context;
    }

    public abstract void initialize();

    public abstract void preRender();

    public abstract void postRender();

    public void render(Frame display) {
        preRender();
        for (Layer layer : display.getAllLayers()) {
            LeguiRendererProvider.getInstance().getComponentRenderer(LayerFrame.class).render(layer.getLayerFrame());
        }
        postRender();
    }

    public abstract void destroy();

    public LeguiContext getContext() {
        return context;
    }
}
