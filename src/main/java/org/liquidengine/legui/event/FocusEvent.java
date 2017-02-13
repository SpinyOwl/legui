package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public class FocusEvent extends AbstractEvent {

    private final boolean   focused;
    private final Component nextFocus;

    public FocusEvent(Component controller, Context context, Component nextFocus, boolean focused) {
        super(controller, context);
        this.focused = focused;
        this.nextFocus = nextFocus;
    }

    public boolean isFocused() {
        return focused;
    }

    public Component getNextFocus() {
        return nextFocus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("focused", focused)
                .append("nextFocus", nextFocus)
                .toString();
    }
}
