package org.liquidengine.legui.component.event.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2fc;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

public class ChangePositionEvent<T extends Component> extends Event<T> {

    private final Vector2fc oldPosition;
    private final Vector2fc newPosition;

    public ChangePositionEvent(T targetComponent, Context context, Frame frame, Vector2fc oldPosition, Vector2fc newPosition) {
        super(targetComponent, context, frame);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public Vector2fc getOldPosition() {
        return oldPosition;
    }

    public Vector2fc getNewPosition() {
        return newPosition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("oldPosition", oldPosition)
            .append("newPosition", newPosition)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChangePositionEvent<?> that = (ChangePositionEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(oldPosition, that.oldPosition)
            .append(newPosition, that.newPosition)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(oldPosition)
            .append(newPosition)
            .toHashCode();
    }
}
