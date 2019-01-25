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
        Slider slider = (Slider) event.getTargetComponent();
        float oldValue =  slider.getValue();
        float newValue = oldValue;
        // respect step size
        if (slider.getStepSize() > 0) {
            newValue += event.getYoffset() * slider.getStepSize();
        } else {
            newValue += event.getYoffset();
        }
        // check for min/max values
        newValue = Math.max(slider.getMinValue(), Math.min(slider.getMaxValue(), newValue));
        // set value & push event
        slider.setValue(newValue);
        EventProcessor.getInstance().pushEvent(new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue, slider.getValue()));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
