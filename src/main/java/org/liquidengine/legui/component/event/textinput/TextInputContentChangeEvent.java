package org.liquidengine.legui.component.event.textinput;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * @author ShchAlexander.
 */
public class TextInputContentChangeEvent<T extends TextInput> extends Event<T> {

    /**
     * Old value.
     */
    private final String oldValue;
    /**
     * New value.
     */
    private final String newValue;

    public TextInputContentChangeEvent(T component, Context context, Frame frame, String oldValue, String newValue) {
        super(component, context, frame);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns old value.
     *
     * @return old value.
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Returns new value.
     *
     * @return new value.
     */
    public String getNewValue() {
        return newValue;
    }
}
