package org.liquidengine.legui.component.misc.listener.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * Slider mouse click event listener. Used to change slider value. Generates slider value change event.
 */
public class SliderMouseClickEventListener implements MouseClickEventListener {

    @Override
    public void process(MouseClickEvent event) {
        if (!event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_LEFT) || event.getAction() != MouseClickEvent.MouseClickAction.PRESS) {
            return;
        }
        Slider slider = (Slider) event.getTargetComponent();
        // calculate new value
        float value = SliderHelper.determineSliderValue(slider, Mouse.getCursorPosition());
        // set value & push event
        float oldValue = slider.getValue();
        slider.setValue(value);
        EventProcessorProvider.getInstance().pushEvent(
                new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue, slider.getValue())
        );
    }
}
