package org.liquidengine.legui.processor.system.component.slider;

import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemScrollEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class SliderScrollProcessor implements LeguiComponentEventProcessor<Slider, SystemScrollEvent> {
    @Override
    public void process(Slider gui, SystemScrollEvent event, LeguiContext leguiContext) {
        float maxValue = 100f;
        float minValue = 0f;
        float curValue = gui.getValue();
        float newVal = (float) (curValue + event.yoffset);

        if (newVal > maxValue) newVal = maxValue;
        if (newVal < minValue) newVal = minValue;

        gui.setValue(newVal);
    }
}
