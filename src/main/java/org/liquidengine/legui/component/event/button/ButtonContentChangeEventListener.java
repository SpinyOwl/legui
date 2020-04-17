package org.liquidengine.legui.component.event.button;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface ButtonContentChangeEventListener extends EventListener<ButtonContentChangeEvent> {

    /**
     * Used to handle {@link ButtonContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(ButtonContentChangeEvent event);
}
