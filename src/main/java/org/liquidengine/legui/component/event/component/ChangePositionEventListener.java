package org.liquidengine.legui.component.event.component;

import org.liquidengine.legui.listener.EventListener;

public interface ChangePositionEventListener extends EventListener<ChangePositionEvent> {

    /**
     * Used to handle {@link ChangePositionEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(ChangePositionEvent event);
}
