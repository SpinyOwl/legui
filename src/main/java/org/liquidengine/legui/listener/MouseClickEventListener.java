package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.MouseClickEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public interface MouseClickEventListener extends EventListener<MouseClickEvent> {
    void process(MouseClickEvent event);
}
