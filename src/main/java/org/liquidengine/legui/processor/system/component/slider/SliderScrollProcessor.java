//package org.liquidengine.legui.processor.system.component.slider;
//
//import org.liquidengine.legui.element.Slider;
//import org.liquidengine.legui.event.system.ScrollEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//
///**
// * Created by Shcherbin Alexander on 8/30/2016.
// */
//public class SliderScrollProcessor implements IGuiProcessor<Slider, ScrollEvent> {
//    @Override
//    public void process(Slider gui, ScrollEvent event, GuiEventProcessorState processorState) {
//        float maxValue = 100f;
//        float minValue = 0f;
//        float curValue = gui.getValue();
//        float newVal = (float) (curValue + event.yoffset);
//
//        if (newVal > maxValue) newVal = maxValue;
//        if (newVal < minValue) newVal = minValue;
//
//        gui.setValue(newVal);
//    }
//}
