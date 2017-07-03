package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiWindowRefreshEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public interface LeguiWindowRefreshEventListener extends LeguiEventListener<LeguiWindowRefreshEvent> {
    void process(LeguiWindowRefreshEvent event);
}
