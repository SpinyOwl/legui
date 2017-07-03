package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.LeguiCharEvent;
import org.liquidengine.legui.event.LeguiKeyEvent;
import org.liquidengine.legui.event.LeguiMouseClickEvent;
import org.liquidengine.legui.event.LeguiMouseDragEvent;
import org.liquidengine.legui.listener.*;
import org.liquidengine.legui.listener.LeguiKeyEventListener;
import org.liquidengine.legui.listener.LeguiMouseClickEventListener;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.theme.Themes;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;
import static org.liquidengine.legui.util.TextUtil.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * TextArea is multiline text component which allow to enter text.
 */
public class TextArea extends Controller implements TextComponent {
    /**
     * Used to hold text state of text area.
     */
    protected TextState textState;

    /**
     * Used to hold caret position of text area.
     */
    private int caretPosition;

    /**
     * Used to hold mouse caret position. Calculated and updated by renderer.
     */
    private int mouseCaretPosition;

    /**
     * Used to hold start selection index.
     */
    private int startSelectionIndex;

    /**
     * Used to hold end selection index.
     */
    private int endSelectionIndex;

    /**
     * If text area editable then text could be changed.
     */
    private boolean editable = true;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public TextArea() {
        initialize("");
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public TextArea(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("");
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public TextArea(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("");
    }

    /**
     * Default constructor with text.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     *
     * @param text text to set.
     */
    public TextArea(String text) {
        initialize(text);
    }

    /**
     * Constructor with text, position and size parameters.
     *
     * @param text   text to set.
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public TextArea(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Constructor with text, position and size parameters.
     *
     * @param text     text to set.
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public TextArea(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize text area.
     *
     * @param s text to set.
     */
    private void initialize(String s) {
        textState = new TextState(s);
        textState.getPadding().set(5, 10, 5, 10);

        getListenerMap().addListener(LeguiMouseDragEvent.class, new LeguiTextAreaDragEventListener());
        getListenerMap().addListener(LeguiMouseClickEvent.class, new LeguiTextAreaMouseClickEventListener());
        getListenerMap().addListener(LeguiKeyEvent.class, new LeguiTextAreaKeyEventListener());
        getListenerMap().addListener(LeguiCharEvent.class, new LeguiTextAreaCharEventListener());

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(TextArea.class).applyAll(this);
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }

    /**
     * Returns caret position.
     *
     * @return caret position.
     */
    public int getCaretPosition() {
        return caretPosition;
    }

    /**
     * Used to set caret position.
     *
     * @param caretPosition caret position to set.
     */
    public void setCaretPosition(int caretPosition) {
        int length = getTextState().getText().length();
        this.caretPosition = caretPosition < 0 ? 0 : caretPosition > length ? length : caretPosition;
    }

    /**
     * Returns true if text is editable.
     *
     * @return true if text is editable.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Used to set editable text or not.
     *
     * @param editable editable text or not.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Returns mouse caret position.
     *
     * @return mouse caret position.
     */
    public int getMouseCaretPosition() {
        return mouseCaretPosition;
    }

    /**
     * Used to set mouse caret position.
     *
     * @param mouseCaretPosition mouse caret position to set.
     */
    public void setMouseCaretPosition(int mouseCaretPosition) {
        this.mouseCaretPosition = mouseCaretPosition;
    }

    /**
     * Returns start selection index.
     *
     * @return start selection index.
     */
    public int getStartSelectionIndex() {
        return startSelectionIndex;
    }

    /**
     * Used to set start selection index.
     *
     * @param startSelectionIndex start selection index to set.
     */
    public void setStartSelectionIndex(int startSelectionIndex) {
        this.startSelectionIndex = startSelectionIndex;
    }

    /**
     * Returns end selection index.
     *
     * @return end selection index.
     */
    public int getEndSelectionIndex() {
        return endSelectionIndex;
    }

    /**
     * Used to set end selection index.
     *
     * @param endSelectionIndex end selection index to set.
     */
    public void setEndSelectionIndex(int endSelectionIndex) {
        this.endSelectionIndex = endSelectionIndex;
    }

    /**
     * Returns selected text.
     *
     * @return selected text.
     */
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

    /**
     * Mouse drag event listener for text area. Used to update selection indices.
     */
    public static class LeguiTextAreaDragEventListener implements LeguiMouseDragEventListener {

        /**
         * Used to handle {@link LeguiMouseDragEvent}.
         *
         * @param event event to handle.
         */
        @Override
        public void process(LeguiMouseDragEvent event) {
            TextArea textArea = (TextArea) event.getComponent();
            if (MOUSE_BUTTON_LEFT.isPressed()) {
                int mouseCaretPosition = textArea.getMouseCaretPosition();
                textArea.setCaretPosition(mouseCaretPosition);
                textArea.setEndSelectionIndex(mouseCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    /**
     * Mouse click event listener for text area. Used to update caret position.
     */
    public static class LeguiTextAreaMouseClickEventListener implements LeguiMouseClickEventListener {

        /**
         * Used to handle {@link LeguiMouseClickEvent}.
         *
         * @param event event to handle.
         */
        @Override
        public void process(LeguiMouseClickEvent event) {
            TextArea textArea = (TextArea) event.getComponent();
            if (event.getAction() == LeguiMouseClickEvent.MouseClickAction.PRESS) {
                int mouseCaretPosition = textArea.getMouseCaretPosition();
                textArea.setCaretPosition(mouseCaretPosition);
                textArea.setStartSelectionIndex(mouseCaretPosition);
                textArea.setEndSelectionIndex(mouseCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    /**
     * Key event listener. Used to provide some text operations by keyboard.
     */
    public static class LeguiTextAreaKeyEventListener implements LeguiKeyEventListener {

        /**
         * Used to handle {@link LeguiKeyEvent}.
         *
         * @param event event to handle.
         */
        @Override
        public void process(LeguiKeyEvent event) {
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

        /**
         * Used to cut some string from text area and put it to clipboard.
         *
         * @param gui          text area to work with.
         * @param leguiContext context.
         * @param textState    text state to work with.
         */
        private void cutAction(TextArea gui, LeguiContext leguiContext, TextState textState) {
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
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
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

    /**
     * Char event listener for text area. Used to fill text area with symbols entered via keyboard.
     */
    public static class LeguiTextAreaCharEventListener implements LeguiCharEventListener {

        /**
         * Used to handle {@link LeguiCharEvent}.
         *
         * @param event event to handle.
         */
        @Override
        public void process(LeguiCharEvent event) {
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
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }
}
