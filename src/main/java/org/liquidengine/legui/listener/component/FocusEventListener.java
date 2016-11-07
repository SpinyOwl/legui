package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.FocusEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Alexander on 07.11.2016.
 */
public interface FocusEventListener extends LeguiEventListener<FocusEvent>{
    @Override
    void update(FocusEvent event);
}
