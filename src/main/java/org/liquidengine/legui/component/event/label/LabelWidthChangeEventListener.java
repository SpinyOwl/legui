package org.liquidengine.legui.component.event.label;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface LabelWidthChangeEventListener extends EventListener<LabelWidthChangeEvent> {

    /**
     * Used to handle {@link LabelWidthChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(LabelWidthChangeEvent event);
}
