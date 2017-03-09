package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.CharEventListener;
import org.liquidengine.legui.listener.KeyEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.system.context.Context;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;
import static org.liquidengine.legui.util.TextUtil.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class TextArea extends Controller implements TextComponent {
    protected TextState textState;

    protected int caretPosition;
    protected int mouseCaretPosition;

    protected int startSelectionIndex;
    protected int endSelectionIndex;

    protected boolean  editable       = true;
    private TextAreaCharEventListener       charEventListener;
    private TextAreaKeyEventListener        keyEventListener;
    private TextAreaMouseClickEventListener mouseClickEventListener;
    private TextAreaDragEventListener       dragEventListener;

    public TextArea() {
        initialize("");
    }

    public TextArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("");
    }

    public TextArea(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("");
    }

    private void initialize(String s) {
        textState = new TextState(s);
        textState.getPadding().set(5, 10, 5, 10);

        charEventListener = new TextAreaCharEventListener();
        keyEventListener = new TextAreaKeyEventListener();
        mouseClickEventListener = new TextAreaMouseClickEventListener();
        dragEventListener = new TextAreaDragEventListener();
        getListenerMap().addListener(MouseDragEvent.class, dragEventListener);
        getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        getListenerMap().addListener(KeyEvent.class, keyEventListener);
        getListenerMap().addListener(CharEvent.class, charEventListener);
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }

    public int getCaretPosition() {
        return caretPosition;
    }

    public void setCaretPosition(int caretPosition) {
        int length = getTextState().getText().length();
        this.caretPosition = caretPosition < 0 ? 0 : caretPosition > length ? length : caretPosition;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getMouseCaretPosition() {
        return mouseCaretPosition;
    }

    public void setMouseCaretPosition(int mouseCaretPosition) {
        this.mouseCaretPosition = mouseCaretPosition;
    }

    public int getStartSelectionIndex() {
        return startSelectionIndex;
    }

    public void setStartSelectionIndex(int startSelectionIndex) {
        this.startSelectionIndex = startSelectionIndex;
    }

    public int getEndSelectionIndex() {
        return endSelectionIndex;
    }

    public void setEndSelectionIndex(int endSelectionIndex) {
        this.endSelectionIndex = endSelectionIndex;
    }

    public String getSelection() {
        if (startSelectionIndex < 0 || endSelectionIndex < 0) return null;
        String selection;
        if (startSelectionIndex > endSelectionIndex) {
            selection = textState.substring(endSelectionIndex, startSelectionIndex);
        } else {
            selection = textState.substring(startSelectionIndex, endSelectionIndex);
        }
        return selection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TextArea textArea = (TextArea) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(caretPosition, textArea.caretPosition)
                .append(mouseCaretPosition, textArea.mouseCaretPosition)
                .append(editable, textArea.editable)
                .append(startSelectionIndex, textArea.startSelectionIndex)
                .append(endSelectionIndex, textArea.endSelectionIndex)
                .append(textState, textArea.textState)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(caretPosition)
                .append(mouseCaretPosition)
                .append(editable)
                .append(startSelectionIndex)
                .append(endSelectionIndex)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("editable", editable)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .toString();
    }

    public static class TextAreaDragEventListener implements MouseDragEventListener {

        @Override
        public void process(MouseDragEvent event) {
            TextArea textArea = (TextArea) event.getComponent();
            if (MOUSE_BUTTON_LEFT.isPressed()) {
                int mouseCaretPosition = textArea.getMouseCaretPosition();
                textArea.setCaretPosition(mouseCaretPosition);
                textArea.setEndSelectionIndex(mouseCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    public static class TextAreaMouseClickEventListener implements MouseClickEventListener {

        @Override
        public void process(MouseClickEvent event) {
            TextArea textArea = (TextArea) event.getComponent();
            if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
                int mouseCaretPosition = textArea.getMouseCaretPosition();
                textArea.setCaretPosition(mouseCaretPosition);
                textArea.setStartSelectionIndex(mouseCaretPosition);
                textArea.setEndSelectionIndex(mouseCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }

    public static class TextAreaKeyEventListener implements KeyEventListener {

        @Override
        public void process(KeyEvent event) {
            TextArea textArea = (TextArea) event.getComponent();
            if (textArea.isFocused() && textArea.isEditable()) {
                int       key           = event.getKey();
                int       caretPosition = textArea.getCaretPosition();
                boolean   pressed       = event.getAction() != GLFW_RELEASE;
                TextState textState     = textArea.getTextState();
                if (key == GLFW_KEY_LEFT && pressed) {
                    keyLeftAction(textArea, caretPosition, event.getMods());
                } else if (key == GLFW_KEY_RIGHT && pressed) {
                    keyRightAction(textArea, caretPosition, textState, event.getMods());
                } else if (key == GLFW_KEY_UP && pressed) {
                    keyUpAction(textArea, caretPosition, textState, event.getMods());
                } else if (key == GLFW_KEY_DOWN && pressed) {
                    keyDownAction(textArea, caretPosition, textState, event.getMods());
                } else if (key == GLFW_KEY_HOME && pressed) {
                    keyHomeAction(textArea, caretPosition, textState, event.getMods());
                } else if (key == GLFW_KEY_END && pressed) {
                    keyEndAction(textArea, caretPosition, textState, event.getMods());
                } else if ((key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) && pressed) {
                    keyEnterAction(textArea, caretPosition);
                } else if (key == GLFW_KEY_BACKSPACE && pressed) {
                    keyBackSpaceAction(textArea, caretPosition, textState, event.getMods());
                } else if (key == GLFW_KEY_DELETE && pressed) {
                    keyDeleteAction(textArea, caretPosition, textState, event.getMods());
                } else if (key == GLFW_KEY_V && pressed && event.getMods() == GLFW_MOD_CONTROL) {
                    pasteAction(textArea, event.getContext(), caretPosition, textState);
                } else if (key == GLFW_KEY_C && pressed && event.getMods() == GLFW_MOD_CONTROL) {
                    copyAction(textArea, event.getContext());
                } else if (key == GLFW_KEY_X && pressed && event.getMods() == GLFW_MOD_CONTROL) {
                    cutAction(textArea, event.getContext(), textState);
                }
            }
        }

        private void cutAction(TextArea gui, Context leguiContext, TextState textState) {
            if (gui.isEditable()) {
                String s = gui.getSelection();
                if (s != null) {
                    int start = gui.getStartSelectionIndex();
                    int end   = gui.getEndSelectionIndex();
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

        private void copyAction(TextArea gui, Context leguiContext) {
            String s = gui.getSelection();
            if (s != null) glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
        }

        private void pasteAction(TextArea gui, Context leguiContext, int caretPosition, TextState textState) {
            if (gui.isEditable()) {
                String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
                if (s != null) {
                    textState.insert(caretPosition, s);
                    gui.setCaretPosition(caretPosition + s.length());
                }
            }
        }

        private void keyDeleteAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            if (gui.isEditable()) {
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    gui.setEndSelectionIndex(findNextWord(textState.getText(), caretPosition));
                }
                int start = gui.getStartSelectionIndex();
                int end   = gui.getEndSelectionIndex();
                if (start > end) {
                    start = gui.getEndSelectionIndex();
                    end = gui.getStartSelectionIndex();
                }
                if (start == end && caretPosition != textState.length()) {
                    textState.deleteCharAt(caretPosition);
                } else {
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                }
            }
        }

        private void keyBackSpaceAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            if (gui.isEditable()) {
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    gui.setEndSelectionIndex(findPrevWord(textState.getText(), caretPosition));
                }
                int start = gui.getStartSelectionIndex();
                int end   = gui.getEndSelectionIndex();
                if (start > end) {
                    start = gui.getEndSelectionIndex();
                    end = gui.getStartSelectionIndex();
                }
                if (start == end && caretPosition != 0) {
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

        private void keyEndAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            String   text  = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some  = getStartLineIndexAndLineNumber(lines, caretPosition);
            int      cl    = lines[some.lineIndex].length();
            int      delta = cl - some.caretPositionInLine;

            int newCaretPosition = caretPosition + delta;
            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);
        }

        private void keyHomeAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            String   text  = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some  = getStartLineIndexAndLineNumber(lines, caretPosition);

            int newCaretPosition = caretPosition - some.caretPositionInLine;
            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);
        }

        private void keyDownAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            if (caretPosition < textState.length()) {
                String   text             = textState.getText();
                String[] lines            = text.split("\n", -1);
                LineData some             = getStartLineIndexAndLineNumber(lines, caretPosition);
                int      newCaretPosition = text.length();

                if (some.lineIndex < lines.length - 1) {
                    int nl = lines[some.lineIndex + 1].length() + 1;
                    int cl = lines[some.lineIndex].length() + 1;
                    if (some.caretPositionInLine >= nl - 1) {
                        newCaretPosition = caretPosition + nl + cl - 1 - some.caretPositionInLine;
                    } else {
                        newCaretPosition = caretPosition + cl;
                    }
                }
                gui.setEndSelectionIndex(newCaretPosition);
                if ((mods & GLFW_MOD_SHIFT) == 0) {
                    gui.setStartSelectionIndex(newCaretPosition);
                }
                gui.setCaretPosition(newCaretPosition);
            }
        }

        private void keyUpAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            if (caretPosition > 0) {
                String   text             = textState.getText();
                String[] lines            = text.split("\n", -1);
                LineData some             = getStartLineIndexAndLineNumber(lines, caretPosition);
                int      newCaretPosition = 0;
                if (some.lineIndex > 0) {
                    int nl = lines[some.lineIndex - 1].length() + 1;
                    newCaretPosition = caretPosition - (some.caretPositionInLine >= nl - 1 ? some.caretPositionInLine + 1 : nl);
                }

                gui.setEndSelectionIndex(newCaretPosition);
                if ((mods & GLFW_MOD_SHIFT) == 0) {
                    gui.setStartSelectionIndex(newCaretPosition);
                }
                gui.setCaretPosition(newCaretPosition);
            }
        }

        private void keyRightAction(TextArea gui, int caretPosition, TextState textState, int mods) {
            if (caretPosition < textState.length()) {
                int newCaretPosition = caretPosition + 1;
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
                }
                gui.setEndSelectionIndex(newCaretPosition);
                if ((mods & GLFW_MOD_SHIFT) == 0) {
                    gui.setStartSelectionIndex(newCaretPosition);
                }
                gui.setCaretPosition(newCaretPosition);
            }
        }

        private void keyLeftAction(TextArea gui, int caretPosition, int mods) {
            if (caretPosition > 0) {
                int newCaretPosition = caretPosition - 1;
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    newCaretPosition = findPrevWord(gui.getTextState().getText(), caretPosition);
                }
                gui.setEndSelectionIndex(newCaretPosition);
                if ((mods & GLFW_MOD_SHIFT) == 0) {
                    gui.setStartSelectionIndex(newCaretPosition);
                }
                gui.setCaretPosition(newCaretPosition);
            }
        }

        private LineData getStartLineIndexAndLineNumber(String[] lines, int caretPosition) {
            int caretLine   = 0;
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

        @Override
        public boolean equals(Object obj) {
            return true;
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

    public static class TextAreaCharEventListener implements CharEventListener {

        @Override
        public void process(CharEvent event) {
            TextArea textArea = (TextArea) event.getComponent();
            if (textArea.isFocused() && textArea.isEditable() && !MOUSE_BUTTON_LEFT.isPressed()) {
                String    str       = cpToStr(event.getCodepoint());
                TextState textState = textArea.getTextState();
                int       start     = textArea.getStartSelectionIndex();
                int       end       = textArea.getEndSelectionIndex();
                if (start > end) {
                    start = textArea.getEndSelectionIndex();
                    end = textArea.getStartSelectionIndex();
                }
                if (start != end) {
                    textState.delete(start, end);
                    textArea.setCaretPosition(start);
                    textArea.setStartSelectionIndex(start);
                    textArea.setEndSelectionIndex(start);
                }
                int caretPosition = textArea.getCaretPosition();
                textState.insert(caretPosition, str);
                int newCaretPosition = caretPosition + str.length();
                textArea.setCaretPosition(newCaretPosition);
                textArea.setEndSelectionIndex(newCaretPosition);
                textArea.setStartSelectionIndex(newCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
