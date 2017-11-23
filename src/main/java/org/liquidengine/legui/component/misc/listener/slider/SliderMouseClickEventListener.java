package org.liquidengine.legui.component.misc.listener.slider;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;

/**
 * Slider mouse click event listener. Used to change slider value. Generates slider value change event.
 */
public class SliderMouseClickEventListener implements MouseClickEventListener {

    @Override
    public void process(MouseClickEvent event) {
        if (event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_LEFT) && event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
            Slider slider = (Slider) event.getTargetComponent();
            Vector2f pos = slider.getAbsolutePosition();

            Vector2f cursorPosition = Mouse.getCursorPosition();
            float value;
            float sliderSize = slider.getSliderSize();
            if (Orientation.VERTICAL.equals(slider.getOrientation())) {
                value = 100f * (pos.y + slider.getSize().y - cursorPosition.y - sliderSize / 2f) / (slider.getSize().y - sliderSize);
            } else {
                value = 100f * (cursorPosition.x - pos.x - sliderSize / 2f) / (slider.getSize().x - sliderSize);
            }
            if (value > Slider.MAX_VALUE) {
                value = Slider.MAX_VALUE;
            }
            if (value < Slider.MIN_VALUE) {
                value = Slider.MIN_VALUE;
            }
            EventProcessor.getInstance()
                .pushEvent(new SliderChangeValueEvent(slider, event.getContext(), event.getFrame(), slider.getValue(), value));
            slider.setValue(value);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
