package org.liquidengine.legui.component.event.selelctbox;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface SelectBoxChangeSelectionEventListener extends EventListener<SelectBoxChangeSelectionEvent> {

    @Override
    void process(SelectBoxChangeSelectionEvent event);

}
