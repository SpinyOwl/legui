//package org.liquidengine.legui.processor.component.checkbox;
//
//import org.liquidengine.legui.element.CheckBox;
//import org.liquidengine.legui.event.system.MouseClickEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//
//import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
//
///**
// * Created by Alexander on 28.08.2016.
// */
//public class CheckBoxMouseClickProcessor implements IGuiProcessor<CheckBox, MouseClickEvent> {
//    @Override
//    public void process(CheckBox gui, MouseClickEvent event, GuiEventProcessorState processorState) {
//        if (processorState.getFocusedGui() == gui) {
//            if (event.action == GLFW_RELEASE) {
//                CheckBox checkBox = (CheckBox) gui;
//                checkBox.setChecked(!checkBox.isChecked());
//            }
//        }
//    }
//}
