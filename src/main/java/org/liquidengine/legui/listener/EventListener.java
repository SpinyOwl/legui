package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.Event;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public interface EventListener<E extends Event> {
    void process(E event);
}
