package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowRefreshEvent implements Event {
    private final Component component;

    public WindowRefreshEvent(Component component) {
        this.component = component;
    }

    @Override
    public Component getComponent() {
        return component;
    }
}
