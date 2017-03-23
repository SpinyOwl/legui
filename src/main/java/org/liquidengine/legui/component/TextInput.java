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
import org.liquidengine.legui.theme.Themes;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;
import static org.liquidengine.legui.util.TextUtil.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Text input is a single line text component which can be used to enter text.
 */
public class TextInput extends Controller implements TextComponent {

    /**
     * Used to store text state of text input.
     */
    protected TextState textState;

    /**
     * Used to store caret position in text.
     */
    private int caretPosition;

    /**
     * Used to store caret position calculated on mouse position base.
     * Updated by renderers.
     */
    private int mouseCaretPosition;

    /**
     * Used to store start selection index.
     */
    private int startSelectionIndex;

    /**
     * Used to store end selection index.
     */
    private int endSelectionIndex;

    /**
     * Used to store text input editable state. If true then text could be updated by user input.
     */
    private boolean editable = true;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public TextInput() {
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
    public TextInput(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("");
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public TextInput(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("");
    }

    /**
     * Default constructor with text to set.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     *
     * @param text text to set.
     */
    public TextInput(String text) {
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
    public TextInput(String text, float x, float y, float width, float height) {
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
    public TextInput(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize text input.
     *
     * @param text text to set.
     */
    private void initialize(String text) {
        textState = new TextState(text);
        textState.getPadding().set(5, 1, 5, 1);

        getListenerMap().addListener(KeyEvent.class, new TextInputKeyEventListener());
        getListenerMap().addListener(MouseClickEvent.class, new TextInputMouseClickEventListener());
        getListenerMap().addListener(MouseDragEvent.class, new TextInputDragEventListener());
        getListenerMap().addListener(CharEvent.class, new TextInputCharEventListener());

        Themes.getDefaultTheme().getThemeManager().getComponentTheme(TextInput.class).applyAll(this);
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
        this.caretPosition = caretPosition;
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

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .append("editable", editable)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TextInput input = (TextInput) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(caretPosition, input.caretPosition)
                .append(mouseCaretPosition, input.mouseCaretPosition)
                .append(startSelectionIndex, input.startSelectionIndex)
                .append(endSelectionIndex, input.endSelectionIndex)
                .append(editable, input.editable)
                .append(textState, input.textState)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(caretPosition)
                .append(mouseCaretPosition)
                .append(startSelectionIndex)
                .append(endSelectionIndex)
                .append(editable)
                .toHashCode();
    }

    /**
     * Key event listener. Used to provide some text operations by keyboard.
     */
    public static class TextInputKeyEventListener implements KeyEventListener {

        /**
         * Used to process {@link KeyEvent}.
         *
         * @param event event to process.
         */
        @Override
        public void process(KeyEvent event) {
            TextInput gui           = (TextInput) event.getComponent();
            int       key           = event.getKey();
            int       caretPosition = gui.getCaretPosition();
            boolean   pressed       = event.getAction() != GLFW_RELEASE;
            TextState textState     = gui.getTextState();
            if (key == GLFW_KEY_LEFT && pressed) {
                keyLeftAction(gui, caretPosition, event.getMods());
            } else if (key == GLFW_KEY_RIGHT && pressed) {
                keyRightAction(gui, caretPosition, textState, event.getMods());
            } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && pressed) {
                keyUpAndHomeAction(gui, event.getMods());
            } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && pressed) {
                keyDownAndEndAction(gui, event.getMods());
            } else if (key == GLFW_KEY_BACKSPACE && pressed) {
                keyBackSpaceAction(gui, caretPosition, textState, event.getMods());
            } else if (key == GLFW_KEY_DELETE && pressed) {
                keyDeleteAction(gui, caretPosition, textState, event.getMods());
            } else if (key == GLFW_KEY_V && pressed && event.getMods() == GLFW_MOD_CONTROL) {
                pasteAction(gui, event.getContext(), caretPosition, textState);
            } else if (key == GLFW_KEY_C && pressed && event.getMods() == GLFW_MOD_CONTROL) {
                copyAction(gui, event.getContext());
            } else if (key == GLFW_KEY_X && pressed && event.getMods() == GLFW_MOD_CONTROL) {
                cutAction(gui, event.getContext());
            }
        }

        private void cutAction(TextInput gui, Context leguiContext) {
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
                    gui.getTextState().delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                    glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
                }
            } else {
                copyAction(gui, leguiContext);
            }
        }

        private void copyAction(TextInput gui, Context leguiContext) {
            String s = gui.getSelection();
            if (s != null) glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
        }

        private void pasteAction(TextInput gui, Context leguiContext, int caretPosition, TextState textState) {
            if (gui.isEditable()) {
                String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
                if (s != null) {
                    textState.insert(caretPosition, s);
                    gui.setCaretPosition(caretPosition + s.length());
                }
            }
        }

        private void keyDeleteAction(TextInput gui, int caretPosition, TextState textState, int mods) {
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
                } else if (start != end) {
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                }
            }
        }

        private void keyBackSpaceAction(TextInput gui, int caretPosition, TextState textState, int mods) {
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
                } else if (start != end) {
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                }

            }
        }

        private void keyDownAndEndAction(TextInput gui, int mods) {
            int newCaretPosition = gui.getTextState().length();
            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);

        }

        private void keyUpAndHomeAction(TextInput gui, int mods) {
            int newCaretPosition = 0;
            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);
        }

        private void keyRightAction(TextInput gui, int caretPosition, TextState textState, int mods) {
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

        private void keyLeftAction(TextInput gui, int caretPosition, int mods) {
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

        @Override
        public boolean equals(Object obj) {
            return obj == this || obj instanceof TextInputKeyEventListener;
        }
    }

    /**
     * Mouse click event listener for text input. Used to update caret position.
     */
    public static class TextInputMouseClickEventListener implements MouseClickEventListener {

        /**
         * Used to process {@link MouseClickEvent}.
         *
         * @param event event to process.
         */
        @Override
        public void process(MouseClickEvent event) {
            TextInput gui                = (TextInput) event.getComponent();
            int       mouseCaretPosition = gui.getMouseCaretPosition();
            if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
                gui.setCaretPosition(mouseCaretPosition);
                gui.setStartSelectionIndex(mouseCaretPosition);
                gui.setEndSelectionIndex(mouseCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    /**
     * Mouse drag event listener for text input. Used to update selection indices.
     */
    public static class TextInputDragEventListener implements MouseDragEventListener {

        @Override
        public void process(MouseDragEvent event) {
            TextInput textInput = (TextInput) event.getComponent();
            if (MOUSE_BUTTON_LEFT.isPressed()) {
                int mouseCaretPosition = textInput.getMouseCaretPosition();
                textInput.setCaretPosition(mouseCaretPosition);
                textInput.setEndSelectionIndex(mouseCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    /**
     * Char event listener for text input. Used to fill text area with symbols entered via keyboard.
     */
    public static class TextInputCharEventListener implements CharEventListener {

        /**
         * Used to process {@link CharEvent}.
         *
         * @param event event to process.
         */
        @Override
        public void process(CharEvent event) {
            TextInput textInput = (TextInput) event.getComponent();
            if (textInput.isFocused() && textInput.isEditable() && !MOUSE_BUTTON_LEFT.isPressed()) {
                String    str       = cpToStr(event.getCodepoint());
                TextState textState = textInput.getTextState();
                int       start     = textInput.getStartSelectionIndex();
                int       end       = textInput.getEndSelectionIndex();
                if (start > end) {
                    start = textInput.getEndSelectionIndex();
                    end = textInput.getStartSelectionIndex();
                }
                if (start != end) {
                    textState.delete(start, end);
                    textInput.setCaretPosition(start);
                    textInput.setStartSelectionIndex(start);
                    textInput.setEndSelectionIndex(start);
                }
                int caretPosition = textInput.getCaretPosition();
                textState.insert(caretPosition, str);
                int newCaretPosition = caretPosition + str.length();
                textInput.setCaretPosition(newCaretPosition);
                textInput.setEndSelectionIndex(newCaretPosition);
                textInput.setStartSelectionIndex(newCaretPosition);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }
}
