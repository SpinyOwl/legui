package org.liquidengine.legui.listener.system.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemScrollEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.liquidengine.legui.listener.system.slider.SliderUpdateUtil.updateSliderValue;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class SliderSystemScrollEventListener implements SystemEventListener<Slider, SystemScrollEvent> {
    @Override
    public void update(SystemScrollEvent event, Slider gui, LeguiContext leguiContext) {
        float maxValue = 100f;
        float minValue = 0f;
        float curValue = gui.getValue();
        float newVal   = (float) (curValue + event.yoffset);

        if (newVal > maxValue) newVal = maxValue;
        if (newVal < minValue) newVal = minValue;

        updateSliderValue(gui, newVal, leguiContext);
    }


}
