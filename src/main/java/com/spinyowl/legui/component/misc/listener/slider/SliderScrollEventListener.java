package com.spinyowl.legui.component.misc.listener.slider;

import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.component.event.slider.SliderChangeValueEvent;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.listener.ScrollEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

/**
 * Slider mouse scroll event listener.
 */
public class SliderScrollEventListener implements ScrollEventListener {

  @Override
  public void process(ScrollEvent event) {
    Slider slider = (Slider) event.getTargetComponent();
    float oldValue = slider.getValue();
    float newValue = oldValue;
    // respect step size
    if (slider.getStepSize() > 0f) {
      newValue = newValue + slider.getStepSize() * (float) event.getYoffset();
    } else {
      newValue = newValue + (float) event.getYoffset();
    }
    // check for min/max values
    if (newValue > slider.getMaxValue()) {
      newValue = slider.getMaxValue();
    }
    if (newValue < slider.getMinValue()) {
      newValue = slider.getMinValue();
    }
    // set value & push event
    slider.setValue(newValue);
    EventProcessorProvider.getInstance().pushEvent(
        new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue, newValue)
    );
  }
}
