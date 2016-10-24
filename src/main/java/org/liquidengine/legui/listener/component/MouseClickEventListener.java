package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Shcherbin Alexander on 10/6/2016.
 */
public interface MouseClickEventListener extends LeguiEventListener<MouseClickEvent> {

    void update(MouseClickEvent event);

}
