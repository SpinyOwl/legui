package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEvent extends AbstractEvent {
    private final boolean iconified;

    public WindowIconifyEvent(Component controller, Frame frame, boolean iconified) {
        super(controller, frame);
        this.iconified = iconified;
    }

    public boolean isIconified() {
        return iconified;
    }
}
