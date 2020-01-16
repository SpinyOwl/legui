package org.liquidengine.legui.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.listener.ListenerMap;

/**
 * Layer one of base structures. Holds layer containers which are used to hold all of other components. <p> Layer can be eventPassable - that's mean that
 * current layer allow to pass events to bottom layer if event wasn't handled by components of this layer. <p> Also layer can be eventReceivable - that's mean
 * that current layer and all of it components can receive events. For example {@link TooltipLayer} is eventPassable and isn't eventReceivable.
 *
 * @param <T> type of components for {@link LayerContainer}
 */
public class Layer<T extends Component> {

    /**
     * Used to hold all components of layer.
     */
    protected LayerContainer container = new LayerContainer();
    /**
     * Parent frame.
     */
    private Frame frame;
    /**
     * Determines if  current layer allow to pass events to bottom layer if event wasn't handled by components of this layer.
     */
    private boolean eventPassable = true;
    /**
     * Determines if current layer and all of it components can receive events.
     */
    private boolean eventReceivable = true;

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
        if (frame == this.frame) {
            return;
        }
        if (this.frame != null) {
            this.frame.removeLayer(this);
        }
        this.frame = frame;
        if (frame != null) {
            frame.addLayer(this);
        }
    }

    /**
     * Returns component container.
     *
     * @return component container.
     */
    public LayerContainer getContainer() {
        return container;
    }

    /**
     * Used to set custom layer container.
     *
     * @param container new layer container to set.
     */
    public void setContainer(LayerContainer container) {
        container.setSize(new Vector2f(this.container.getSize()));
        this.container = container;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getContainer())
            .append(isEventPassable())
            .append(isEventReceivable())
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

        Layer<?> layer = (Layer<?>) obj;

        return new EqualsBuilder()
            .append(isEventPassable(), layer.isEventPassable())
            .append(isEventReceivable(), layer.isEventReceivable())
            .append(getContainer(), layer.getContainer())
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("container", getContainer())
            .append("eventPassable", isEventPassable())
            .append("eventReceivable", isEventReceivable())
            .toString();
    }

    /**
     * Returns parent component. If returns null - current component is root component.
     *
     * @return null or parent component.
     */
    public Component getParent() {
        return container.getParent();
    }

    /**
     * Used to set parent component. By default used by containers to attach component to container. Parent component used by renderers and event listeners and
     * processors. <p> Don't use this method if you want to attach component to container. In this case use {@link Component#add(Component)} method.
     *
     * @param parent component container.
     */
    public void setParent(Component parent) {
        container.setParent(parent);
    }

    /**
     * Returns event listeners for component instance.
     *
     * @return event listeners map.
     */
    public ListenerMap getListenerMap() {
        return container.getListenerMap();
    }

    /**
     * Used to set event listener map for component.
     *
     * @param listenerMap map of event listeners.
     */
    public void setListenerMap(ListenerMap listenerMap) {
        container.setListenerMap(listenerMap);
    }

    /**
     * Returns position vector. Be careful during changing this vector.
     *
     * @return position vector.
     */
    public Vector2f getPosition() {
        return container.getPosition();
    }

    /**
     * Used to set position of component.
     *
     * @param position new position for component.
     */
    public void setPosition(Vector2f position) {
        container.setPosition(position);
    }

    /**
     * Used to set current position.
     *
     * @param x x position relative to parent component.
     * @param y y position relative to parent component.
     */
    public void setPosition(float x, float y) {
        container.setPosition(x, y);
    }

    /**
     * Returns size vector of component. So to get width you can use.
     * <pre>
     * {@code
     * Vector2f size = component.getSize();
     * float width = size.x;
     * float height = size.y;
     * }
     * </pre>
     *
     * @return size of component.
     */
    public Vector2f getSize() {
        return container.getSize();
    }

    /**
     * Used to set size vector.
     *
     * @param size size vector.
     */
    public void setSize(Vector2f size) {
        container.setSize(size);
    }

    /**
     * Used to set size vector.
     *
     * @param width width to set.
     * @param height height to set.
     */
    public void setSize(float width, float height) {
        container.setSize(width, height);
    }

    /**
     * Returns absolute component position.
     *
     * @return position vector.
     */
    public Vector2f getAbsolutePosition() {
        return container.getAbsolutePosition();
    }

    /**
     * Returns true if component enabled. By default if component enabled it receives and proceed events.
     *
     * @return true if component enabled. default value is {@link Boolean#TRUE}.
     */
    public boolean isEnabled() {
        return container.isEnabled();
    }

    /**
     * Used to enable or disable component. By default if component enabled it receives and proceed events.
     *
     * @param enabled flag to set.
     */
    public void setEnabled(boolean enabled) {
        container.setEnabled(enabled);
    }

    /**
     * Returns true if component visible. By default if component visible it will be rendered and will receive events.
     *
     * @return true if component visible. default value is {@link Boolean#TRUE}.
     */
    public boolean isVisible() {
        return container.isVisible();
    }

    /**
     * Used to determine if point intersects component (in screen space). This method uses component intersector.
     *
     * @param point point to check.
     *
     * @return true if component intersected by point.
     */
    public boolean intersects(Vector2f point) {
        return container.intersects(point);
    }

    /**
     * Returns component intersector which used to check if cursor intersect component or not.
     *
     * @return intersector.
     */
    public Intersector getIntersector() {
        return container.getIntersector();
    }

    /**
     * Used to set intersector for component.
     *
     * @param intersector intersector.
     */
    public void setIntersector(Intersector intersector) {
        container.setIntersector(intersector);
    }

    /**
     * Returns component metadata. Storage of some temporary statements. Can be used for example by stateless renderers.
     *
     * @return map of objects.
     */
    public Map<String, Object> getMetadata() {
        return container.getMetadata();
    }

    /**
     * Returns true if component is hovered.
     *
     * @return true if component is hovered.
     */
    public boolean isHovered() {
        return container.isHovered();
    }

    /**
     * Used to make component hovered or not.
     *
     * @param hovered new hovered value.
     */
    public void setHovered(boolean hovered) {
        container.setHovered(hovered);
    }

    /**
     * Returns true if component is focused.
     *
     * @return true if component is focused.
     */
    public boolean isFocused() {
        return container.isFocused();
    }

    /**
     * Used to make component focused or not.
     *
     * @param focused new hovered value.
     */
    public void setFocused(boolean focused) {
        container.setFocused(focused);
    }

    /**
     * Returns true if component is pressed.
     *
     * @return true if component is pressed.
     */
    public boolean isPressed() {
        return container.isPressed();
    }

    /**
     * Used to make component pressed or not.
     *
     * @param pressed new hovered value.
     */
    public void setPressed(boolean pressed) {
        container.setPressed(pressed);
    }

    /**
     * Returns tooltip if it persist.
     *
     * @return tooltip.
     */
    public Tooltip getTooltip() {
        return container.getTooltip();
    }

    /**
     * Used to set tooltip to component.
     *
     * @param tooltip tooltip to set.
     */
    public void setTooltip(Tooltip tooltip) {
        container.setTooltip(tooltip);
    }

    /**
     * Returns count of child components.
     *
     * @return count of child components.
     *
     * @see List#size()
     */
    public int count() {
        return container.count();
    }

    /**
     * Returns true if layerFrame contains no elements.
     *
     * @return true if layerFrame contains no elements.
     *
     * @see List#isEmpty()
     */
    public boolean isEmpty() {
        return container.isEmpty();
    }

    /**
     * Returns true if layerFrame contains specified component.
     *
     * @param component component to check.
     *
     * @return true if layerFrame contains specified component.
     *
     * @see List#contains(Object)
     */
    public boolean contains(T component) {
        return container.contains(component);
    }

    /**
     * Returns an iterator over the elements in this layerFrame. The elements are returned in no particular order.
     *
     * @return an iterator over the elements in this layerFrame.
     *
     * @see List#iterator()
     */
    public Iterator<Component> containerIterator() {
        return container.containerIterator();
    }

    /**
     * Used to add component to layerFrame.
     *
     * @param component component to add.
     *
     * @return true if component is added.
     *
     * @see List#add(Object)
     */
    public boolean add(T component) {
        return container.add(component);
    }

    /**
     * Used to add components.
     *
     * @param components components nodes to add.
     */
    public void addAll(Collection<? extends Component> components) {
        container.addAll(components);
    }

    /**
     * Used to remove component.
     *
     * @param component component to remove.
     *
     * @return true if removed.
     */
    public boolean remove(T component) {
        return container.remove(component);
    }

    /**
     * Used to remove components.
     *
     * @param components components to remove.
     *
     * @see List#removeAll(Collection)
     */
    public void removeAll(Collection<? extends Component> components) {
        container.removeAll(components);
    }

    /**
     * Removes all of the elements of this layerFrame that satisfy the given predicate. Errors or runtime exceptions thrown during iteration or by the predicate
     * are relayed to the caller.
     *
     * @param filter a predicate which returns true for elements to be removed.
     *
     * @see List#removeIf(Predicate)
     */
    public void removeIf(Predicate<? super Component> filter) {
        container.removeIf(filter);
    }

    /**
     * Used to remove all child components from layerFrame.
     *
     * @see List#clear()
     */
    public void clearChildComponents() {
        container.clearChildComponents();
    }

    /**
     * Returns true if this Container contains all of the elements of the specified collection.
     *
     * @param components components collection to check.
     *
     * @return true if this Container contains all of the elements of the specified collection.
     *
     * @see List#containsAll(Collection)
     */
    public boolean containsAll(Collection<Component> components) {
        return container.containsAll(components);
    }

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream with this collection as its source.
     *
     * @see List#stream()
     */
    public Stream<Component> stream() {
        return container.stream();
    }

    /**
     * Returns a possibly parallel Stream with this collection as its source. It is allowable for this method to return a sequential stream.
     *
     * @return possibly parallel Stream with this collection as its source.
     *
     * @see List#parallelStream()
     */
    public Stream<Component> parallelStream() {
        return container.parallelStream();
    }

    /**
     * Performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    public void forEach(Consumer<? super Component> action) {
        container.forEach(action);
    }

    /**
     * Used to retrieve child components as {@link List}. <p> <span style="color:red">NOTE: this method returns NEW {@link List} of components</span>.
     *
     * @return list of child components.
     */
    public List<Component> getChildComponents() {
        return container.getChildComponents();
    }
}
