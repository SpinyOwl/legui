package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.MouseDragEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public interface MouseDragEventListener extends LeguiEventListener<MouseDragEvent> {

    void update(MouseDragEvent event);

}
