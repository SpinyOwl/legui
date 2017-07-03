package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiWindowPosEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface LeguiWindowPosEventListener extends LeguiEventListener<LeguiWindowPosEvent> {
    void process(LeguiWindowPosEvent event);
}
