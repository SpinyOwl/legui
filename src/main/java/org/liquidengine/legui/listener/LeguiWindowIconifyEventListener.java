package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiWindowIconifyEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface LeguiWindowIconifyEventListener extends LeguiEventListener<LeguiWindowIconifyEvent> {
    void process(LeguiWindowIconifyEvent event);
}
