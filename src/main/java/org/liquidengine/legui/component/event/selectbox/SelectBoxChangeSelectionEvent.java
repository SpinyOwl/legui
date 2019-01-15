package org.liquidengine.legui.component.event.selectbox;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * @author ShchAlexander.
 */
public class SelectBoxChangeSelectionEvent<T> extends Event<SelectBox<T>> {

    private final T oldValue;
    private final T newValue;

    public SelectBoxChangeSelectionEvent(SelectBox<T> component, Context context, Frame frame, T oldValue, T newValue) {
        super(component, context, frame);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns old value.
     *
     * @return old value.
     */
    public T getOldValue() {
        return oldValue;
    }

    /**
     * Returns new value.
     *
     * @return new value.
     */
    public T getNewValue() {
        return newValue;
    }
}
