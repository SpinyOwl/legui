package org.liquidengine.legui.component;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public class LayerFrame extends ComponentContainer {
    protected Layer layer;

    protected LayerFrame(Layer layer) {
        this.layer = layer;
    }

    public Layer getLayer() {
        return layer;
    }
}
