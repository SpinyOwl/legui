package org.liquidengine.legui.component.event.widget;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface WidgetCloseEventListener<T extends WidgetCloseEvent> extends EventListener<T> {
    /**
     * Used to handle {@link WidgetCloseEvent} event.
     *
     * @param event event to handle.
     */
    void process(WidgetCloseEvent event);
}
