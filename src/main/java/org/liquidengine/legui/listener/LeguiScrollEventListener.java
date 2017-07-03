package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiScrollEvent;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public interface LeguiScrollEventListener extends LeguiEventListener<LeguiScrollEvent> {
    void process(LeguiScrollEvent event);
}
