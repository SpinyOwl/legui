package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Controller;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEvent extends AbstractEvent {
    private final boolean focused;

    public WindowFocusEvent(Controller controller, boolean focused) {
        super(controller);
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

}
