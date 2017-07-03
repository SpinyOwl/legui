package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiKeyEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/13/2017.
 */
public interface LeguiKeyEventListener extends LeguiEventListener<LeguiKeyEvent> {
    void process(LeguiKeyEvent event);
}
