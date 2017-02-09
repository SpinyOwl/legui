package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Controller;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEvent extends AbstractEvent {
    private final boolean iconified;

    public WindowIconifyEvent(Controller controller, boolean iconified) {
        super(controller);
        this.iconified = iconified;
    }

    public boolean isIconified() {
        return iconified;
    }
}
