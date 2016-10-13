package org.liquidengine.legui.event.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;

/**
 * Created by Alexander on 13.10.2016.
 */
public class MouseScrollEvent extends AbstractComponentEvent {
    private final double xoffset;
    private final double yoffset;

    public MouseScrollEvent(Component component, double xoffset, double yoffset) {
        super(component);
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MouseScrollEvent that = (MouseScrollEvent) o;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("xoffset", xoffset)
                .append("yoffset", yoffset)
                .toString();
    }
}
