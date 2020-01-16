package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 2/9/2017.
 */
public class MouseDragEvent<T extends Component> extends Event<T> {

    private final Vector2f delta;

    public MouseDragEvent(T component, Context context, Frame frame, Vector2f delta) {
        super(component, context, frame);
        this.delta = delta;
    }

    public Vector2f getDelta() {
        return delta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("delta", delta)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MouseDragEvent<?> that = (MouseDragEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(delta, that.delta)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(delta)
            .toHashCode();
    }
}
