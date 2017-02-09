package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.AbstractEvent;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public interface EventListener<E extends AbstractEvent> {
    void process(E event);
}
