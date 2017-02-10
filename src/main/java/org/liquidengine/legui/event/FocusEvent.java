package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Controller;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public class FocusEvent extends AbstractEvent {

    private final boolean    focused;
    private final Controller nextFocus;

    public FocusEvent(Controller controller, Controller nextFocus, boolean focused) {
        super(controller);
        this.focused = focused;
        this.nextFocus = nextFocus;
    }

    public boolean isFocused() {
        return focused;
    }

    public Controller getNextFocus() {
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
