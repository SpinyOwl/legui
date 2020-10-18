package org.liquidengine.legui.component.event.checkbox;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface CheckBoxWidthChangeEventListener extends EventListener<CheckBoxWidthChangeEvent> {

    /**
     * Used to handle {@link CheckBoxWidthChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(CheckBoxWidthChangeEvent event);
}
