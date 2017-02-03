package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.Event;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEvent implements Event {
    private final Component component;
    private final boolean iconified;

    public WindowIconifyEvent(Component component, boolean iconified) {
        this.component = component;
        this.iconified = iconified;
    }

    @Override
    public Component getComponent() {
        return component;
    }

    public boolean isIconified() {
        return iconified;
    }
}
