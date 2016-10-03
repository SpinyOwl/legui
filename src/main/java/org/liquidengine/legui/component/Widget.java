package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public class Widget extends ComponentContainer {
    protected TextState textState;
    protected Vector4f closeButtonColor = ColorConstants.red();

    protected float titleHeight = 20;
    protected boolean titleEnabled = true;
    protected Vector4f titleBackgroundColor = ColorConstants.lightGray();

    protected boolean closeable = true;
    protected boolean resizable = false;

    public Widget() {
        initialize("Widget");
    }

    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("Widget");
    }

    public Widget(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("Widget");
    }

    public Widget(String title) {
        initialize(title);
    }

    public Widget(String title, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(title);
    }

    public Widget(String title, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(title);
    }

    private void initialize(String title) {
        textState = new TextState(title);
        textState.setHorizontalAlign(HorizontalAlign.LEFT);
        textState.getPadding().set(10, 5, 10, 5);
        backgroundColor.set(1);
        border = new SimpleLineBorder(this, ColorConstants.black(), 1);
        componentListenerHolder.addMouseDragEventListener(event -> {
            if(event.getComponent()==this){
                Vector2f sub = event.getCursorPosition().sub(event.getCursorPositionPrev());
//                System.out.println(sub);
                position.add(sub);
            }
        });
    }

    public float getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(float titleHeight) {
        this.titleHeight = titleHeight;
    }

    public boolean isTitleEnabled() {
        return titleEnabled;
    }

    public void setTitleEnabled(boolean titleEnabled) {
        this.titleEnabled = titleEnabled;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public Vector4f getTitleBackgroundColor() {
        return titleBackgroundColor;
    }

    public void setTitleBackgroundColor(Vector4f titleBackgroundColor) {
        this.titleBackgroundColor = titleBackgroundColor;
    }

    public TextState getTitleTextState() {
        return textState;
    }

    public Vector4f getCloseButtonColor() {
        return closeButtonColor;
    }

    public void setCloseButtonColor(Vector4f closeButtonColor) {
        this.closeButtonColor = closeButtonColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Widget widget = (Widget) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(titleHeight, widget.titleHeight)
                .append(titleEnabled, widget.titleEnabled)
                .append(closeable, widget.closeable)
                .append(resizable, widget.resizable)
                .append(textState, widget.textState)
                .append(titleBackgroundColor, widget.titleBackgroundColor)
                .append(closeButtonColor, widget.closeButtonColor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(titleHeight)
                .append(titleEnabled)
                .append(titleBackgroundColor)
                .append(closeButtonColor)
                .append(closeable)
                .append(resizable)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("titleHeight", titleHeight)
                .append("titleEnabled", titleEnabled)
                .append("titleBackgroundColor", titleBackgroundColor)
                .append("closeButtonColor", closeButtonColor)
                .append("closeable", closeable)
                .append("resizable", resizable)
                .toString();
    }
}
