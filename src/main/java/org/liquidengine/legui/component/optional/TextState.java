package org.liquidengine.legui.component.optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Used to hold text state of components.
 */
public class TextState implements Serializable {
    /**
     * Text data.
     */
    private String text = "";

    /**
     * Used to store caret position in text.
     */
    private int caretPosition;

    /**
     * Used to store caret position calculated on mouse position base. Updated by renderers.
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
     * Default constructor.
     */
    public TextState() {
    }

    /**
     * Used to create TextState with predefined text.
     *
     * @param text text to set.
     */
    public TextState(String text) {
        setText(text);
    }

    /**
     * Returns text.
     *
     * @return text.
     */
    public String getText() {
        return text;
    }

    /**
     * Used to set new text.
     *
     * @param text new text.
     */
    public void setText(String text) {
        if (text != null)
            this.text = text;
        else {
            this.text = "";
        }
        this.caretPosition = this.startSelectionIndex = this.endSelectionIndex = 0;

    }

    /**
     * Returns text length.
     *
     * @return text length.
     * @see StringBuffer#length()
     */
    public int length() {
        return text.length();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("text", text)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .append("editable", editable)
                .toString();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextState textState = (TextState) o;

        return new EqualsBuilder()
                .append(text, textState.text)
                .append(caretPosition, this.caretPosition)
                .append(mouseCaretPosition, this.mouseCaretPosition)
                .append(startSelectionIndex, this.startSelectionIndex)
                .append(endSelectionIndex, this.endSelectionIndex)
                .append(editable, this.editable)
                .isEquals();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(text)
                .append(caretPosition)
                .append(mouseCaretPosition)
                .append(startSelectionIndex)
                .append(endSelectionIndex)
                .append(editable)
                .toHashCode();
    }

    /**
     * Used to copy provided text state to current state.
     *
     * @param state state to copy.
     */
    public void copy(TextState state) {
        this.setText(state.getText());
        this.setText(state.text);
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
        int length = text.length();
        this.caretPosition = caretPosition < 0 ? 0 : caretPosition > length ? length : caretPosition;
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
            selection = text.substring(endSelectionIndex, startSelectionIndex);
        } else {
            selection = text.substring(startSelectionIndex, endSelectionIndex);
        }
        return selection;
    }
}