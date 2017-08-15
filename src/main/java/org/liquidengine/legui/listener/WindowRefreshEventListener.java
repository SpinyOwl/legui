package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.WindowRefreshEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface WindowRefreshEventListener extends EventListener<WindowRefreshEvent> {

    void process(WindowRefreshEvent event);
}
