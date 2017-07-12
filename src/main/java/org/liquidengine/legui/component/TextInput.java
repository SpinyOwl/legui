package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.textinput.TextInputCharEventListener;
import org.liquidengine.legui.component.misc.listener.textinput.TextInputDragEventListener;
import org.liquidengine.legui.component.misc.listener.textinput.TextInputKeyEventListener;
import org.liquidengine.legui.component.misc.listener.textinput.TextInputMouseClickEventListener;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.theme.Themes;

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
        if (startSelectionIndex < 0 || endSelectionIndex < 0) {
            return null;
        }
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
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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

}
