package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaCharEventListener;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaDragEventListener;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaKeyEventListener;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaMouseClickEventListener;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.theme.Themes;

/**
 * TextArea is multiline text component which allow to enter text.
 */
public class TextArea extends Controller implements TextComponent {

    /**
     * Used to hold text state of text area.
     */
    protected TextState textState;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public TextArea() {
        initialize("");
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
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
     * @param size size of component.
     */
    public TextArea(Vector2f position, Vector2f size) {
        super(position, size);
        initialize("");
    }

    /**
     * Default constructor with text. <p> Also if you want to make it easy to use with Json marshaller/unmarshaller component should contain empty constructor.
     *
     * @param text text to set.
     */
    public TextArea(String text) {
        initialize(text);
    }

    /**
     * Constructor with text, position and size parameters.
     *
     * @param text text to set.
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     */
    public TextArea(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Constructor with text, position and size parameters.
     *
     * @param text text to set.
     * @param position position position in parent component.
     * @param size size of component.
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

        getListenerMap().addListener(MouseDragEvent.class, new TextAreaDragEventListener());
        getListenerMap().addListener(MouseClickEvent.class, new TextAreaMouseClickEventListener());
        getListenerMap().addListener(KeyEvent.class, new TextAreaKeyEventListener());
        getListenerMap().addListener(CharEvent.class, new TextAreaCharEventListener());

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
        return textState.getCaretPosition();
    }

    /**
     * Used to set caret position.
     *
     * @param caretPosition caret position to set.
     */
    public void setCaretPosition(int caretPosition) {
        textState.setCaretPosition(caretPosition);
    }

    /**
     * Returns true if text is editable.
     *
     * @return true if text is editable.
     */
    public boolean isEditable() {
        return textState.isEditable();
    }

    /**
     * Used to set editable text or not.
     *
     * @param editable editable text or not.
     */
    public void setEditable(boolean editable) {
        textState.setEditable(editable);
    }

    /**
     * Returns mouse caret position.
     *
     * @return mouse caret position.
     */
    public int getMouseCaretPosition() {
        return textState.getMouseCaretPosition();
    }

    /**
     * Used to set mouse caret position.
     *
     * @param mouseCaretPosition mouse caret position to set.
     */
    public void setMouseCaretPosition(int mouseCaretPosition) {
        textState.setMouseCaretPosition(mouseCaretPosition);
    }

    /**
     * Returns start selection index.
     *
     * @return start selection index.
     */
    public int getStartSelectionIndex() {
        return textState.getStartSelectionIndex();
    }

    /**
     * Used to set start selection index.
     *
     * @param startSelectionIndex start selection index to set.
     */
    public void setStartSelectionIndex(int startSelectionIndex) {
        textState.setStartSelectionIndex(startSelectionIndex);
    }

    /**
     * Returns end selection index.
     *
     * @return end selection index.
     */
    public int getEndSelectionIndex() {
        return textState.getEndSelectionIndex();
    }

    /**
     * Used to set end selection index.
     *
     * @param endSelectionIndex end selection index to set.
     */
    public void setEndSelectionIndex(int endSelectionIndex) {
        textState.setEndSelectionIndex(endSelectionIndex);
    }

    /**
     * Returns selected text.
     *
     * @return selected text.
     */
    public String getSelection() {
        return textState.getSelection();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextArea textArea = (TextArea) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(textState, textArea.textState)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(textState)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("textState", textState)
            .toString();
    }

}
