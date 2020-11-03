package org.liquidengine.legui.component.optional;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

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

    private float textWidth;
    private float textHeight;

    private Float caretX;
    private Float caretY;

    /**
     * Should be defined to call some extra functionality after {@link #setText(String)}.  Accepts old value as first arg, and new value as the second arg.
     */
    private BiConsumer<String, String> textSetCallback;

    private Predicate<String> validator;


    /**
     * Default constructor.
     */
    public TextState() {
        this("", null);
    }

    /**
     * Used to create TextState with predefined text.
     *
     * @param text text to set.
     */
    public TextState(String text) {
        this(text, null);
    }

    /**
     * Used to create TextState with predefined text.
     *
     * @param textSetCallback callback to call some extra functionality after {@link #setText(String)}. Accepts old value as first arg, and new value as the
     *                        second arg.
     */
    public TextState(BiConsumer<String, String> textSetCallback) {
        this("", textSetCallback);
    }

    /**
     * Used to create TextState with predefined text.
     *
     * @param text            text to set.
     * @param textSetCallback callback to call some extra functionality after {@link #setText(String)}. Accepts old value as first arg, and new value as the
     *                        second arg.
     */
    public TextState(String text, BiConsumer<String, String> textSetCallback) {
        this.textSetCallback = textSetCallback;
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
        if (validator != null && !validator.test(text)) {
            return;
        }

        String oldValue = this.text;
        String newValue = text;

        if (text != null) {
            this.text = text;
        } else {
            this.text = "";
        }

        this.caretPosition = this.startSelectionIndex = this.endSelectionIndex = 0;

        if (this.textSetCallback != null) {
            textSetCallback.accept(oldValue, newValue);
        }
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

    public float getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(float textWidth) {
        this.textWidth = textWidth;
    }

    public float getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(float textHeight) {
        this.textHeight = textHeight;
    }

    /**
     * Absolute caret X position.
     *
     * @return absolute caret X position.
     */
    public Float getCaretX() {
        return caretX;
    }

    /**
     * Used by renderer to set absolute caret x position.
     *
     * @param caretX caret x position.
     */
    public void setCaretX(Float caretX) {
        this.caretX = caretX;
    }

    /**
     * Absolute caret Y position.
     *
     * @return absolute caret Y position.
     */
    public Float getCaretY() {
        return caretY;
    }

    /**
     * Used by renderer to set absolute caret y position.
     *
     * @param caretY caret x position.
     */
    public void setCaretY(Float caretY) {
        this.caretY = caretY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextState textState = (TextState) o;
        return caretPosition == textState.caretPosition &&
                mouseCaretPosition == textState.mouseCaretPosition &&
                startSelectionIndex == textState.startSelectionIndex &&
                endSelectionIndex == textState.endSelectionIndex &&
                editable == textState.editable &&
                Objects.equals(text, textState.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, caretPosition, mouseCaretPosition, startSelectionIndex, endSelectionIndex, editable);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .append("caretPosition", caretPosition)
                .append("mouseCaretPosition", mouseCaretPosition)
                .append("startSelectionIndex", startSelectionIndex)
                .append("endSelectionIndex", endSelectionIndex)
                .append("editable", editable)
                .toString();
    }

    public Predicate<String> getValidator() {
        return validator;
    }

    public void setValidator(Predicate<String> validator) {
        this.validator = validator;
    }

    public static final Predicate<String> INTEGER_VALIDATOR = s -> s.matches("-?\\d+");
    public static final Predicate<String> NUMBER_VALIDATOR = s -> s.matches("-?\\d+(\\.\\d+)?");
}