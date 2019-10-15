package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Focus event. Occurs when component receive or lose focus.
 */
public class FocusEvent<T extends Component> extends Event<T> {

    private final boolean focused;
    private final Component nextFocus;

    /**
     * Used to create focus event.
     *
     * @param component event receiver.
     * @param context context.
     * @param nextFocus focus receiver.
     * @param focused state of component.
     * @param frame frame.
     */
    public FocusEvent(T component, Context context, Frame frame, Component nextFocus, boolean focused) {
        super(component, context, frame);
        this.focused = focused;
        this.nextFocus = nextFocus;
    }

    /**
     * Returns true if component receive focus.
     *
     * @return true if component receive focus.
     */
    public boolean isFocused() {
        return focused;
    }

    /**
     * Returns component which received focus.
     *
     * @return component which received focus.
     */
    public Component getNextFocus() {
        return nextFocus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("focused", focused)
            .append("nextFocus", nextFocus)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FocusEvent<?> that = (FocusEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(focused, that.focused)
            .append(nextFocus, that.nextFocus)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(focused)
            .append(nextFocus)
            .toHashCode();
    }
}
