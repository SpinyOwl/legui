package org.liquidengine.legui.component.event.textarea;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TextAreaFieldContentChangeEventListener extends EventListener<TextAreaFieldContentChangeEvent> {

    /**
     * Used to handle {@link TextAreaFieldContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextAreaFieldContentChangeEvent event);
}
