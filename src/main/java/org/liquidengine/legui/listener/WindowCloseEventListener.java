package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.WindowCloseEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface WindowCloseEventListener extends EventListener<WindowCloseEvent> {
    void process(WindowCloseEvent event);
}
