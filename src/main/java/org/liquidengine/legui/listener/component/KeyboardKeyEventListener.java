package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.KeyboardKeyEvent;

/**
 * Created by Alexander on 13.10.2016.
 */
public interface KeyboardKeyEventListener extends IEventListener<KeyboardKeyEvent> {

    @Override
    void update(KeyboardKeyEvent event);

}
