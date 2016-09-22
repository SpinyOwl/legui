package org.liquidengine.legui.processor.component.textinput;

/**
 * Created by Alexander on 28.08.2016.
 */
//public class TextInputKeyProcessor implements IGuiProcessor<TextInput, KeyEvent> {
//    @Override
//    public void process(TextInput gui, KeyEvent event, GuiEventProcessorState processorState) {
//        if (gui.isFocused() && gui.isEditable()) {
//            int key = event.key;
//            int caretPosition = gui.getCaretPosition();
//            if (key == GLFW_KEY_LEFT && event.action != GLFW_RELEASE) {
//                if (caretPosition > 0) {
//                    gui.setCaretPosition(caretPosition - 1);
//                }
//            } else if (key == GLFW_KEY_RIGHT && event.action != GLFW_RELEASE) {
//                if (caretPosition < gui.getTextState().length()) {
//                    gui.setCaretPosition(caretPosition + 1);
//                }
//            } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && event.action != GLFW_RELEASE) {
//                gui.setCaretPosition(0);
//            } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && event.action != GLFW_RELEASE) {
//                gui.setCaretPosition(gui.getTextState().length());
//            } else if (key == GLFW_KEY_BACKSPACE && event.action != GLFW_RELEASE) {
//                if (caretPosition != 0) {
//                    gui.deleteCharAt(caretPosition - 1);
//                    gui.setCaretPosition(caretPosition - 1);
//                }
//            } else if (key == GLFW_KEY_DELETE && event.action != GLFW_RELEASE) {
//                if (caretPosition != gui.length()) {
//                    gui.deleteCharAt(caretPosition);
//                }
//            }
//        }
//
//    }
//}
