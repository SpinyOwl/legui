package org.liquidengine.legui.component.event.button;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface ButtonWidthChangeEventListener extends EventListener<ButtonWidthChangeEvent> {

    /**
     * Used to handle {@link ButtonWidthChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(ButtonWidthChangeEvent event);
}
