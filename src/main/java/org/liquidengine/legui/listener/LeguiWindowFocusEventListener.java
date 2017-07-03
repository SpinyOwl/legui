package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiWindowFocusEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface LeguiWindowFocusEventListener extends LeguiEventListener<LeguiWindowFocusEvent> {
    void process(LeguiWindowFocusEvent event);
}
