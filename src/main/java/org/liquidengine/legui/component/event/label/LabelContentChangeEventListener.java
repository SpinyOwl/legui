package org.liquidengine.legui.component.event.label;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface LabelContentChangeEventListener extends EventListener<LabelContentChangeEvent> {

    /**
     * Used to handle {@link LabelContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(LabelContentChangeEvent event);
}
