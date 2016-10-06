package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.MouseClickEvent;

/**
 * Created by Shcherbin Alexander on 10/6/2016.
 */
public interface MouseClickListener extends IEventListener<MouseClickEvent> {

    void update(MouseClickEvent event);

}
