package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.CursorEnterEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public interface CursorEnterEventListener extends EventListener<CursorEnterEvent> {
    void process(CursorEnterEvent event);
}
