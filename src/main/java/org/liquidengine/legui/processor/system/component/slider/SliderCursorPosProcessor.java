//package org.liquidengine.legui.processor.system.component.slider;
//
//import org.joml.Vector2f;
//import org.liquidengine.legui.element.Orientation;
//import org.liquidengine.legui.element.Slider;
//import org.liquidengine.legui.event.system.CursorPosEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//import org.liquidengine.legui.util.Util;
//import org.lwjgl.glfw.GLFW;
//
///**
// * Created by Alexander on 28.08.2016.
// */
//public class SliderCursorPosProcessor implements IGuiProcessor<Slider,CursorPosEvent> {
//    @Override
//    public void process(Slider gui, CursorPosEvent event, GuiEventProcessorState processorState) {
//        if (!gui.isVisible()) return;
//        if (!gui.isEnabled()) return;
//        if (processorState.getFocusedGui() != gui) return;
//        if (!processorState.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) return;
//
//        Vector2f pos = Util.calculatePosition(gui);
//
//        Vector2f cursorPosition = processorState.getCursorPosition();
//        if (Orientation.VERTICAL.equals(gui.getOrientation())) {
//            float value = ((pos.y + gui.getSize().y) - cursorPosition.y) / gui.getSize().y;
//            gui.setValue(value * 100f);
//        } else {
//            float value = (cursorPosition.x - pos.x) / gui.getSize().x;
//            gui.setValue(value * 100f);
//        }
//    }
//}
