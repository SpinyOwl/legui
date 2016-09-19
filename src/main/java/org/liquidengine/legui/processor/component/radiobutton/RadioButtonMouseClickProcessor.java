//package org.liquidengine.legui.processor.component.radiobutton;
//
//import org.liquidengine.legui.element.RadioButton;
//import org.liquidengine.legui.event.system.MouseClickEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//
//import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
//
///**
// * Created by Shcherbin Alexander on 8/25/2016.
// */
//public class RadioButtonMouseClickProcessor implements IGuiProcessor<RadioButton, MouseClickEvent> {
//    public void process(RadioButton gui, MouseClickEvent event, GuiEventProcessorState processorState) {
//        if (processorState.getFocusedGui() == gui) {
//            if (event.action == GLFW_RELEASE) {
//                gui.setSelected(true);
//            }
//        }
//    }
//}
