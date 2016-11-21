package org.liquidengine.legui.listener.system.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.SliderChangeEvent;
import org.liquidengine.legui.processor.LeguiEventListenerProcessor;

/**
 * Created by Alexander on 18.10.2016.
 */
public final class SliderUpdateUtil {
    private SliderUpdateUtil() {
    }

    static void updateSliderValue(Slider gui, float newVal, LeguiContext context) {
        float value = gui.getValue();
        gui.setValue(newVal);
        SliderChangeEvent event = new SliderChangeEvent(gui, value, newVal);
        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor == null) {
            gui.getLeguiEventListeners().getListeners(SliderChangeEvent.class).forEach(l -> l.update(event));
        } else {
            leguiEventProcessor.pushEvent(event);
        }
    }
}
