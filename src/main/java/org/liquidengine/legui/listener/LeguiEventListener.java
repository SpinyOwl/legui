package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiEvent;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public interface LeguiEventListener<T extends LeguiEvent> extends Serializable {
    void update(T event);
}
