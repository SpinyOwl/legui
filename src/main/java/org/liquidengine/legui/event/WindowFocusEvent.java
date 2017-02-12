package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEvent extends AbstractEvent {
    private final boolean focused;

    public WindowFocusEvent(Component controller, boolean focused) {
        super(controller);
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

}
