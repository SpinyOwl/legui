package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public abstract class Event<T extends Component> {

    private final T targetComponent;
    private final Context context;
    private final Frame frame;

    public Event(T targetComponent, Context context, Frame frame) {
        this.targetComponent = targetComponent;
        this.context = context;
        this.frame = frame;
    }

    public Frame getFrame() {
        return frame;
    }

    public T getTargetComponent() {
        return targetComponent;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event that = (Event) o;

        return new EqualsBuilder()
            .append(getTargetComponent(), that.getTargetComponent())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getTargetComponent())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", targetComponent.getClass().getSimpleName())
            .toString();
    }
}
