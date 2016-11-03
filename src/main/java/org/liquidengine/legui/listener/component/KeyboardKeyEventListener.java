package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.KeyboardKeyEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Alexander on 13.10.2016.
 */
public interface KeyboardKeyEventListener extends LeguiEventListener<KeyboardKeyEvent> {

    @Override
    void update(KeyboardKeyEvent event);

}
