package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.LeguiEvent;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public class CursorEnterEvent extends LeguiEvent {
    private final CursorEnterAction action;
    private final Vector2f cursorComponentPosition;

    public CursorEnterEvent(Component component, CursorEnterAction action, Vector2f cursorComponentPosition) {
        super(component);
        this.action = action;
        this.cursorComponentPosition = cursorComponentPosition;
    }

    public CursorEnterAction getAction() {
        return action;
    }

    public Vector2f getCursorComponentPosition() {
        return cursorComponentPosition;
    }

    public enum CursorEnterAction {
        ENTER,
        EXIT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursorEnterEvent)) return false;
        if (!super.equals(o)) return false;
        CursorEnterEvent that = (CursorEnterEvent) o;
        return action == that.action &&
                Objects.equal(cursorComponentPosition, that.cursorComponentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), action, cursorComponentPosition);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("action", action)
                .append("cursorComponentPosition", cursorComponentPosition)
                .toString();
    }
}
