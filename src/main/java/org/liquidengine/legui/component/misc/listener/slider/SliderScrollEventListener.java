package org.liquidengine.legui.component.misc.listener.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.ScrollEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;

/**
 * Slider mouse scroll event listener.
 */
public class SliderScrollEventListener implements ScrollEventListener {

    @Override
    public void process(ScrollEvent event) {
        Slider slider = (Slider) event.getComponent();
        float curValue = slider.getValue();
        float value = (float) (curValue + event.getYoffset());

        if (value > Slider.MAX_VALUE) {
            value = Slider.MAX_VALUE;
        }
        if (value < Slider.MIN_VALUE) {
            value = Slider.MIN_VALUE;
        }

        EventProcessor.getInstance().pushEvent(new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), slider.getValue(), value));
        slider.setValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
