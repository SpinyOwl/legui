package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEvent extends AbstractEvent {
    private final boolean iconified;

    public WindowIconifyEvent(Component controller, Context context, boolean iconified) {
        super(controller, context);
        this.iconified = iconified;
    }

    public boolean isIconified() {
        return iconified;
    }
}
