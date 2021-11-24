package com.spinyowl.legui.component.misc.listener.slider;

import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.component.event.slider.SliderChangeValueEvent;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.MouseDragEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

/**
 * Slider mouse drag event listener. Used to change slider value. Generates slider value change
 * event.
 */
public class SliderMouseDragEventListener implements MouseDragEventListener {

  @Override
  public void process(MouseDragEvent event) {
    Slider slider = (Slider) event.getTargetComponent();
    if (!Mouse.MouseButton.MOUSE_BUTTON_LEFT.isPressed()) {
      return;
    }
    // calculate new value
    float value = SliderHelper.determineSliderValue(slider, Mouse.getCursorPosition());
    // set value & push event
    float oldValue = slider.getValue();
    slider.setValue(value);
    EventProcessorProvider.getInstance().pushEvent(
        new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue,
            slider.getValue())
    );
  }
}
