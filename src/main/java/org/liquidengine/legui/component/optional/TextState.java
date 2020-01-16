package org.liquidengine.legui.component.optional;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;

/**
 * Used to hold text state of components.
 */
public class TextState implements Serializable {

    /**
     * Font name.
     */
    private String font = FontRegistry.DEFAULT;
    /**
     * Font size.
     */
    private float fontSize = 16;
    /**
     * Text data.
     */
    private String text = "";
    /**
     * Horizontal alignment. By default used {@link HorizontalAlign#CENTER}.
     */
    private HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
    /**
     * Vertical alignment.
     */
    private VerticalAlign verticalAlign = VerticalAlign.MIDDLE;
    /**
     * Text color.
     */
    private Vector4f textColor = ColorConstants.black();
    /**
     * Highlight color (used to highlight selection).
     */
    private Vector4f highlightColor = ColorConstants.blue();

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
     * Returns current font name.
     *
     * @return current font name.
     */
    public String getFont() {
        return font;
    }

    /**
     * Used to set new font.
     *
     * @param font new font.
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     * Returns font size.
     *
     * @return font size.
     */
    public float getFontSize() {
        return fontSize;
    }

    /**
     * Used to set new font size.
     *
     * @param fontSize new font size.
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Returns horizontal alignment.
     *
     * @return horizontal alignment.
     */
    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    /**
     * Used to set horizontal alignment.
     *
     * @param horizontalAlign horizontal alignment.
     */
    public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    /**
     * Returns vertical alignment.
     *
     * @return vertical alignment.
     */
    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    /**
     * Used to set vertical alignment.
     *
     * @param verticalAlign vertical alignment.
     */
    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    /**
     * Returns text color.
     *
     * @return text color.
     */
    public Vector4f getTextColor() {
        return textColor;
    }

    /**
     * Used to set text color.
     *
     * @param textColor text color.
     */
    public void setTextColor(Vector4f textColor) {
        this.textColor = textColor;
    }

    /**
     * Used to set text color.
     *
     * @param r red component.
     * @param g green component.
     * @param b blue component.
     * @param a alpha component.
     */
    public void setTextColor(float r, float g, float b, float a) {
        this.textColor = new Vector4f(r, g, b, a);
    }

    /**
     * Returns highlight color.
     *
     * @return highlight color
     */
    public Vector4f getHighlightColor() {
        return highlightColor;
    }

    /**
     * Used to set highlight color.
     *
     * @param highlightColor highlight color.
     */
    public void setHighlightColor(Vector4f highlightColor) {
        this.highlightColor = highlightColor;
    }

    /**
     * Used to set highlight color.
     *
     * @param r red component.
     * @param g green component.
     * @param b blue component.
     * @param a alpha component.
     */
    public void setHighlightColor(float r, float g, float b, float a) {
        this.highlightColor = new Vector4f(r, g, b, a);
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
     *
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
            .append("font", font)
            .append("fontSize", fontSize)
            .append("text", text)
            .append("horizontalAlign", horizontalAlign)
            .append("verticalAlign", verticalAlign)
            .append("textColor", textColor)
            .append("highlightColor", highlightColor)
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
            .append(fontSize, textState.fontSize)
            .append(font, textState.font)
            .append(text, textState.text)
            .append(horizontalAlign, textState.horizontalAlign)
            .append(verticalAlign, textState.verticalAlign)
            .append(textColor, textState.textColor)
            .append(highlightColor, textState.highlightColor)
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
            .append(font)
            .append(fontSize)
            .append(text)
            .append(horizontalAlign)
            .append(verticalAlign)
            .append(textColor)
            .append(highlightColor)
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
        this.font = state.font;
        this.fontSize = state.fontSize;
        this.setText(state.text);
        this.horizontalAlign = state.horizontalAlign;
        this.verticalAlign = state.verticalAlign;
        this.textColor.set(state.textColor);
        this.highlightColor.set(state.highlightColor);
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