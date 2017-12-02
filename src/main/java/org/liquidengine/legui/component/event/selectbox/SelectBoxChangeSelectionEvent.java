package org.liquidengine.legui.component.event.selectbox;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * @author ShchAlexander.
 */
public class SelectBoxChangeSelectionEvent<T extends SelectBox> extends Event<T> {

    private final String oldValue;
    private final String newValue;

    public SelectBoxChangeSelectionEvent(T component, Context context, Frame frame, String oldValue, String newValue) {
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
