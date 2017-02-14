package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
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
public class TextInput extends Controller {
    protected TextState textState;

    protected int caretPosition;
    protected int mouseCaretPosition;

    protected int startSelectionIndex;
    protected int endSelectionIndex;

    protected Vector4f selectionColor = ColorConstants.lightBlue();
    protected boolean  editable       = true;
    private TextInputKeyEventListener        keyEventListener;
    private TextInputMouseClickEventListener mouseClickEventListener;
    private TextInputDragEventListener       dragEventListener;
    private TextInputCharEventListener       charEventListener;

    public TextInput() {
        initialize("TextInput");
    }

    public TextInput(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize("TextInput");
    }

    public TextInput(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    public TextInput(String text) {
        initialize(text);
    }

    public int getMouseCaretPosition() {
        return mouseCaretPosition;
    }

    public void setMouseCaretPosition(int mouseCaretPosition) {
        this.mouseCaretPosition = mouseCaretPosition;
    }

    private void initialize(String text) {
        textState = new TextState(text);
        textState.getPadding().set(5, 1, 5, 1);

        keyEventListener = new TextInputKeyEventListener(this);
        mouseClickEventListener = new TextInputMouseClickEventListener(this);
        dragEventListener = new TextInputDragEventListener(this);
        charEventListener = new TextInputCharEventListener(this);

        getListenerMap().addListener(KeyEvent.class, keyEventListener);
        getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        getListenerMap().addListener(MouseDragEvent.class, dragEventListener);
        getListenerMap().addListener(CharEvent.class, charEventListener);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getCaretPosition() {
        return caretPosition;
    }

    public void setCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
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

    public TextState getTextState() {
        return textState;
    }

    public Vector4f getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Vector4f selectionColor) {
        this.selectionColor = selectionColor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("textState", textState)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .append("selectionColor", selectionColor)
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
                .append(selectionColor, input.selectionColor)
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
                .append(selectionColor)
                .append(editable)
                .toHashCode();
    }

    public static class TextInputKeyEventListener implements KeyEventListener {

        private final TextInput gui;

        public TextInputKeyEventListener(TextInput gui) {
            this.gui = gui;
        }

        @Override
        public void process(KeyEvent event) {
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
                    System.out.println(start + " " + end);
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
    }

    public static class TextInputMouseClickEventListener implements MouseClickEventListener {
        private final TextInput gui;

        public TextInputMouseClickEventListener(TextInput gui) {
            this.gui = gui;
        }

        @Override
        public void process(MouseClickEvent event) {
            int mouseCaretPosition = gui.getMouseCaretPosition();
            if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
                gui.setCaretPosition(mouseCaretPosition);
                gui.setStartSelectionIndex(mouseCaretPosition);
                gui.setEndSelectionIndex(mouseCaretPosition);
            }
        }
    }

    public static class TextInputDragEventListener implements MouseDragEventListener {
        private final TextInput textInput;

        public TextInputDragEventListener(TextInput textInput) {
            this.textInput = textInput;
        }

        @Override
        public void process(MouseDragEvent event) {
            if (MOUSE_BUTTON_LEFT.isPressed()) {
                int mouseCaretPosition = textInput.getMouseCaretPosition();
                textInput.setCaretPosition(mouseCaretPosition);
                textInput.setEndSelectionIndex(mouseCaretPosition);
            }
        }
    }

    public static class TextInputCharEventListener implements CharEventListener {

        private final TextInput textInput;

        public TextInputCharEventListener(TextInput textInput) {
            this.textInput = textInput;
        }

        @Override
        public void process(CharEvent event) {
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
    }
}
