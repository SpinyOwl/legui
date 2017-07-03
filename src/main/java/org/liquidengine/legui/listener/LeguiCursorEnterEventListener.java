package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiCursorEnterEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public interface LeguiCursorEnterEventListener extends LeguiEventListener<LeguiCursorEnterEvent> {
    void process(LeguiCursorEnterEvent event);
}
