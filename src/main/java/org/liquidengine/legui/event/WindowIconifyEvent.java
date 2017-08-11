package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEvent<T extends Component> extends Event<T> {
    private final boolean iconified;

    public WindowIconifyEvent(T component, Context context, Frame frame, boolean iconified) {
        super(component, context, frame);
        this.iconified = iconified;
    }

    public boolean isIconified() {
        return iconified;
    }
}
