package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEvent<T extends Component> extends AbstractEvent<T> {
    private final boolean iconified;

    public WindowIconifyEvent(T component, Context context, boolean iconified) {
        super(component, context);
        this.iconified = iconified;
    }

    public boolean isIconified() {
        return iconified;
    }
}
