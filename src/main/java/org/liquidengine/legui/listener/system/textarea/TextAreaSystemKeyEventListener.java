package org.liquidengine.legui.listener.system.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextAreaSystemKeyEventListener implements SystemEventListener<TextArea, SystemKeyEvent> {
    @Override
    public void update(SystemKeyEvent event, TextArea gui, LeguiContext leguiContext) {
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
                    LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
                    if (some.lineCaretIndex > 0) {
                        int nl = lines[some.lineCaretIndex - 1].length() + 1;
                        int np = caretPosition - (some.caretPositionInLine >= nl - 1 ? some.caretPositionInLine + 1 : nl);
                        gui.setCaretPosition(np);
                    } else {
                        gui.setCaretPosition(0);
                    }
                }
            } else if (key == GLFW_KEY_DOWN && PRESS) {
                if (caretPosition < textState.length()) {
                    String text = textState.getText();
                    String[] lines = text.split("\n", -1);
                    LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
                    if (some.lineCaretIndex < lines.length - 1) {
                        int nl = lines[some.lineCaretIndex + 1].length() + 1;
                        int cl = lines[some.lineCaretIndex].length() + 1;
                        int np = 0;
                        if (some.caretPositionInLine >= nl - 1) {
                            np = caretPosition + nl + cl - 1 - some.caretPositionInLine;
                        } else {
                            np = caretPosition + cl;
                        }
                        gui.setCaretPosition(np);
                    } else {
                        gui.setCaretPosition(text.length());
                    }
                }
            } else if (key == GLFW_KEY_HOME && PRESS) {
                String text = textState.getText();
                String[] lines = text.split("\n", -1);
                LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
                gui.setCaretPosition(caretPosition - some.caretPositionInLine);
            } else if (key == GLFW_KEY_END && PRESS) {
                String text = textState.getText();
                String[] lines = text.split("\n", -1);
                LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
                int cl = lines[some.lineCaretIndex].length();
                int delta = cl - some.caretPositionInLine;
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

    private LineData getStartLineIndexAndLineNumber(String[] lines, int caretPosition) {
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
        return new LineData(caretPosition - caretOffset, caretLine);
    }

    private static class LineData {
        private int caretPositionInLine;
        private int lineCaretIndex;

        public LineData(int caretPositionInLine, int lineCaretIndex) {
            this.caretPositionInLine = caretPositionInLine;
            this.lineCaretIndex = lineCaretIndex;
        }
    }
}
