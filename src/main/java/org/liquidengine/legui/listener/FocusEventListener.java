package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.FocusEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public interface FocusEventListener extends EventListener<FocusEvent> {
    void process(FocusEvent event);
}
