package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.AbstractComponentEvent;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public interface IEventListener<T extends AbstractComponentEvent> {
    void update(T event);
}
