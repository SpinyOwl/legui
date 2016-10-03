package org.liquidengine.legui.event.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class MouseDragEvent implements LeguiComponentEvent {
    private Vector2f cursorPosition;
    private Vector2f cursorPositionPrev;
    private Component component;

    public MouseDragEvent(Vector2f cursorPosition, Vector2f cursorPositionPrev, Component component) {
        this.cursorPosition = cursorPosition;
        this.cursorPositionPrev = cursorPositionPrev;
        this.component = component;
    }

    public Vector2f getCursorPosition() {
        return cursorPosition;
    }

    public Vector2f getCursorPositionPrev() {
        return cursorPositionPrev;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("cursorPosition", cursorPosition)
                .append("cursorPositionPrev", cursorPositionPrev)
                .append("component", component.getClass().getSimpleName())
                .toString();
    }
}
