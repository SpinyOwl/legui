package org.liquidengine.legui.component.event.textarea;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TextAreaFieldHeightChangeEventListener extends EventListener<TextAreaFieldHeightChangeEvent> {

    /**
     * Used to handle {@link TextAreaFieldHeightChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextAreaFieldHeightChangeEvent event);
}
