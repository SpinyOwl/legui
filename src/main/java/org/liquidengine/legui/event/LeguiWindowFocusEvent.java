package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class LeguiWindowFocusEvent<T extends Component> extends LeguiEvent<T> {
    private final boolean focused;

    public LeguiWindowFocusEvent(T component, LeguiContext context, boolean focused) {
        super(component, context);
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

}
