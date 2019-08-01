package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEvent<T extends Component> extends Event<T> {

    private final double xoffset;
    private final double yoffset;

    public ScrollEvent(T component, Context context, Frame frame, double xoffset, double yoffset) {
        super(component, context, frame);
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("xoffset", xoffset)
            .append("yoffset", yoffset)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ScrollEvent<?> that = (ScrollEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(xoffset, that.xoffset)
            .append(yoffset, that.yoffset)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(xoffset)
            .append(yoffset)
            .toHashCode();
    }
}
