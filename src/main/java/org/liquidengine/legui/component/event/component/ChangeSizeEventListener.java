package org.liquidengine.legui.component.event.component;

import org.liquidengine.legui.listener.EventListener;

public interface ChangeSizeEventListener extends EventListener<ChangeSizeEvent> {

    /**
     * Used to handle {@link ChangeSizeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(ChangeSizeEvent event);

    
}
