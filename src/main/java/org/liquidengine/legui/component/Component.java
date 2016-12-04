package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.intersector.LeguiIntersector;
import org.liquidengine.legui.component.intersector.RectangleIntersector;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.listener.LeguiEventListenerList;
import org.liquidengine.legui.listener.SystemEventListenerList;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;
import org.liquidengine.legui.util.ColorConstants;

import java.io.Serializable;

/**
 * Component is an object that have graphical representation in legui system
 * <p>
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Component implements Serializable {
    protected Vector2f position;
    protected Vector2f size;
    protected Vector4f backgroundColor = ColorConstants.white();

    protected Border border = new SimpleRectangleLineBorder(ColorConstants.transparent(), 0);

    protected boolean enabled = true;
    protected boolean visible = true;

    protected ComponentState state = new ComponentState();

    protected float cornerRadius = 0;

    protected ComponentContainer parent;
    protected LeguiIntersector intersector = new RectangleIntersector();
    protected LeguiComponentRenderer renderer = LeguiRendererProvider.getProvider().getRenderer(this);

    protected LeguiEventListenerList leguiEventListeners = new LeguiEventListenerList();
    protected SystemEventListenerList systemEventListeners = new SystemEventListenerList(this.getClass());

    public Component() {
        this(10, 10, 10, 10);
    }

    public Component(float x, float y, float width, float height) {
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    public Component(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
    }

    public void render(LeguiContext context) {
        renderer.render(this, context);
    }

    public ComponentContainer getParent() {
        return parent;
    }

    public void setParent(ComponentContainer parent) {
        this.parent = parent;
    }

    public SystemEventListenerList getSystemEventListeners() {
        return systemEventListeners;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void setSize(float width, float height) {
        this.size.set(width, height);
    }

    public LeguiEventListenerList getLeguiEventListeners() {
        return leguiEventListeners;
    }

    public void setLeguiEventListeners(LeguiEventListenerList leguiEventListeners) {
        this.leguiEventListeners = leguiEventListeners;
    }

    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundColor(float r, float g, float b, float a) {
        backgroundColor.set(r, g, b, a);
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LeguiIntersector getIntersector() {
        return intersector;
    }

    public void setIntersector(LeguiIntersector intersector) {
        this.intersector = intersector;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public Component getComponentAt(Vector2f cursorPosition) {
        if (visible && intersector.intersects(this, cursorPosition)) {
            return this;
        } else {
            return null;
        }
    }

    public LeguiComponentRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(LeguiComponentRenderer renderer) {
        this.renderer = renderer;
    }

    public ComponentState getState() {
        return state;
    }

    public void setState(ComponentState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        return new EqualsBuilder()
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
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
