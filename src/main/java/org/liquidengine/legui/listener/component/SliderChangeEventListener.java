package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.SliderChangeEvent;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public interface SliderChangeEventListener extends IEventListener<SliderChangeEvent> {

    void update(SliderChangeEvent event);
    
}
