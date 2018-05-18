package org.liquidengine.legui.event;

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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("focused", focused)
            .append("nextFocus", nextFocus)
            .toString();
    }
}
