package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 2/2/2017.
 */
public class WindowSizeEvent<T extends Component> extends Event<T> {

    private final int width;
    private final int height;

    public WindowSizeEvent(T component, Context context, Frame frame, int width, int height) {
        super(component, context, frame);
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("width", width)
            .append("height", height)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WindowSizeEvent<?> that = (WindowSizeEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(width, that.width)
            .append(height, that.height)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(width)
            .append(height)
            .toHashCode();
    }
}
