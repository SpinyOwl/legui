package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.component.TabKeyEventListener;
import org.liquidengine.legui.component.misc.listener.component.TooltipCursorEnterListener;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.listener.ListenerMap;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.theme.Themes;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Component is an object that have graphical representation in legui system.
 */
public abstract class Component implements Serializable {
    ////////////////////////////////
    //// COMPONENT BASE DATA
    ////////////////////////////////
    /**
     * Metadata map, place where renderers or event processors can store state of component.
     */
    private Map<String, Object> metadata = new HashMap<>();
    /**
     * Parent component container. For root components it could be null.
     */
    private Component parent;
    /**
     * Map for UI event listeners.
     */
    private ListenerMap listenerMap = new ListenerMap();
    /**
     * Position of component relative top left corner in parent component.
     * <p>
     * If component is the root component then position calculated relative window top left corner.
     */
    private Vector2f position = new Vector2f();
    /**
     * Size of component.
     */
    private Vector2f size = new Vector2f();

    /**
     * Used to enable and disable event processing for this component. If enabled==false then component won't receive events.
     */
    private boolean enabled = true;
    /**
     * Intersector which used to determine for example if cursor intersects component or not. Cannot be null.
     */
    private Intersector intersector = new RectangleIntersector();
    /**
     * Determines whether this component hovered or not (cursor is over this component).
     */
    private boolean hovered;
    /**
     * Determines whether this component focused or not.
     */
    private boolean focused;
    /**
     * Determines whether this component pressed or not (Mouse button is down and on this component).
     */
    private boolean pressed;
    /**
     * Tooltip.
     */
    private Tooltip tooltip;

    /**
     * Tab index. Used to switch between components using tab key.
     */
    private int tabIndex;

    /**
     * Show if component can be focused by tabbing.
     */
    private boolean tabFocusable = true;

    /**
     * Show if component can be focused.
     * <br><b>Note! You should take in consideration that component that marked as non-focusable will not receive any events.
     * In fact this could be used to organize elements using containers.</b>
     */
    private boolean focusable = true;

    ////////////////////////////////
    //// CONTAINER BASE DATA
    ////////////////////////////////

    /**
     * Component style.
     */
    private Style style = new Style();
    /**
     * Component style.
     */
    private Style hoveredStyle = new Style();
    /**
     * Component style.
     */
    private Style focusedStyle = new Style();
    /**
     * Component style.
     */
    private Style pressedStyle = new Style();
    /**
     * List of child components.
     */
    private List<Component> childComponents = new CopyOnWriteArrayList<>();

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with Json marshaller/unmarshaller component should contain empty constructor.
     */
    public Component() {
        this(0, 0, 10, 10);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public Component(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public Component(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
        initialize();
    }

    ////////////////////////////////
    //// CONTAINER BASE DATA
    ////////////////////////////////

    public Style getFocusedStyle() {
        return focusedStyle;
    }

    public Style getHoveredStyle() {
        return hoveredStyle;
    }

    public Style getPressedStyle() {
        return pressedStyle;
    }

    /**
     * Returns component style.
     *
     * @return component style.
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Used to set component style.
     *
     * @param style component style to set.
     */
    public void setStyle(Style style) {
        if (style != null) {
            this.style = style;
        }
    }

    /**
     * Used to initialize component.
     */
    private void initialize() {
        getListenerMap().addListener(CursorEnterEvent.class, new TooltipCursorEnterListener());
        getListenerMap().addListener(KeyEvent.class, new TabKeyEventListener());
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Component.class).applyAll(this);
    }

    /**
     * Returns parent component. If returns null - current component is root component.
     *
     * @return null or parent component.
     */
    public Component getParent() {
        return parent;
    }

    /**
     * Used to set parent component. By default used by containers to attach component to container. Parent component used by renderers and event listeners and
     * processors.
     * <p>
     * Don't use this method if you want to attach component to container. In this case use {@link Component#add(Component)} method.
     *
     * @param parent component container.
     */
    public void setParent(Component parent) {
        if (parent == this) {
            return;
        }

        if (this.parent != null) {
            this.parent.remove(this);
        }
        this.parent = parent;
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Returns event listeners for component instance.
     *
     * @return event listeners map.
     */
    public ListenerMap getListenerMap() {
        return listenerMap;
    }

    /**
     * Used to set event listener map for component.
     *
     * @param listenerMap map of event listeners.
     */
    public void setListenerMap(ListenerMap listenerMap) {
        this.listenerMap = listenerMap;
    }

    /**
     * Returns position vector. Be careful during changing this vector.
     *
     * @return position vector.
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Used to set position of component.
     *
     * @param position new position for component.
     */
    public void setPosition(Vector2f position) {
        if (position != null) {
            this.position = position;
        } else {
            this.position.set(0);
        }
    }

    /**
     * Used to set current position.
     *
     * @param x x position relative to parent component.
     * @param y y position relative to parent component.
     */
    public void setPosition(float x, float y) {
        this.position.set(x, y);
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
        return size;
    }

    /**
     * Used to set size vector.
     *
     * @param size size vector.
     */
    public void setSize(Vector2f size) {
        if (size != null) {
            this.size = size;
        } else {
            this.size.set(0);
        }
    }

    /**
     * Used to set size vector.
     *
     * @param width  width to set.
     * @param height height to set.
     */
    public void setSize(float width, float height) {
        this.size.set(width, height);
    }

    /**
     * Returns absolute component position.
     *
     * @return position vector.
     */
    public Vector2f getAbsolutePosition() {
        Vector2f screenPos = new Vector2f(this.position);
        for (Component p = this.getParent(); p != null; p = p.getParent()) {
            screenPos.add(p.getPosition());
        }
        return screenPos;
    }

    /**
     * Returns true if component enabled. By default if component enabled it receives and proceed events.
     *
     * @return true if component enabled. default value is {@link Boolean#TRUE}.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Used to enable or disable component. By default if component enabled it receives and proceed events.
     *
     * @param enabled flag to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns true if component visible. By default if component visible it will be rendered and will receive events.
     *
     * @return true if component visible. default value is {@link Boolean#TRUE}.
     */
    public boolean isVisible() {
        return this.style.getDisplay() != Style.DisplayType.NONE;
    }

    /**
     * Used to determine if point intersects component (in screen space). This method uses component intersector.
     *
     * @param point point to check.
     * @return true if component intersected by point.
     */
    public boolean intersects(Vector2f point) {
        return intersector.intersects(this, point);
    }

    /**
     * Returns component intersector which used to check if cursor intersect component or not.
     *
     * @return intersector.
     */
    public Intersector getIntersector() {
        return intersector;
    }

    /**
     * Used to set intersector for component.
     *
     * @param intersector intersector.
     */
    public void setIntersector(Intersector intersector) {
        if (intersector == null) {
            return;
        }
        this.intersector = intersector;
    }

    /**
     * Returns component metadata. Storage of some temporary statements. Can be used for example by stateless renderers.
     *
     * @return map of objects.
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Returns true if component is hovered.
     *
     * @return true if component is hovered.
     */
    public boolean isHovered() {
        return hovered;
    }

    /**
     * Used to make component hovered or not.
     *
     * @param hovered new hovered value.
     */
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    /**
     * Returns true if component is focused.
     *
     * @return true if component is focused.
     */
    public boolean isFocused() {
        return focused;
    }

    /**
     * Used to make component focused or not.
     *
     * @param focused new hovered value.
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    /**
     * Returns true if component is pressed.
     *
     * @return true if component is pressed.
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Used to make component pressed or not.
     *
     * @param pressed new hovered value.
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     * Returns tooltip if it persist.
     *
     * @return tooltip.
     */
    public Tooltip getTooltip() {
        return tooltip;
    }

    /**
     * Used to set tooltip to component.
     *
     * @param tooltip tooltip to set.
     */
    public void setTooltip(Tooltip tooltip) {
        // check same tooltip
        if (this.tooltip == tooltip) {
            return;
        }
        // unbind current tooltip from this component
        if (this.tooltip != null) {
            Tooltip prev = this.tooltip;
            this.tooltip = null;
            prev.setComponent(null);
        }

        this.tooltip = tooltip;
        // bind component to tooltip
        if (tooltip != null) {
            tooltip.setComponent(this);
        }
    }

    /**
     * Returns tab index.
     *
     * @return tab index.
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Used to set tab index.
     *
     * @param tabIndex tab index.
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * Returns true if component focused by tabbing.
     *
     * @return true if component focused by tabbing.
     */
    public boolean isTabFocusable() {
        return tabFocusable;
    }

    /**
     * Used to set tab affecting for component.
     *
     * @param tabFocusable new tab affecting state.
     */
    public void setTabFocusable(boolean tabFocusable) {
        this.tabFocusable = tabFocusable;
    }

    /**
     * Returns true if component focused.
     *
     * @return true if component focused.
     */
    public boolean isFocusable() {
        return focusable;
    }

    /**
     * Used to set component focusable.
     * <br><b>Note! You should take in consideration that component that marked as non-focusable will not receive any events.
     * In fact this could be used to organize elements using containers.</b>
     *
     * @param focusable new focusable state.
     */
    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    /////////////////////////////////
    //// CONTAINER METHODS
    /////////////////////////////////

    /**
     * Returns count of child components.
     *
     * @return count of child components.
     * @see List#size()
     */
    public int count() {
        return childComponents.size();
    }

    /**
     * Returns true if component contains no elements.
     *
     * @return true if component contains no elements.
     * @see List#isEmpty()
     */
    public boolean isEmpty() {
        return childComponents.isEmpty();
    }

    /**
     * Returns true if component contains specified component.
     *
     * @param component component to check.
     * @return true if component contains specified component.
     * @see List#contains(Object)
     */
    public boolean contains(Component component) {
        return isContains(component);
    }

    /**
     * Returns an iterator over the elements in this component. The elements are returned in no particular order.
     *
     * @return an iterator over the elements in this component.
     * @see List#iterator()
     */
    public Iterator<Component> containerIterator() {
        return childComponents.iterator();
    }

    /**
     * Used to add component to component.
     *
     * @param component component to add.
     * @return true if component is added.
     * @see List#add(Object)
     */
    public boolean add(Component component) {
        if (component == null || component == this || isContains(component)) {
            return false;
        }
        boolean added = childComponents.add(component);
        if (added) {
            changeParent(component);
        }
        return added;
    }

    /**
     * Used to check if component collection contains component or not. Checked by reference.
     *
     * @param component component to check.
     * @return true if collection contains provided component.
     */
    private boolean isContains(Component component) {
        return childComponents.stream().anyMatch(c -> c == component);
    }

    /**
     * Used to add components.
     *
     * @param components components nodes to add.
     */
    public void addAll(Collection<? extends Component> components) {
        if (components != null) {
            components.forEach(this::add);
        }
    }

    /**
     * Used to change parent of added component.
     *
     * @param component component to change.
     */
    private void changeParent(Component component) {
        Component p = component.getParent();
        if (p == this) {
            return;
        }
        if (p != null) {
            p.remove(component);
        }
        component.setParent(this);
    }

    /**
     * Used to remove component.
     *
     * @param component component to remove.
     * @return true if removed.
     * @see List#remove(Object)
     */
    public boolean remove(Component component) {
        if (component != null) {
            Component p = component.getParent();
            if (p == this && isContains(component)) {
                boolean removed = childComponents.remove(component);
                if (removed) {
                    component.setParent(null);
                }
                return removed;
            }
        }
        return false;
    }

    /**
     * Used to remove components.
     *
     * @param components components to remove.
     * @see List#removeAll(Collection)
     */
    public void removeAll(Collection<? extends Component> components) {
        if (components != null) {
            components.forEach(this::remove);
        }
    }

    /**
     * Removes all of the elements of this component that satisfy the given predicate. Errors or runtime exceptions thrown during iteration or by the predicate
     * are relayed to the caller.
     *
     * @param filter a predicate which returns true for elements to be removed.
     * @see List#removeIf(Predicate)
     */
    public void removeIf(Predicate<? super Component> filter) {
        childComponents.stream().filter(filter).forEach(this::remove);
    }

    /**
     * Used to remove all child components from component.
     *
     * @see List#clear()
     */
    public void clearChildComponents() {
        childComponents.forEach(compo -> compo.setParent(null));
        childComponents.clear();
    }

    /**
     * Returns true if this Container contains all of the elements of the specified collection.
     *
     * @param components components collection to check.
     * @return true if this Container contains all of the elements of the specified collection.
     * @see List#containsAll(Collection)
     */
    public boolean containsAll(Collection<Component> components) {
        return this.childComponents.containsAll(components);
    }

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream with this collection as its source.
     * @see List#stream()
     */
    public Stream<Component> stream() {
        return childComponents.stream();
    }

    /**
     * Returns a possibly parallel Stream with this collection as its source. It is allowable for this method to return a sequential stream.
     *
     * @return possibly parallel Stream with this collection as its source.
     * @see List#parallelStream()
     */
    public Stream<Component> parallelStream() {
        return childComponents.parallelStream();
    }

    /**
     * Performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    public void forEach(Consumer<? super Component> action) {
        childComponents.forEach(action);
    }

    /**
     * Used to retrieve child components as {@link List}.
     * <p>
     * <span style="color:red">NOTE: this method returns NEW {@link List} of components</span>.
     *
     * @return list of child components.
     */
    public List<Component> getChildComponents() {
        return new ArrayList<>(childComponents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Component component = (Component) o;

        return new EqualsBuilder()
                .append(this.isEnabled(), component.isEnabled())
                .append(this.isVisible(), component.isVisible())
                .append(this.isHovered(), component.isHovered())
                .append(this.isFocused(), component.isFocused())
                .append(this.isPressed(), component.isPressed())
                .append(this.getListenerMap(), component.getListenerMap())
                .append(this.getPosition(), component.getPosition())
                .append(this.getSize(), component.getSize())
                .append(this.getIntersector(), component.getIntersector())
                .append(this.getTabIndex(), component.getTabIndex())
                .append(this.isTabFocusable(), component.isTabFocusable())
                .append(this.isFocusable(), component.isFocusable())
                .append(childComponents, component.childComponents)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(listenerMap)
                .append(position)
                .append(size)
                .append(enabled)
                .append(intersector)
                .append(hovered)
                .append(focused)
                .append(pressed)
                .append(tabIndex)
                .append(tabFocusable)
                .append(focusable)
                .append(childComponents)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("listenerMap", listenerMap)
                .append("position", position)
                .append("size", size)
                .append("enabled", enabled)
                .append("intersector", intersector)
                .append("hovered", hovered)
                .append("focused", focused)
                .append("tabIndex", tabIndex)
                .append("tabFocusable", tabFocusable)
                .append("focusable", focusable)
                .append("pressed", pressed)
                .toString();
    }

}
