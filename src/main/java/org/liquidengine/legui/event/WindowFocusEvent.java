package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEvent<T extends Component> extends Event<T> {

    private final boolean focused;

    public WindowFocusEvent(T component, Context context, Frame frame, boolean focused) {
        super(component, context, frame);
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("focused", focused)
            .toString();
    }
}
