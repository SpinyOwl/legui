package org.liquidengine.legui.processor.system.component.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextAreaKeyProcessor implements LeguiComponentEventProcessor<TextArea, SystemKeyEvent> {
    @Override
    public void process(TextArea gui, SystemKeyEvent event, LeguiContext leguiContext) {
        if (gui.isFocused() && gui.isEditable()) {
            int key = event.key;
            int caretPosition = gui.getCaretPosition();
            boolean PRESS = event.action != GLFW_RELEASE;
            TextState textState = gui.getTextState();
            if (key == GLFW_KEY_LEFT && PRESS) {
                if (caretPosition > 0) {
                    gui.setCaretPosition(caretPosition - 1);
                }
            } else if (key == GLFW_KEY_RIGHT && PRESS) {
                if (caretPosition < textState.length()) {
                    gui.setCaretPosition(caretPosition + 1);
                }
            } else if (key == GLFW_KEY_UP && PRESS) {
                if (caretPosition > 0) {
                    String text = textState.getText();
                    String[] lines = text.split("\n", -1);
                    int[] some = some(lines, caretPosition);
                    if (some[1] > 0) {
                        int nl = lines[some[1] - 1].length() + 1;
                        int delta = nl + some[0];
                        int np = caretPosition - delta + (some[0] > nl ? nl : some[0]);
                        gui.setCaretPosition(np);
                    } else {
                        gui.setCaretPosition(0);
                    }
                }
            } else if (key == GLFW_KEY_DOWN && PRESS) {
                if (caretPosition < textState.length()) {
                    String text = textState.getText();
                    String[] lines = text.split("\n", -1);
                    int[] some = some(lines, caretPosition);
                    if (some[1] < lines.length - 1) {
                        int cl = lines[some[1]].length() + 1;
                        int nl = lines[some[1] + 1].length() + 1;
                        int delta = cl - some[0];
                        int np = caretPosition + delta + (some[0] > nl ? nl : some[0]);
                        gui.setCaretPosition(np);
                    } else {
                        gui.setCaretPosition(text.length());
                    }
                }
            } else if (key == GLFW_KEY_HOME && PRESS) {
                String text = textState.getText();
                String[] lines = text.split("\n", -1);
                int[] some = some(lines, caretPosition);
                gui.setCaretPosition(caretPosition - some[0]);
            } else if (key == GLFW_KEY_END && PRESS) {
                String text = textState.getText();
                String[] lines = text.split("\n", -1);
                int[] some = some(lines, caretPosition);
                int cl = lines[some[1]].length();
                int delta = cl - some[0];
                int np = caretPosition + delta;
                gui.setCaretPosition(np);
            } else if (key == GLFW_KEY_ENTER && PRESS) {
                if (gui.isEditable()) {
                    gui.getTextState().insert(caretPosition, "\n");
                    gui.setCaretPosition(caretPosition + 1);
                }
            } else if (key == GLFW_KEY_BACKSPACE && PRESS) {
                if (gui.isEditable() && caretPosition != 0) {
                    textState.deleteCharAt(caretPosition - 1);
                    gui.setCaretPosition(caretPosition - 1);
                }
            } else if (key == GLFW_KEY_DELETE && PRESS) {
                if (gui.isEditable() && caretPosition != textState.length()) {
                    textState.deleteCharAt(caretPosition);
                }
            }
        }


    }

    public int[] some(String[] lines, int caretPosition) {
        int caretLine = 0;
        int caretOffset = 0;
        for (String line : lines) {
            int newOffset = caretOffset + line.length();
            if (newOffset >= caretPosition) {
                break;
            }
            caretLine++;
            caretOffset = newOffset + 1;
        }
        return new int[]{caretPosition - caretOffset, caretLine};
    }
}
