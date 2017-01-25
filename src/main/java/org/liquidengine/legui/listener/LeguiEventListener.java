package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiEvent;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public interface LeguiEventListener<E extends LeguiEvent> {
    void process(E event);
}
