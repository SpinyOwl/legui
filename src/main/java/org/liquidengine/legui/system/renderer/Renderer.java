package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.system.context.Context;

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
            RendererProvider.getInstance().getComponentRenderer(layer.getContainer().getClass()).render(layer.getContainer(), context);
        }
        postRender();
    }

    public abstract void destroy();

    public Context getContext() {
        return context;
    }
}
