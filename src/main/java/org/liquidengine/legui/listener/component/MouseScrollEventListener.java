package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.MouseScrollEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Alexander on 13.10.2016.
 */
public interface MouseScrollEventListener extends LeguiEventListener<MouseScrollEvent> {
    void update(MouseScrollEvent event);
}
