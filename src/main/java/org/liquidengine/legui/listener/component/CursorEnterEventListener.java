package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.CursorEnterEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Alexander on 05.10.2016.
 */
public interface CursorEnterEventListener extends LeguiEventListener<CursorEnterEvent> {

    void update(CursorEnterEvent event);

}
