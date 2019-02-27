package org.liquidengine.legui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;

/**
 * Default structure which should be associated with OpenGL window. Contains two default layers: <ul> <li>Component layer - holds components and always on
 * bottom of all layers.</li> <li>Tooltip layer - holds tooltips and always on top of all layers.</li> </ul> <span style="color:red;">NOTE: layers processed in
 * reverse order - from top to bottom.</span>
 */
public class Frame {

    /**
     * Used to hold tooltips.
     */
    private TooltipLayer tooltipLayer;
    /**
     * Used to hold components.
     */
    private Layer<Component> componentLayer;
    /**
     * All other layers added to this list.
     */
    private List<Layer> layers = new CopyOnWriteArrayList<>();

    /**
     * Used to create frame and initialize layers with specified size.
     *
     * @param width width.
     * @param height height.
     */
    public Frame(float width, float height) {
        initialize(width, height);
    }

    /**
     * Default frame constructor.
     */
    public Frame() {
        initialize(10, 10);
    }

    /**
     * Used to create frame and initialize layer containers with specified size.
     *
     * @param size size.
     */
    public Frame(Vector2f size) {
        initialize(size.x, size.y);
    }

    /**
     * Used to initialize frame and layers.
     *
     * @param width initial layer containers width.
     * @param height initial layer containers height.
     */
    private void initialize(float width, float height) {
        tooltipLayer = new TooltipLayer();
        componentLayer = new ComponentLayer();
        tooltipLayer.setFrame(this);
        componentLayer.setFrame(this);
        setSize(width, height);
    }

    /**
     * Used to set layer containers size. NOTE: All LayerContainers will be resized to specified size!
     *
     * @param size frame size.
     */
    public void setSize(Vector2f size) {
        setSize(size.x, size.y);
    }

    /**
     * Used to set layer containers size. NOTE: All LayerContainers will be resized to specified size!
     *
     * @param width width.
     * @param height height.
     */
    public void setSize(float width, float height) {
        tooltipLayer.getContainer().setSize(width, height);
        componentLayer.getContainer().setSize(width, height);
        layers.forEach(l -> l.getContainer().setSize(width, height));
    }

    /**
     * Used to add layer to frame. <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @param layer layer to add.
     */
    public void addLayer(Layer layer) {
        if (layer == null ||
            layer == tooltipLayer ||
            layer == componentLayer) {
            return;
        }
        if (!containsLayer(layer) && layers.add(layer)) {
            changeFrame(layer);
        }
    }

    private void changeFrame(Layer layer) {
        Frame frame = layer.getFrame();
        if (frame == this) {
            return;
        }
        if (frame != null) {
            frame.removeLayer(layer);
        }
        layer.setFrame(this);
    }

    /**
     * Used to remove layer from frame.
     *
     * @param layer layer to remove.
     */
    public void removeLayer(Layer layer) {
        if (layer == null || layer == tooltipLayer || layer == componentLayer) {
            return;
        }
        Frame frame = layer.getFrame();
        if (frame == this && containsLayer(layer)) {
            boolean removed = layers.remove(layer);
            if (removed) {
                layer.setParent(null);
            }
        }
    }

    /**
     * Used to check if layer list contains provided layer.
     *
     * @param layer layer to check.
     *
     * @return true if layer list contains provided layer.
     */
    public boolean containsLayer(Layer layer) {
        return (layer != null) && ((layer == tooltipLayer) || (layer == componentLayer) || layers.stream().anyMatch(l -> l == layer));
    }

    /**
     * Used to retrieve default component layer. <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return default component layer.
     */
    public Layer<Component> getComponentLayer() {
        return componentLayer;
    }

    /**
     * Used to retrieve default tooltip layer. <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return default tooltip layer.
     */
    public TooltipLayer getTooltipLayer() {
        return tooltipLayer;
    }

    /**
     * Used to retrieve layers added by developer. <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return layers added by developer.
     */
    public List<Layer> getLayers() {
        return new ArrayList<>(layers);
    }

    /**
     * Used to retrieve all layers where <ul> <li><b>List[0]</b> - default component layer.</li> <li><b>List[1]-List[length-2]</b> - layers added by
     * developer.</li> <li><b>List[length-1]</b> - default tooltip layer.</li> </ul> <p> <span style="color:red;">NOTE: layers processed in reverse order - from
     * top to bottom.</span>
     *
     * @return all layers.
     */
    public List<Layer> getAllLayers() {
        ArrayList<Layer> layerList = new ArrayList<>();
        layerList.add(componentLayer);
        layerList.addAll(this.layers);
        layerList.add(tooltipLayer);
        return layerList;
    }

    /**
     * Used to retrieve container of default component layer.
     *
     * @return container of default component layer.
     */
    public Component getContainer() {
        return componentLayer.getContainer();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(tooltipLayer)
            .append(componentLayer)
            .append(layers)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Frame frame = (Frame) obj;

        return new EqualsBuilder()
            .append(tooltipLayer, frame.tooltipLayer)
            .append(componentLayer, frame.componentLayer)
            .append(layers, frame.layers)
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("tooltipLayer", tooltipLayer)
            .append("componentLayer", componentLayer)
            .append("layers", layers)
            .toString();
    }
}
