package org.liquidengine.legui.component.event.tooltip;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author ShchAlexander.
 */
public interface TooltipTextSizeChangeEventListener extends EventListener<TooltipTextSizeChangeEvent> {

    /**
     * Used to handle {@link TooltipTextSizeChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TooltipTextSizeChangeEvent event);
}
