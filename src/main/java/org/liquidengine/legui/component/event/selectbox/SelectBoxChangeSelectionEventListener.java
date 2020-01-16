package org.liquidengine.legui.component.event.selectbox;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface SelectBoxChangeSelectionEventListener<T> extends EventListener<SelectBoxChangeSelectionEvent<T>> {

    /**
     * Used to handle {@link SelectBoxChangeSelectionEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(SelectBoxChangeSelectionEvent<T> event);

}
