package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEvent implements Event {
    private final Component component;
    private final boolean   focused;

    public WindowFocusEvent(Component component, boolean focused) {
        this.component = component;
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    @Override
    public Component getComponent() {
        return component;
    }
}
