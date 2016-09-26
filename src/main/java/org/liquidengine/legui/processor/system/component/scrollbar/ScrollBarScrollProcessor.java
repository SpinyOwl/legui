//package org.liquidengine.legui.processor.system.component.scrollbar;
//
//import org.liquidengine.legui.element.ScrollBar;
//import org.liquidengine.legui.event.system.ScrollEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//
///**
// * Created by Shcherbin Alexander on 8/30/2016.
// */
//public class ScrollBarScrollProcessor implements IGuiProcessor<ScrollBar, ScrollEvent> {
//
//    @Override
//    public void process(ScrollBar gui, ScrollEvent event, GuiEventProcessorState processorState) {
//        float maxValue = gui.getMaxValue();
//        float minValue = gui.getMinValue();
//        float curValue = gui.getCurValue();
//        float visibleAmount = gui.getVisibleAmount();
//        float newVal = (float) (curValue - event.yoffset * visibleAmount / 10f);
//
//        if (newVal > maxValue) newVal = maxValue;
//        if (newVal < minValue) newVal = minValue;
//
//        gui.setCurValue(newVal);
//        ScrollBartUtil.updateViewport(gui, newVal);
//    }
//
//}
