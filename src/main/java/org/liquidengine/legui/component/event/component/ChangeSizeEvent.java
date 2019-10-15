package org.liquidengine.legui.component.event.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2fc;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

public class ChangeSizeEvent<T extends Component> extends Event<T> {

    private final Vector2fc oldSize;
    private final Vector2fc newSize;

    public ChangeSizeEvent(T targetComponent, Context context, Frame frame, Vector2fc oldSize, Vector2fc newSize) {
        super(targetComponent, context, frame);
        this.oldSize = oldSize;
        this.newSize = newSize;
    }

    public Vector2fc getOldSize() {
        return oldSize;
    }

    public Vector2fc getNewSize() {
        return newSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("oldSize", oldSize)
            .append("newSize", newSize)
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

        ChangeSizeEvent<?> that = (ChangeSizeEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(oldSize, that.oldSize)
            .append(newSize, that.newSize)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(oldSize)
            .append(newSize)
            .toHashCode();
    }
}
