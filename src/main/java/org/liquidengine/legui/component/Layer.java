package org.liquidengine.legui.component;

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

    private boolean enabled;
    private boolean visible;


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
        container.getListenerMap().addListener(WindowSizeEvent.class, (WindowSizeEventListener) event -> container.getSize().set(event.getWidth(), event.getHeight()));
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
}
