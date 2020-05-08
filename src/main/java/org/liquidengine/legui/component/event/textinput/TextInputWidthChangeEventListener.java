package org.liquidengine.legui.component.event.textinput;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TextInputWidthChangeEventListener extends EventListener<TextInputWidthChangeEvent> {

    /**
     * Used to handle {@link TextInputWidthChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextInputWidthChangeEvent event);
}
