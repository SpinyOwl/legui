package org.liquidengine.legui.renderer;

import org.liquidengine.legui.component.LayerFrame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.Frame;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public abstract class LeguiRenderer {

    public abstract void initialize();

    public void render(Frame display) {
        for (Layer layer : display.getAllLayers()) {
            LeguiRendererProvider.getInstance().getRenderer(LayerFrame.class).render(layer.getLayerFrame());
        }
    }

    public abstract void destroy();

}
