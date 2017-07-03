package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class LeguiWindowIconifyEvent<T extends Component> extends LeguiEvent<T> {
    private final boolean iconified;

    public LeguiWindowIconifyEvent(T component, LeguiContext context, boolean iconified) {
        super(component, context);
        this.iconified = iconified;
    }

    public boolean isIconified() {
        return iconified;
    }
}
