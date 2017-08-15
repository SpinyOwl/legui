package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.ScrollEvent;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public interface ScrollEventListener extends EventListener<ScrollEvent> {

    void process(ScrollEvent event);
}
