package org.liquidengine.legui.component.misc.listener.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.ScrollEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;

import java.math.BigDecimal;

/**
 * Slider mouse scroll event listener.
 */
public class SliderScrollEventListener implements ScrollEventListener {

    @Override
    public void process(ScrollEvent event) {
        Slider slider = (Slider) event.getTargetComponent();
        BigDecimal oldValue =  slider.getValuePrecise();
        BigDecimal newValue = new BigDecimal(oldValue.doubleValue());
        // respect step size
        if (slider.getStepSize() > 0) {
            newValue = newValue.add(new BigDecimal(event.getYoffset() * slider.getStepSize()));
        } else {
            newValue = newValue.add(new BigDecimal(event.getYoffset()));
        }
        // check for min/max values
        if (newValue.floatValue() > slider.getMaxValue()) {
            newValue = new BigDecimal(slider.getMaxValue());
        }
        if (newValue.floatValue() < slider.getMinValue()) {
            newValue = new BigDecimal(slider.getMinValue());
        }
        // set value & push event
        slider.setValuePrecise(newValue);
        EventProcessor.getInstance().pushEvent(new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue.floatValue(), newValue.floatValue()));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
