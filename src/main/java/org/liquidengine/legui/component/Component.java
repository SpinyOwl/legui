package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.intersector.LeguiIntersector;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.processor.system.component.LeguiEventProcessorContainer;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Component is an object that have graphical representation in legui system
 * <p>
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public abstract class Component {
    private final LeguiEventProcessorContainer processors = new LeguiEventProcessorContainer(this);
    protected Vector2f position;
    protected Vector2f size;
    protected Vector4f backgroundColor = ColorConstants.lightGray();

    protected Border border = new SimpleLineBorder(this, ColorConstants.transparent(), 0);

    protected boolean enabled = true;
    protected boolean visible = true;
    protected boolean focused = false;

    protected float cornerRadius = 0;

    protected ComponentContainer parent;
    protected LeguiIntersector intersector;
    protected LeguiComponentRenderer renderer = LeguiRendererProvider.getProvider().getRenderer(this);

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

    public LeguiEventProcessorContainer getProcessors() {
        return processors;
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

    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
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

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
