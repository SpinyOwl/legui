package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowCloseEvent implements Event {
    private Component component;

    public WindowCloseEvent(Component component) {
        this.component = component;
    }

    @Override
    public Component getComponent() {
        return component;
    }
}
