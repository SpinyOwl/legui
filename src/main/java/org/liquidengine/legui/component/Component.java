package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.intersector.LeguiIntersector;
import org.liquidengine.legui.component.intersector.RectangleIntersector;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.listener.LeguiEventListenerMap;
import org.liquidengine.legui.listener.SystemEventListenerMap;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;
import org.liquidengine.legui.util.ColorConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Component is an object that have graphical representation in legui system.
 * <p>
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Component implements Serializable {

    /**
     * Indexer is integer that store next value for component index.
     */
    private static final AtomicInteger indexer = new AtomicInteger();

    /**
     * Component id.
     */
    private final int componentId;

    /**
     * Metadata map, place where renderers or event processors can store state of component.
     */
    protected Map<String, Object> metadata = new HashMap<>();

    /**
     * Position of component relative top left corner in parent component.
     * <p>
     * If component is the root component then position calculated relative window top left corner.
     */
    protected Vector2f position;

    /**
     * Size of component.
     */
    protected Vector2f size;

    /**
     * Component background color.
     * <p>Represented by vector where (x=r,y=g,z=b,w=a).
     * <p>For example white = {@code new Vector4f(1,1,1,1)}
     */
    protected Vector4f backgroundColor = ColorConstants.white();

    /**
     * Component border.
     */
    protected Border border;

    /**
     * Used to enable and disable event processing for this component.
     * If enabled==false then component won't receive events.
     */
    protected boolean enabled = true;

    /**
     * Determines whether this component should be visible when its
     * parent is visible. Components are initially visible.
     */
    protected boolean visible = true;

    /**
     * Used to store component states such as
     * <ul>
     * <li>hovered</li>
     * <li>pressed</li>
     * <li>focused</li>
     * </ul>
     */
    protected ComponentState state = new ComponentState();

    /**
     * Used to store corner radius of component.
     */
    protected float cornerRadius = 0;

    /**
     * Parent component container. For root components it could be null
     */
    protected ComponentContainer parent;

    /**
     * Intersector which used to determine for example if cursor intersects component or not. Cannot be null.
     */
    protected LeguiIntersector intersector = new RectangleIntersector();

    /**
     * Renderer which used to render component.
     */
    protected LeguiComponentRenderer renderer = LeguiRendererProvider.getProvider().getRenderer(this);

    /**
     * Event listener for ui events.
     */
    protected LeguiEventListenerMap leguiEventListeners = new LeguiEventListenerMap();

    /**
     * Event listener for system events, which transformed into specific ui events.
     */
    protected SystemEventListenerMap systemEventListeners = new SystemEventListenerMap(this.getClass());

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json serializer/deserializer component should contain empty constructor.
     */
    public Component() {
        this(0, 0, 10, 10);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public Component(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component
     * @param size     size of component
     */
    public Component(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
        this.componentId = indexer.getAndIncrement();
    }

    /**
     * Used by renderer thread to render component. Similar to flyweight pattern,
     * but implementation of renderer can be changed in dependent of context and graphics api
     *
     * @param context legui context
     */
    public void render(LeguiContext context) {
        renderer.render(this, context);
    }

    /**
     * Returns parent component. If returns null - current component is root component
     *
     * @return null or parent component
     */
    public ComponentContainer getParent() {
        return parent;
    }

    /**
     * Used to set parent component.
     *
     * @param parent component container.
     */
    public void setParent(ComponentContainer parent) {
        this.parent = parent;
        if (!parent.containsComponent(this)) {
            parent.addComponent(this);
        }
    }

    /**
     * Returns system event listeners for component instance.
     *
     * @return system event listeners map
     */
    public SystemEventListenerMap getSystemEventListeners() {
        return systemEventListeners;
    }

    /**
     * Returns position vector.
     * Be careful during changing this vector.
     *
     * @return position vector
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Used to set position of component
     *
     * @param position
     */
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    /**
     * Used to set current position.
     *
     * @param x x position relative to parent component
     * @param y y position relative to parent component
     */
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    /**
     * Returns size vector of component.
     * So to get width you can use
     * <pre>
     * {@code
     * Vector2f size = component.getSize();
     * float width = size.x;
     * float height = size.y;
     * }
     * </pre>
     *
     * @return
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Used to set size vector
     *
     * @param size size vector
     */
    public void setSize(Vector2f size) {
        this.size = size;
    }

    /**
     * Used to set size vector
     *
     * @param width  width to set
     * @param height height to set
     */
    public void setSize(float width, float height) {
        this.size.set(width, height);
    }

    /**
     * Returns ui event listeners
     *
     * @return
     */
    public LeguiEventListenerMap getLeguiEventListeners() {
        return leguiEventListeners;
    }

    /**
     * Used to set ui event listeners
     *
     * @param leguiEventListeners
     */
    public void setLeguiEventListeners(LeguiEventListenerMap leguiEventListeners) {
        this.leguiEventListeners = leguiEventListeners;
    }

    /**
     * Returns {@link Vector4f} background color vector where x,y,z,w mapped to r,g,b,a values.
     * <ul>
     * <li>0,0,0,1 - black</li>
     * <li>1,0,0,1 - red.</li>
     * <li>0,1,0,1 - green.</li>
     * <li>0,0,1,1 - blue.</li>
     * <li>0,0,0,0 - transparent black.</li>
     * </ul>
     *
     * @return background color vector
     */
    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Used to set background color vector where x,y,z,w mapped to r,g,b,a values.
     * <ul>
     * <li>0,0,0,1 - black</li>
     * <li>1,0,0,1 - red.</li>
     * <li>0,1,0,1 - green.</li>
     * <li>0,0,1,1 - blue.</li>
     * <li>0,0,0,0 - transparent black.</li>
     * </ul>
     *
     * @param backgroundColor background color vector
     */
    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Used to set background color vector.
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @param a alpha value
     */
    public void setBackgroundColor(float r, float g, float b, float a) {
        backgroundColor.set(r, g, b, a);
    }

    /**
     * Returns border of component.
     *
     * @return border
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Used to set border for component
     *
     * @param border border
     */
    public void setBorder(Border border) {
        this.border = border;
    }

    /**
     * Returns true if component enabled. By default if component enabled it receives and proceed events
     *
     * @return true if component enabled. default value is {@link Boolean#TRUE}
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Used to enable or disable component. By default if component enabled it receives and proceed events
     *
     * @param enabled flag to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns true if component visible.
     * By default if component visible it will be rendered and will receive events
     *
     * @return true if component visible. default value is {@link Boolean#TRUE}
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Used to make component visible or invisible.
     * By default if component visible it will be rendered and will receive events
     *
     * @param visible flag to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Returns component intersector which used to check if cursor intersect component or not.
     *
     * @return intersector
     */
    public LeguiIntersector getIntersector() {
        return intersector;
    }

    /**
     * Used to set intersector for component.
     *
     * @param intersector intersector
     */
    public void setIntersector(LeguiIntersector intersector) {
        this.intersector = intersector;
    }

    /**
     * Returns corner radius of component
     *
     * @return corner radius
     */
    public float getCornerRadius() {
        return cornerRadius;
    }

    /**
     * Used to set corner radius
     *
     * @param cornerRadius corner radius
     */
    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    /**
     * If this component is composite of several other components
     * it should return one of child components which intersects with cursor
     * else it should return {@code this}.
     * <p>
     * If cursor is outside of component method should return null
     *
     * @param cursorPosition cursor position
     * @return component at cursor or null.
     */
    public Component getComponentAt(Vector2f cursorPosition) {
        if (visible && intersector.intersects(this, cursorPosition)) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Returns renderer for component
     *
     * @return renderer
     */
    public LeguiComponentRenderer getRenderer() {
        return renderer;
    }

    /**
     * Used to set component renderer
     *
     * @param renderer component renderer
     */
    public void setRenderer(LeguiComponentRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Returns component state
     *
     * @return component state
     */
    public ComponentState getState() {
        return state;
    }

    /**
     * Used to set component state
     *
     * @param state component state
     */
    public void setState(ComponentState state) {
        this.state = state;
    }

    /**
     * Returns component metadata. Storage of some temporary statements. Can be used for example by stateless renderers.
     *
     * @return map of objects
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Used to return component id
     */
    public int getComponentId() {
        return componentId;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        return new EqualsBuilder()
                .append(componentId, component.componentId)
                .append(enabled, component.enabled)
                .append(visible, component.visible)
                .append(cornerRadius, component.cornerRadius)
                .append(position, component.position)
                .append(size, component.size)
                .append(backgroundColor, component.backgroundColor)
                .append(border, component.border)
                .append(intersector, component.intersector)
                .append(leguiEventListeners, component.leguiEventListeners)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(componentId)
                .append(position)
                .append(size)
                .append(backgroundColor)
                .append(border)
                .append(enabled)
                .append(visible)
                .append(cornerRadius)
                .append(intersector)
                .append(leguiEventListeners)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("position", position)
                .append("size", size)
                .append("backgroundColor", backgroundColor)
                .append("border", border)
                .append("enabled", enabled)
                .append("visible", visible)
                .append("cornerRadius", cornerRadius)
                .append("intersector", intersector)
                .append("leguiEventListeners", leguiEventListeners)
                .toString();
    }
}
