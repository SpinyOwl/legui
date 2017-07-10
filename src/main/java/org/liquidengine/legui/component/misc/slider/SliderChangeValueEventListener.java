package org.liquidengine.legui.component.misc.slider;

import org.liquidengine.legui.listener.EventListener;

/**
 * Slider change value event listener. Used to change slider value. Generates slider value change event.
 */
public interface SliderChangeValueEventListener extends EventListener<SliderChangeValueEvent> {
    void process(SliderChangeValueEvent event);
}
