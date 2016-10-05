package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public class CursorEnterEvent implements LeguiComponentEvent {
    private final Component component;
    private final Action action;
    private final Vector2f cursorComponentPosition;

    public CursorEnterEvent(Component component, Action action, Vector2f cursorComponentPosition) {
        this.component = component;
        this.action = action;
        this.cursorComponentPosition = cursorComponentPosition;
    }

    public Action getAction() {
        return action;
    }

    public Vector2f getCursorComponentPosition() {
        return cursorComponentPosition;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursorEnterEvent)) return false;
        CursorEnterEvent that = (CursorEnterEvent) o;
        return Objects.equal(component, that.component) &&
                action == that.action &&
                Objects.equal(cursorComponentPosition, that.cursorComponentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(component, action, cursorComponentPosition);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("component", component)
                .append("action", action)
                .append("cursorComponentPosition", cursorComponentPosition)
                .toString();
    }

    public static enum Action {
        ENTER,
        EXIT
    }
}
