package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.CharEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/14/2017.
 */
public interface CharEventListener extends EventListener<CharEvent> {
    void process(CharEvent event);
}
