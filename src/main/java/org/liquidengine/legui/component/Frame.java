package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default structure which should be associated with OpenGL window.
 * Contains two default layers:
 * <ul>
 * <li>Component layer - holds components and always on bottom of all layers</li>
 * <li>Tooltip layer - holds tooltips and always on top of all layers.</li>
 * </ul>
 * <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
 */
public class Frame {
    /**
     * Used to hold tooltips.
     */
    private TooltipLayer   tooltipLayer;
    /**
     * Used to hold components.
     */
    private ComponentLayer componentLayer;
    /**
     * All other layers added to this list.
     */
    private List<Layer> layers = new CopyOnWriteArrayList<>();

    /**
     * Used to create frame and initialize layers with specified size.
     *
     * @param width  width
     * @param height height
     */
    public Frame(float width, float height) {
        initialize(width, height);
    }

    /**
     * Default frame constructor.
     */
    public Frame() {
        initialize(0, 0);
    }

    /**
     * Used to create frame and initialize layers with specified size.
     *
     * @param size size
     */
    public Frame(Vector2f size) {
        initialize(size.x, size.y);
    }

    /**
     * Used to initialize frame and layers.
     *
     * @param width  initial window width
     * @param height initial window height
     */
    private void initialize(float width, float height) {
        tooltipLayer = new TooltipLayer();
        componentLayer = new ComponentLayer();
        tooltipLayer.setFrame(this);
        componentLayer.setFrame(this);
        setSize(width, height);
    }

    /**
     * Used to set frame size (if frame created with default constructor)
     *
     * @param size frame size.
     */
    public void setSize(Vector2f size) {
        setSize(size.x, size.y);
    }

    /**
     * Used to set frame size (if frame created with default constructor)
     *
     * @param width  width
     * @param height height
     */
    public void setSize(float width, float height) {
        tooltipLayer.getContainer().setSize(width, height);
        componentLayer.getContainer().setSize(width, height);
        layers.forEach(l -> l.getContainer().setSize(width, height));
    }

    /**
     * Used to add layer to frame.
     * <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @param layer layer to add.
     */
    public void addLayer(Layer layer) {
        if (layer == null ||
                layer == tooltipLayer ||
                layer == componentLayer ||
                layer.getFrame() == this) {
            return;
        }

        if (layer.getFrame() != null) {
            layer.getFrame().removeLayer(layer);
        }
        layer.setFrame(this);
        layers.add(layer);
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
        layer.setFrame(this);
        if (layers.contains(layer)) {
            layers.remove(layer);
        }
    }

    /**
     * Used to retrieve default component layer.
     * <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return default component layer.
     */
    public ComponentLayer getComponentLayer() {
        return componentLayer;
    }

    /**
     * Used to retrieve default tooltip layer.
     * <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return default tooltip layer.
     */
    public TooltipLayer getTooltipLayer() {
        return tooltipLayer;
    }

    /**
     * Used to retrieve layers added by developer.
     * <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return layers added by developer.
     */
    public List<Layer> getLayers() {
        return new ArrayList<>(layers);
    }

    /**
     * Used to retrieve all layers where
     * <ul>
     * <li><b>List[0]</b> - default component layer.</li>
     * <li><b>List[1]-List[length-2]</b> - layers added by developer.</li>
     * <li><b>List[length-1]</b> - default tooltip layer.</li>
     * </ul>
     * <p>
     * <span style="color:red;">NOTE: layers processed in reverse order - from top to bottom.</span>
     *
     * @return all layers.
     */
    public List<Layer> getAllLayers() {
        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(componentLayer);
        layers.addAll(this.layers);
        layers.add(tooltipLayer);
        return layers;
    }

    /**
     * Used to retrieve container of default component layer.
     *
     * @return container of default component layer.
     */
    public Container getContainer() {
        return componentLayer.getContainer();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tooltipLayer)
                .append(componentLayer)
                .append(layers)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Frame frame = (Frame) obj;

        return new EqualsBuilder()
                .append(tooltipLayer, frame.tooltipLayer)
                .append(componentLayer, frame.componentLayer)
                .append(layers, frame.layers)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("tooltipLayer", tooltipLayer)
                .append("componentLayer", componentLayer)
                .append("layers", layers)
                .toString();
    }
}
