package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.AbstractComponentEvent;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public interface IEventListener<T extends AbstractComponentEvent> extends Serializable{
    void update(T event);
}
