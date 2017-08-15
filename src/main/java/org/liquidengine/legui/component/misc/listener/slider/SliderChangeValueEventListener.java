package org.liquidengine.legui.component.misc.listener.slider;


import org.liquidengine.legui.component.misc.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.listener.EventListener;

/**
 * Slider change value event listener. Used to change slider value. Generates slider value change event.
 */
public interface SliderChangeValueEventListener extends EventListener<SliderChangeValueEvent> {

    void process(SliderChangeValueEvent event);
}
