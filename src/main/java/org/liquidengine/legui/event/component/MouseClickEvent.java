package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

/**
 * Created by Shcherbin Alexander on 10/6/2016.
 */
public class MouseClickEvent extends AbstractComponentEvent {
    private final Vector2f position;
    private final MouseClickAction action;
    private final int button;

    public MouseClickEvent(Component component, Vector2f position, MouseClickAction action, int button) {
        super(component);
        this.position = position;
        this.action = action;
        this.button = button;
    }

    public Vector2f getPosition() {
        return position;
    }

    public MouseClickAction getAction() {
        return action;
    }


    public int getButton() {
        return button;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("position", position)
                .append("action", action)
                .append("button", button)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MouseClickEvent that = (MouseClickEvent) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(button, that.button)
                .append(position, that.position)
                .append(action, that.action)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(position)
                .append(action)
                .append(button)
                .toHashCode();
    }

    public enum MouseClickAction {
        CLICK,
        PRESS,
        RELEASE
    }
}
