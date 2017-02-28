package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;

/**
 * Layer one of base structures.
 * Holds layer containers which are used to hold all of other components.
 * <p>
 * Layer can be eventPassable - that's mean that current layer allow to
 * pass events to bottom layer if event wasn't handled
 * by components of this layer.
 * <p>
 * Also layer can be eventReceivable - that's mean that current layer
 * and all of it components can receive events.
 * For example {@link TooltipLayer} is eventPassable and isn't eventReceivable.
 *
 * @param <T> type of components for {@link LayerContainer}
 */
public class Layer<T extends Component> {
    /**
     * Used to hold all components of layer.
     */
    protected LayerContainer<T> container = new LayerContainer<>();
    /**
     * Parent frame.
     */
    private Frame frame;
    /**
     * Determines if  current layer allow to
     * pass events to bottom layer if event wasn't handled
     * by components of this layer.
     */
    private boolean eventPassable   = true;
    /**
     * Determines if current layer and all of it components can receive events.
     */
    private boolean eventReceivable = true;

    private boolean                 enabled;
    private boolean                 visible;
    private WindowSizeEventListener listener;


    /**
     * Default constructor.
     */
    public Layer() {
        initialize();
    }

    /**
     * Used to initialize layer and layer container.
     */
    private void initialize() {
        listener = new LayerContainerWindowSizeEventListener();
        container.getListenerMap().addListener(WindowSizeEvent.class, listener);
    }

    /**
     * Returns frame to which attached this layer.
     *
     * @return frame to which attached this layer.
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Used to attach layer to frame.
     *
     * @param frame frame to attach.
     */
    protected void setFrame(Frame frame) {
        if (!frame.getAllLayers().contains(this)) {
            frame.addLayer(this);
        }
        this.frame = frame;
    }

    /**
     * Returns component container.
     *
     * @return component container.
     */
    public LayerContainer<T> getContainer() {
        return container;
    }

    /**
     * Used to set custom layer container.
     *
     * @param container new layer container to set.
     */
    public void setContainer(LayerContainer<T> container) {
        this.container.getListenerMap().removeListener(WindowSizeEvent.class, listener);
        container.setSize(new Vector2f(this.container.getSize()));
        this.container = container;
        container.getListenerMap().addListener(WindowSizeEvent.class, listener);
    }

    /**
     * Returns true if layer is event passable.
     *
     * @return true if layer is event passable.
     */
    public boolean isEventPassable() {
        return eventPassable;
    }

    /**
     * Used to enable or disable passing events to bottom layer.
     *
     * @param eventPassable true/false to enable/disable passing events to bottom layer.
     */
    public void setEventPassable(boolean eventPassable) {
        this.eventPassable = eventPassable;
    }

    /**
     * Returns true if layer is event receivable.
     *
     * @return true if layer is event receivable.
     */
    public boolean isEventReceivable() {
        return eventReceivable;
    }

    /**
     * Used to enable or disable receiving events by layer.
     *
     * @param eventReceivable true/false to enable/disable receiving events by layer.
     */
    public void setEventReceivable(boolean eventReceivable) {
        this.eventReceivable = eventReceivable;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(container)
                .append(eventPassable)
                .append(eventReceivable)
//                .append(enabled)
//                .append(visible)
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

        Layer<?> layer = (Layer<?>) obj;

        return new EqualsBuilder()
                .append(eventPassable, layer.eventPassable)
                .append(eventReceivable, layer.eventReceivable)
//                .append(enabled, layer.enabled)
//                .append(visible, layer.visible)
                .append(container, layer.container)
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
                .append("container", container)
                .append("eventPassable", eventPassable)
                .append("eventReceivable", eventReceivable)
//                .append("enabled", enabled)
//                .append("visible", visible)
                .toString();
    }

    public static class LayerContainerWindowSizeEventListener implements WindowSizeEventListener {
        @Override
        public void process(WindowSizeEvent event) {
            event.getComponent().getSize().set(event.getWidth(), event.getHeight());
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
