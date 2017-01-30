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
import org.liquidengine.legui.util.Util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Component is an object that have graphical representation in legui system.
 * <p>
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Component implements Serializable {

//    /**
//     * Indexer is integer that store next value for tooltipComponent index.
//     */
//    private static final AtomicInteger indexer = new AtomicInteger();
//
//    /**
//     * Component id.
//     */
//    private final int componentId;

    /**
     * Metadata map, place where renderers or event processors can store state of tooltipComponent.
     */
    protected Map<String, Object> metadata = new HashMap<>();

    /**
     * Position of tooltipComponent relative top left corner in parent tooltipComponent.
     * <p>
     * If tooltipComponent is the root tooltipComponent then position calculated relative window top left corner.
     */
    protected Vector2f position;

    /**
     * Size of tooltipComponent.
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
     * Used to enable and disable event processing for this tooltipComponent.
     * If enabled==false then tooltipComponent won't receive events.
     */
    protected boolean enabled = true;

    /**
     * Determines whether this tooltipComponent should be visible when its
     * parent is visible. Components are initially visible.
     */
    protected boolean visible = true;

    /**
     * Used to store tooltipComponent states such as
     * <ul>
     * <li>hovered</li>
     * <li>pressed</li>
     * <li>focused</li>
     * </ul>
     */
    protected ComponentState state = new ComponentState();

    /**
     * Used to store corner radius of tooltipComponent.
     */
    protected float cornerRadius = 0;

    /**
     * Parent tooltipComponent container. For root components it could be null
     */
    protected ComponentContainer parent;

    /**
     * Intersector which used to determine for example if cursor intersects tooltipComponent or not. Cannot be null.
     */
    protected LeguiIntersector intersector = new RectangleIntersector();

    /**
     * Renderer which used to render tooltipComponent.
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
     * Tooltip message
     */
    protected String tooltipText = null;

    protected Tooltip tooltip = null;

    /**
     * Default constructor. Used to create tooltipComponent instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json serializer/deserializer tooltipComponent should contain empty constructor.
     */
    public Component() {
        this(0, 0, 10, 10);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent tooltipComponent
     * @param y      y position position in parent tooltipComponent
     * @param width  width of tooltipComponent
     * @param height height of tooltipComponent
     */
    public Component(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent tooltipComponent
     * @param size     size of tooltipComponent
     */
    public Component(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
//        this.componentId = indexer.getAndIncrement();
    }

    /**
     * Used by renderer thread to render tooltipComponent. Similar to flyweight pattern,
     * but implementation of renderer can be changed in dependent of context and graphics api
     *
     * @param context legui context
     */
    public void render(LeguiContext context) {
        renderer.render(this, context);
    }

    /**
     * Returns parent tooltipComponent. If returns null - current tooltipComponent is root tooltipComponent
     *
     * @return null or parent tooltipComponent
     */
    public ComponentContainer getParent() {
        return parent;
    }

    /**
     * Used to set parent tooltipComponent.
     *
     * @param parent tooltipComponent container.
     */
    public void setParent(ComponentContainer parent) {
        this.parent = parent;
        if (!parent.containsComponent(this)) {
            parent.addComponent(this);
        }
    }

    /**
     * Returns system event listeners for tooltipComponent instance.
     *
     * @return system event listeners map
     */
    public SystemEventListenerMap getSystemEventListeners() {
        return systemEventListeners;
    }

    /**
     * Returns origin position vector (tooltipComponent position on the screen).
     * Be careful during changing this vector.
     *
     * @return position vector
     */
    public Vector2f getOriginPosition() {
        return Util.calculatePosition(this);
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
     * Used to set position of tooltipComponent
     *
     * @param position
     */
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    /**
     * Used to set current position.
     *
     * @param x x position relative to parent tooltipComponent
     * @param y y position relative to parent tooltipComponent
     */
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    /**
     * Returns size vector of tooltipComponent.
     * So to get width you can use
     * <pre>
     * {@code
     * Vector2f size = tooltipComponent.getSize();
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
     * Returns border of tooltipComponent.
     *
     * @return border
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Used to set border for tooltipComponent
     *
     * @param border border
     */
    public void setBorder(Border border) {
        this.border = border;
    }

    /**
     * Returns true if tooltipComponent enabled. By default if tooltipComponent enabled it receives and proceed events
     *
     * @return true if tooltipComponent enabled. default value is {@link Boolean#TRUE}
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Used to enable or disable tooltipComponent. By default if tooltipComponent enabled it receives and proceed events
     *
     * @param enabled flag to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns true if tooltipComponent visible.
     * By default if tooltipComponent visible it will be rendered and will receive events
     *
     * @return true if tooltipComponent visible. default value is {@link Boolean#TRUE}
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Used to make tooltipComponent visible or invisible.
     * By default if tooltipComponent visible it will be rendered and will receive events
     *
     * @param visible flag to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Returns tooltipComponent intersector which used to check if cursor intersect tooltipComponent or not.
     *
     * @return intersector
     */
    public LeguiIntersector getIntersector() {
        return intersector;
    }

    /**
     * Used to set intersector for tooltipComponent.
     *
     * @param intersector intersector
     */
    public void setIntersector(LeguiIntersector intersector) {
        this.intersector = intersector;
    }

    /**
     * Returns corner radius of tooltipComponent
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
     * If this tooltipComponent is composite of several other components
     * it should return one of child components which intersects with cursor
     * else it should return {@code this}.
     * <p>
     * If cursor is outside of tooltipComponent method should return null
     *
     * @param cursorPosition cursor position
     * @return tooltipComponent at cursor or null.
     */
    public Component getComponentAt(Vector2f cursorPosition) {
        if (visible && intersector.intersects(this, cursorPosition)) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Returns renderer for tooltipComponent
     *
     * @return renderer
     */
    public LeguiComponentRenderer getRenderer() {
        return renderer;
    }

    /**
     * Used to set tooltipComponent renderer
     *
     * @param renderer tooltipComponent renderer
     */
    public void setRenderer(LeguiComponentRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Returns tooltipComponent state
     *
     * @return tooltipComponent state
     */
    public ComponentState getState() {
        return state;
    }

    /**
     * Used to set tooltipComponent state
     *
     * @param state tooltipComponent state
     */
    public void setState(ComponentState state) {
        this.state = state;
    }

    /**
     * Returns tooltipComponent metadata. Storage of some temporary statements. Can be used for example by stateless renderers.
     *
     * @return map of objects
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

//    /**
//     * Used to return tooltipComponent id
//     */
//    public int getComponentId() {
//        return componentId;
//    }


    /**
     * Returns tooltipText text
     *
     * @return tooltipText text
     */
    public String getTooltipText() {
        return tooltipText;
    }

    /**
     * Used to set tooltipText text
     *
     * @param tooltip tooltipText text
     */
    public void setTooltipText(String tooltip) {
        this.tooltipText = tooltip;
        if (this.tooltip == null) {
            this.tooltip = new Tooltip(this);
        }
        this.tooltip.getTextState().setText(tooltip);
    }

    /**
     * Generated tooltipText tooltipComponent on base of tooltipText text.
     * <p>
     * You can use this tooltipComponent to set background, font and other visual effects.
     * <p>
     * <span style="color:red">
     * NOTE: do nat add this tooltipComponent to frame tooltipComponent layer. <br>
     * It automatically added to tooltipText layer by system event processor.
     * Also tooltipText position automatically calculated there.
     * </span>
     *
     * @return generated tooltipText tooltipComponent or null if tooltipText is empty.
     */
    public Tooltip getTooltip() {
        return tooltip;
    }

    /**
     * you can set your own tooltip with own tooltip renderer using this setter.
     * @param tooltip new tooltip.
     */
    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
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
//                .append(componentId, tooltipComponent.componentId)
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
//                .append(componentId)
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
