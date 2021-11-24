package com.spinyowl.legui.component.misc.listener.slider;

import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.component.event.slider.SliderChangeValueEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

/**
 * Slider mouse click event listener. Used to change slider value. Generates slider value change
 * event.
 */
public class SliderMouseClickEventListener implements MouseClickEventListener {

  @Override
  public void process(MouseClickEvent event) {
    if (!event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_LEFT)
        || event.getAction() != MouseClickEvent.MouseClickAction.PRESS) {
      return;
    }
    Slider slider = (Slider) event.getTargetComponent();
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
