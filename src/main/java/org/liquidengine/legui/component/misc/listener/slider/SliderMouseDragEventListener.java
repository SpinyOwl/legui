package org.liquidengine.legui.component.misc.listener.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * Slider mouse drag event listener. Used to change slider value. Generates slider value change event.
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
                new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), oldValue, slider.getValue())
        );
    }
}
