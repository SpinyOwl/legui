package org.liquidengine.legui.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.misc.listener.component.TabKeyEventListener;
import org.liquidengine.legui.component.misc.listener.component.TooltipCursorEnterListener;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;
import org.liquidengine.legui.listener.ListenerMap;
import org.liquidengine.legui.theme.Themes;

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
     * Component background color.
     * <p>
     * Represented by vector where (x=r,y=g,z=b,w=a).
     * <p>
     * For example white = {@code new Vector4f(1,1,1,1)}
     */
    private Vector4f backgroundColor = ColorConstants.white();
    /**
     * Stroke color. Used to render stroke if component is focused.
     */
    private Vector4f focusedStrokeColor = ColorConstants.lightBlue();
    /**
     * Component border.
     */
    private Border border = null;
    /**
     * Used to store corner radius of component.
     */
    private float cornerRadius = 0;
    /**
     * Used to enable and disable event processing for this component. If enabled==false then component won't receive events.
     */
    private boolean enabled = true;
    /**
     * Determines whether this component should be visible when its parent is visible. Components are initially visible.
     */
    private boolean visible = true;
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

    ////////////////////////////////
    //// COMPONENT LAYER DATA
    ////////////////////////////////

    /**
     * Layout. Used to layout
     */
    private Layout layout;

    /**
     * Preferred size of component. Used to set preferred size of component for layout manager. Layout manager will try to make this component size equal to
     * preferred.
     * <p>
     * {@code -1} is default value - that means that layout manager will calculate preferred size.
     */
    private Vector2f preferredSize;

    /**
     * Minimum size of component. Used to set minimum size of component for layout manager. Layout manager uses this minimum size if component should be as
     * small as possible. If one of dimensions is <= 0 this minimum size is 0.
     */
    private Vector2f minimumSize;

    /**
     * Maximum size of component. Used to set maximum size of component for layout manager. Layout manager uses this maximum size if component should be as
     * small as possible. If one of dimensions is <= 0 this maximum size is 0.
     */
    private Vector2f maximumSize;

    ////////////////////////////////
    //// CONTAINER BASE DATA
    ////////////////////////////////

    /**
     * List of child components.
     */
    private List<Component> components = new CopyOnWriteArrayList<>();

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
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     */
    public Component(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     */
    public Component(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
        initialize();
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
     * @param width width to set.
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
        for (Component parent = this.getParent(); parent != null; parent = parent.getParent()) {
            screenPos.add(parent.getPosition());
        }
        return screenPos;
    }

    /**
     * Returns {@link Vector4f} background color vector where x,y,z,w mapped to r,g,b,a values. <ul> <li>vector.x - red.</li> <li>vector.y - green.</li>
     * <li>vector.z - blue.</li> <li>vector.a - alpha.</li> </ul>
     *
     * @return background color vector.
     */
    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Used to set background color vector where x,y,z,w mapped to r,g,b,a values. <ul> <li>vector.x - red.</li> <li>vector.y - green.</li> <li>vector.z -
     * blue.</li> <li>vector.a - alpha.</li> </ul>
     *
     * @param backgroundColor background color vector.
     */
    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Used to set background color vector.
     *
     * @param r red value.
     * @param g green value.
     * @param b blue value.
     * @param a alpha value.
     */
    public void setBackgroundColor(float r, float g, float b, float a) {
        backgroundColor.set(r, g, b, a);
    }

    /**
     * Returns {@link Vector4f} focused stroke color vector where x,y,z,w mapped to r,g,b,a values. <ul> <li>vector.x - red.</li> <li>vector.y - green.</li>
     * <li>vector.z - blue.</li> <li>vector.a - alpha.</li> </ul>
     *
     * @return background color vector.
     */
    public Vector4f getFocusedStrokeColor() {
        return focusedStrokeColor;
    }

    /**
     * Used to set focused stroke color vector where x,y,z,w mapped to r,g,b,a values. <ul> <li>vector.x - red.</li> <li>vector.y - green.</li> <li>vector.z -
     * blue.</li> <li>vector.a - alpha.</li> </ul>
     *
     * @param focusedStrokeColor focused stroke color vector.
     */
    public void setFocusedStrokeColor(Vector4f focusedStrokeColor) {
        this.focusedStrokeColor = focusedStrokeColor;
    }

    /**
     * Used to set focused stroke color vector.
     *
     * @param r red value.
     * @param g green value.
     * @param b blue value.
     * @param a alpha value.
     */
    public void setFocusedStrokeColor(float r, float g, float b, float a) {
        backgroundColor.set(r, g, b, a);
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
        return visible;
    }

    /**
     * Used to make component visible or invisible. By default if component visible it will be rendered and will receive events.
     *
     * @param visible flag to set.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Used to determine if point intersects component (in screen space). This method uses component intersector.
     *
     * @param point point to check.
     *
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
     * Returns border of component.
     *
     * @return border.
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Used to set border for component.
     *
     * @param border border.
     */
    public void setBorder(Border border) {
        this.border = border;
    }

    /**
     * Returns corner radius of component.
     *
     * @return corner radius.
     */
    public float getCornerRadius() {
        return cornerRadius;
    }

    /**
     * Used to set corner radius.
     *
     * @param cornerRadius corner radius.
     */
    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
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

    ////////////////////////////////
    //// COMPONENT LAYER DATA
    ////////////////////////////////


    /**
     * Returns layout.
     *
     * @return layout.
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Used to set layout for this component.
     *
     * @param layout layout to set.
     */
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    /**
     * Gets preferred size.
     * <p>
     * Preferred size of component. Used to set preferred size of component for layout manager. Layout manager will try to make this component size equal to
     * preferred.
     * <p>
     * {@code -1} is default value - that means that layout manager will calculate preferred size.
     *
     * @return the preferred size.
     */
    public Vector2f getPreferredSize() {
        if (preferredSize == null) {
            if (layout != null) {
                preferredSize = layout.getPreferredSize(this);
            } else {
                preferredSize = new Vector2f(size);
            }
        }
        return preferredSize;
    }

    /**
     * Sets preferred size.
     * <p>
     * Preferred size of component. Used to set preferred size of component for layout manager. Layout manager will try to make this component size equal to
     * preferred.
     * <p>
     * {@code -1} is default value - that means that layout manager will calculate preferred size.
     *
     * @param preferredSize the preferred size.
     */
    public void setPreferredSize(Vector2f preferredSize) {
        this.preferredSize = preferredSize;
    }

    /**
     * Gets minimum size.
     * <p>
     * Minimum size of component. Used to set minimum size of component for layout manager. Layout manager uses this minimum size if component should be as
     * small as possible. If one of dimensions is <= 0 this minimum size is 0.
     *
     * @return the minimum size.
     */
    public Vector2f getMinimumSize() {
        if (minimumSize == null) {
            if (layout != null) {
                minimumSize = layout.getMinimumSize(this);
            } else {
                minimumSize = new Vector2f();
            }
        }
        return minimumSize;
    }

    /**
     * Sets minimum size.
     * <p>
     * Minimum size of component. Used to set minimum size of component for layout manager. Layout manager uses this minimum size if component should be as
     * small as possible. If one of dimensions is <= 0 this minimum size is 0.
     *
     * @param minimumSize the minimum size.
     */
    public void setMinimumSize(Vector2f minimumSize) {
        this.minimumSize = minimumSize;
    }

    /**
     * Gets maximum size.
     * <p>
     * Maximum size of component. Used to set maximum size of component for layout manager. Layout manager uses this maximum size if component should be as
     * small as possible. If one of dimensions is <= 0 this maximum size is 0.
     *
     * @return the maximum size.
     */
    public Vector2f getMaximumSize() {
        if (maximumSize == null) {
            if (layout != null) {
                maximumSize = layout.getMaximumSize(this);
            } else {
                maximumSize = new Vector2f(Float.MAX_VALUE);
            }
        }
        return maximumSize;
    }

    /**
     * Sets maximum size.
     * <p>
     * Maximum size of component. Used to set maximum size of component for layout manager. Layout manager uses this maximum size if component should be as
     * small as possible. If one of dimensions is <= 0 this maximum size is 0.
     *
     * @param maximumSize the maximum size.
     */
    public void setMaximumSize(Vector2f maximumSize) {
        this.maximumSize = maximumSize;
    }

    /////////////////////////////////
    //// CONTAINER METHODS
    /////////////////////////////////

    /**
     * Returns count of child components.
     *
     * @return count of child components.
     *
     * @see List#size()
     */
    public int count() {
        return components.size();
    }

    /**
     * Returns true if layerFrame contains no elements.
     *
     * @return true if layerFrame contains no elements.
     *
     * @see List#isEmpty()
     */
    public boolean isEmpty() {
        return components.isEmpty();
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
    public boolean contains(Component component) {
        return isContains(component);
    }

    /**
     * Returns an iterator over the elements in this layerFrame. The elements are returned in no particular order.
     *
     * @return an iterator over the elements in this layerFrame.
     *
     * @see List#iterator()
     */
    public Iterator<Component> containerIterator() {
        return components.iterator();
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
    public boolean add(Component component) {
        return this.add(component, null);
    }

    /**
     * Used to add component to layerFrame.
     *
     * @param component component to add.
     * @param constraint layout constraint.
     * @return true if component is added.
     * @see List#add(Object)
     */
    public boolean add(Component component, LayoutConstraint constraint) {
        if (component == null || component == this || isContains(component)) {
            return false;
        }
        boolean added = components.add(component);
        if (added) {
            if (layout != null) {
                layout.addComponent(component, constraint);
            }
            changeParent(component);
        }
        return added;
    }

    /**
     * Used to check if component collection contains component or not. Checked by reference.
     *
     * @param component component to check.
     *
     * @return true if collection contains provided component.
     */
    private boolean isContains(Component component) {
        return components.stream().anyMatch(c -> c == component);
    }

    /**
     * Used to change parent of added component.
     *
     * @param component component to change.
     */
    private void changeParent(Component component) {
        Component parent = component.getParent();
        if (parent == this) {
            return;
        }
        if (parent != null) {
            parent.remove(component);
        }
        component.setParent(this);
    }

    /**
     * Used to remove component.
     *
     * @param component component to remove.
     *
     * @return true if removed.
     *
     * @see List#remove(Object)
     */
    public boolean remove(Component component) {
        if (component != null) {
            Component parent = component.getParent();
            if (parent != null && parent == this && isContains(component)) {
                boolean removed = components.remove(component);
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
     *
     * @see List#removeAll(Collection)
     */
    public void removeAll(Collection<? extends Component> components) {
        List<Component> toRemove = new ArrayList<>();
        components.forEach(compo -> {
            if (compo != null) {
                compo.setParent(null);
                toRemove.add(compo);
            }
        });
        this.components.removeAll(toRemove);
    }

    /**
     * Removes all of the elements of this layerFrame that satisfy the given predicate. Errors or runtime exceptions thrown during iteration or by the predicate
     * are relayed to the caller.
     *
     * @param filter a predicate which returns true for elements to be removed.
     *
     * @return true if any components were removed.
     *
     * @see List#removeIf(Predicate)
     */
    public boolean removeIf(Predicate<? super Component> filter) {
        components.stream().filter(filter).forEach(compo -> compo.setParent(null));
        return components.removeIf(filter);
    }

    /**
     * Used to remove all child components from layerFrame.
     *
     * @see List#clear()
     */
    public void clearChilds() {
        components.forEach(compo -> compo.setParent(null));
        components.clear();
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
        return this.components.containsAll(components);
    }

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream with this collection as its source.
     *
     * @see List#stream()
     */
    public Stream<Component> stream() {
        return components.stream();
    }

    /**
     * Returns a possibly parallel Stream with this collection as its source. It is allowable for this method to return a sequential stream.
     *
     * @return possibly parallel Stream with this collection as its source.
     *
     * @see List#parallelStream()
     */
    public Stream<Component> parallelStream() {
        return components.parallelStream();
    }

    /**
     * Performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    public void forEach(Consumer<? super Component> action) {
        components.forEach(action);
    }

    /**
     * Used to retrieve child components as {@link List}.
     * <p>
     * <span style="color:red">NOTE: this method returns NEW {@link List} of components</span>.
     *
     * @return list of child components.
     */
    public List<Component> getChilds() {
        return new ArrayList<>(components);
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
            .append(this.getCornerRadius(), component.getCornerRadius())
            .append(this.isEnabled(), component.isEnabled())
            .append(this.isVisible(), component.isVisible())
            .append(this.isHovered(), component.isHovered())
            .append(this.isFocused(), component.isFocused())
            .append(this.isPressed(), component.isPressed())
            .append(this.getListenerMap(), component.getListenerMap())
            .append(this.getPosition(), component.getPosition())
            .append(this.getSize(), component.getSize())
            .append(this.getBackgroundColor(), component.getBackgroundColor())
            .append(this.getBorder(), component.getBorder())
            .append(this.getIntersector(), component.getIntersector())
            .append(this.getTabIndex(), component.getTabIndex())
            .append(this.isTabFocusable(), component.isTabFocusable())
            .append(components, component.components)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(cornerRadius)
            .append(listenerMap)
            .append(position)
            .append(size)
            .append(backgroundColor)
            .append(border)
            .append(enabled)
            .append(visible)
            .append(intersector)
            .append(hovered)
            .append(focused)
            .append(pressed)
            .append(tabIndex)
            .append(tabFocusable)
            .append(components)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("cornerRadius", cornerRadius)
            .append("listenerMap", listenerMap)
            .append("position", position)
            .append("size", size)
            .append("backgroundColor", backgroundColor)
            .append("border", border)
            .append("enabled", enabled)
            .append("visible", visible)
            .append("intersector", intersector)
            .append("hovered", hovered)
            .append("focused", focused)
            .append("tabIndex", tabIndex)
            .append("tabFocusable", tabFocusable)
            .append("pressed", pressed)
            .toString();
    }

}
