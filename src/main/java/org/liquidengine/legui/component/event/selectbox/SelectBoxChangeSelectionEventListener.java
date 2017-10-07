package org.liquidengine.legui.component.event.selectbox;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface SelectBoxChangeSelectionEventListener extends EventListener<SelectBoxChangeSelectionEvent> {

    /**
     * Used to handle {@link SelectBoxChangeSelectionEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(SelectBoxChangeSelectionEvent event);

}
