//package org.liquidengine.legui.processor.component.slider;
//
//import org.joml.Vector2f;
//import org.liquidengine.legui.element.Orientation;
//import org.liquidengine.legui.element.Slider;
//import org.liquidengine.legui.event.system.MouseClickEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//import org.liquidengine.legui.util.Util;
//
//import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
//
///**
// * Created by Shcherbin Alexander on 8/25/2016.
// */
//public class SliderMouseClickProcessor implements IGuiProcessor<Slider, MouseClickEvent> {
//    @Override
//    public final void process(Slider gui, MouseClickEvent event, GuiEventProcessorState processorState) {
//        if (!gui.isVisible()) return;
//        if (!gui.isEnabled()) return;
//        if (event.action != GLFW_PRESS) return;
//
//        Vector2f pos = Util.calculatePosition(gui);
//
//        Vector2f cursorPosition = processorState.getCursorPosition();
//        if (Orientation.VERTICAL.equals(gui.getOrientation())) {
//            float value = (pos.y + gui.getSize().y - cursorPosition.y) / gui.getSize().y;
//            gui.setValue(value * 100f);
//        } else {
//            float value = (cursorPosition.x - pos.x) / gui.getSize().x;
//            gui.setValue(value * 100f);
//        }
//    }
//}
