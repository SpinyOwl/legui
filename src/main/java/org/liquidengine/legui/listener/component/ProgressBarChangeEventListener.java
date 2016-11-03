package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.ProgressBarChangeEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public interface ProgressBarChangeEventListener extends LeguiEventListener<ProgressBarChangeEvent> {

    void update(ProgressBarChangeEvent event);

}
