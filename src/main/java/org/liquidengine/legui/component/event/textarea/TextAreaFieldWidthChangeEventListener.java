package org.liquidengine.legui.component.event.textarea;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TextAreaFieldWidthChangeEventListener extends EventListener<TextAreaFieldWidthChangeEvent> {

    /**
     * Used to handle {@link TextAreaFieldWidthChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextAreaFieldWidthChangeEvent event);
}
