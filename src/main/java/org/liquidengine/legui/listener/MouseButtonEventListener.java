package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.MouseButtonEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public interface MouseButtonEventListener extends EventListener<MouseButtonEvent> {
    void process(MouseButtonEvent event);
}
