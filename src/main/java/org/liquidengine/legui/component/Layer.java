package org.liquidengine.legui.component;

public class Layer<T extends Component> {
    protected LayerFrame<T> layerFrame;
    protected Layer      bottomLayer;
    protected Layer      topLayer;
    protected Frame      display;

    public Layer() {
        initialize();
    }

    private void initialize() {
        layerFrame = new LayerFrame<>(this);
    }

    public Frame getDisplay() {
        return display;
    }

    public Layer getBottomLayer() {
        return bottomLayer;
    }

    public Layer getTopLayer() {
        return topLayer;
    }

    public ComponentContainer getLayerFrame() {
        return layerFrame;
    }
}
