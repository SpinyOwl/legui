package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.CursorEnterEvent;

/**
 * Created by Alexander on 05.10.2016.
 */
public interface CursorEnterListener extends LeguiListener<CursorEnterEvent> {

    void update(CursorEnterEvent event);

}
