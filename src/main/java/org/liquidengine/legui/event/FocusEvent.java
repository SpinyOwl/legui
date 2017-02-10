package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Controller;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public class FocusEvent extends AbstractEvent {

    private final boolean focused;

    public FocusEvent(Controller controller, boolean focused) {
        super(controller);
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("focused", focused)
                .toString();
    }
}
