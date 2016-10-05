package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.MouseDragEvent;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public interface MouseDragEventListener extends LeguiListener<MouseDragEvent> {

    void update(MouseDragEvent event);

}
