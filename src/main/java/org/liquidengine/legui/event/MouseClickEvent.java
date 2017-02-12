package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.input.Mouse;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public class MouseClickEvent extends AbstractEvent {
    public static final int MOUSE_PRESS   = 0;
    public static final int CLICK         = 1;
    public static final int MOUSE_RELEASE = 2;

    private final int               action;
    private final Mouse.MouseButton button;
    private final Vector2f          position;
    private final Vector2f          absolutePosition;

    public MouseClickEvent(Component controller, int action, Mouse.MouseButton button, Vector2f position, Vector2f absolutePosition) {
        super(controller);
        this.action = action;
        this.button = button;
        this.position = position;
        this.absolutePosition = absolutePosition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("action", action)
                .append("button", button)
                .append("position", position)
                .append("absolutePosition", absolutePosition)
                .toString();
    }

    public int getAction() {
        return action;
    }

    public Mouse.MouseButton getButton() {
        return button;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getAbsolutePosition() {
        return absolutePosition;
    }
}
