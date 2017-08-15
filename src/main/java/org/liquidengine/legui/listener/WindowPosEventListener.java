package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.WindowPosEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface WindowPosEventListener extends EventListener<WindowPosEvent> {

    void process(WindowPosEvent event);
}
