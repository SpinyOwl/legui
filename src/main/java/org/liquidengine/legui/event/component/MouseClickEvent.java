package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
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

    public MouseClickEvent(Component component, Vector2f position, MouseClickAction action) {
        super(component);
        this.position = position;
        this.action = action;
    }

    public Vector2f getPosition() {
        return position;
    }

    public MouseClickAction getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MouseClickEvent)) return false;
        if (!super.equals(o)) return false;
        MouseClickEvent that = (MouseClickEvent) o;
        return Objects.equal(position, that.position) &&
                action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), position, action);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("position", position)
                .append("action", action)
                .toString();
    }

    public enum MouseClickAction {
        CLICK,
        PRESS,
        RELEASE
    }
}
