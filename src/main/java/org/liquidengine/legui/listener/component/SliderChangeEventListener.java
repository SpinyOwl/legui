package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.SliderChangeEvent;
import org.liquidengine.legui.listener.LeguiEventListener;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public interface SliderChangeEventListener extends LeguiEventListener<SliderChangeEvent> {

    void update(SliderChangeEvent event);
    
}
