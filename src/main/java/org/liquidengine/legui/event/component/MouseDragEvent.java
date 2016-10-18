package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class MouseDragEvent extends AbstractComponentEvent {
    private final Vector2f cursorPosition;
    private final Vector2f cursorPositionPrev;

    public MouseDragEvent(Vector2f cursorPosition, Vector2f cursorPositionPrev, Component component) {
        super(component);
        this.cursorPosition = cursorPosition;
        this.cursorPositionPrev = cursorPositionPrev;
    }

    public Vector2f getCursorPosition() {
        return cursorPosition;
    }

    public Vector2f getCursorPositionPrev() {
        return cursorPositionPrev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MouseDragEvent)) return false;
        if (!super.equals(o)) return false;
        MouseDragEvent that = (MouseDragEvent) o;
        return Objects.equal(cursorPosition, that.cursorPosition) &&
                Objects.equal(cursorPositionPrev, that.cursorPositionPrev);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), cursorPosition, cursorPositionPrev);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cursorPosition", cursorPosition)
                .append("cursorPositionPrev", cursorPositionPrev)
                .toString();
    }
}
