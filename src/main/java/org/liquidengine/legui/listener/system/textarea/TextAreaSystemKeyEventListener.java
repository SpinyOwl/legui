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
            boolean pressed = event.action != GLFW_RELEASE;
            TextState textState = gui.getTextState();
            if (key == GLFW_KEY_LEFT && pressed) {
                keyLeftAction(gui, caretPosition);
            } else if (key == GLFW_KEY_RIGHT && pressed) {
                keyRightAction(gui, caretPosition, textState);
            } else if (key == GLFW_KEY_UP && pressed) {
                keyUpAction(gui, caretPosition, textState);
            } else if (key == GLFW_KEY_DOWN && pressed) {
                keyDownAction(gui, caretPosition, textState);
            } else if (key == GLFW_KEY_HOME && pressed) {
                keyHomeAction(gui, caretPosition, textState);
            } else if (key == GLFW_KEY_END && pressed) {
                keyEndAction(gui, caretPosition, textState);
            } else if ((key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) && pressed) {
                keyEnterAction(gui, caretPosition);
            } else if (key == GLFW_KEY_BACKSPACE && pressed) {
                keyBackSpaceAction(gui, caretPosition, textState);
            } else if (key == GLFW_KEY_DELETE && pressed) {
                keyDeleteAction(gui, caretPosition, textState);
            } else if (key == GLFW_KEY_V && pressed && event.mods == GLFW_MOD_CONTROL) {
                pasteAction(gui, leguiContext, caretPosition, textState);
            } else if (key == GLFW_KEY_C && pressed && event.mods == GLFW_MOD_CONTROL) {
                copyAction(gui, leguiContext);
            } else if (key == GLFW_KEY_X && pressed && event.mods == GLFW_MOD_CONTROL) {
                cutAction(gui, leguiContext, textState);
            }
        }
    }

    private void cutAction(TextArea gui, LeguiContext leguiContext, TextState textState) {
        if (gui.isEditable()) {
            String s = gui.getSelection();
            if (s != null) {
                int start = gui.getStartSelectionIndex();
                int end = gui.getEndSelectionIndex();
                if (start > end) {
                    int swap = start;
                    start = end;
                    end = swap;
                }
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
                glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
            }
        }
    }

    private void copyAction(TextArea gui, LeguiContext leguiContext) {
        String s = gui.getSelection();
        if (s != null) glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
    }

    private void pasteAction(TextArea gui, LeguiContext leguiContext, int caretPosition, TextState textState) {
        if (gui.isEditable()) {
            String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
            if (s != null) {
                textState.insert(caretPosition, s);
                gui.setCaretPosition(caretPosition + s.length());
            }
        }
    }

    private void keyDeleteAction(TextArea gui, int caretPosition, TextState textState) {
        if (gui.isEditable() && caretPosition != textState.length()) {
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            if (start == end) {
                textState.deleteCharAt(caretPosition);
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
        }
    }

    private void keyBackSpaceAction(TextArea gui, int caretPosition, TextState textState) {
        if (gui.isEditable() && caretPosition != 0) {
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            if (start == end) {
                textState.deleteCharAt(caretPosition - 1);
                gui.setCaretPosition(caretPosition - 1);
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
        }
    }

    private void keyEnterAction(TextArea gui, int caretPosition) {
        if (gui.isEditable()) {
            gui.getTextState().insert(caretPosition, "\n");
            gui.setCaretPosition(caretPosition + 1);
        }
    }

    private void keyEndAction(TextArea gui, int caretPosition, TextState textState) {
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
        int cl = lines[some.lineIndex].length();
        int delta = cl - some.caretPositionInLine;
        int np = caretPosition + delta;
        gui.setCaretPosition(np);
    }

    private void keyHomeAction(TextArea gui, int caretPosition, TextState textState) {
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
        gui.setCaretPosition(caretPosition - some.caretPositionInLine);
    }

    private void keyDownAction(TextArea gui, int caretPosition, TextState textState) {
        if (caretPosition < textState.length()) {
            String text = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
            if (some.lineIndex < lines.length - 1) {
                int nl = lines[some.lineIndex + 1].length() + 1;
                int cl = lines[some.lineIndex].length() + 1;
                int np;
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
    }

    private void keyUpAction(TextArea gui, int caretPosition, TextState textState) {
        if (caretPosition > 0) {
            String text = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
            if (some.lineIndex > 0) {
                int nl = lines[some.lineIndex - 1].length() + 1;
                int np = caretPosition - (some.caretPositionInLine >= nl - 1 ? some.caretPositionInLine + 1 : nl);
                gui.setCaretPosition(np);
            } else {
                gui.setCaretPosition(0);
            }
        }
    }

    private void keyRightAction(TextArea gui, int caretPosition, TextState textState) {
        if (caretPosition < textState.length()) {
            gui.setCaretPosition(caretPosition + 1);
        }
    }

    private void keyLeftAction(TextArea gui, int caretPosition) {
        if (caretPosition > 0) {
            gui.setCaretPosition(caretPosition - 1);
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
        private int lineIndex;

        public LineData(int caretPositionInLine, int lineIndex) {
            this.caretPositionInLine = caretPositionInLine;
            this.lineIndex = lineIndex;
        }
    }
}
