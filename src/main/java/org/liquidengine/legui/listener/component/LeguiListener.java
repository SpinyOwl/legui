package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.LeguiComponentEvent;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public interface LeguiListener<T extends LeguiComponentEvent> {
    void update(T event);
}
