package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.MouseScrollEvent;

/**
 * Created by Alexander on 13.10.2016.
 */
public interface MouseScrollEventListener extends IEventListener<MouseScrollEvent> {
    void update(MouseScrollEvent event);
}
