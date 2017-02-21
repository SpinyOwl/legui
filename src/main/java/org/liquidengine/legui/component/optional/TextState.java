package org.liquidengine.legui.component.optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.theme.Theme;

import java.io.Serializable;

/**
 * Used to hold text state of components.
 */
public class TextState implements Serializable {
    /**
     * Font name.
     */
    private String          font            = Theme.DEFAULT_THEME.font();
    /**
     * Font size.
     */
    private float           fontSize        = Theme.DEFAULT_THEME.fontSize();
    /**
     * Text data.
     */
    private StringBuffer    text            = new StringBuffer();
    /**
     * Horizontal alignment.
     */
    private HorizontalAlign horizontalAlign = Theme.DEFAULT_THEME.horizontalAlign();
    /**
     * Vertical alignment
     */
    private VerticalAlign   verticalAlign   = Theme.DEFAULT_THEME.verticalAlign();
    /**
     * Text color
     */
    private Vector4f        textColor       = Theme.DEFAULT_THEME.fontColor();
    /**
     * Highlight color (used to highlight selection)
     */
    private Vector4f        highlightColor  = Theme.DEFAULT_THEME.highlightColor();
    /**
     * Padding in component.
     */
    private Vector4f        padding         = Theme.DEFAULT_THEME.textPadding();

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
     * Returns padding.
     * <ul>
     * <li>x - left</li>
     * <li>y - top</li>
     * <li>z - right</li>
     * <li>w - bottom</li>
     * </ul>
     *
     * @return padding
     */
    public Vector4f getPadding() {
        return padding;
    }

    /**
     * Used to set padding.
     * <ul>
     * <li>x - left</li>
     * <li>y - top</li>
     * <li>z - right</li>
     * <li>w - bottom</li>
     * </ul>
     *
     * @param padding text padding.
     */
    public void setPadding(Vector4f padding) {
        this.padding = padding;
    }

    /**
     * Used to set padding.
     *
     * @param left   left padding
     * @param top    top padding
     * @param right  right padding
     * @param bottom bottom padding
     */
    public void setPadding(float left, float top, float right, float bottom) {
        this.padding = new Vector4f(left, top, right, bottom);
    }

    /**
     * Used to append character to text.
     *
     * @param c character to append.
     */
    public void append(Character c) {
        text.append(c);
    }

    /**
     * Returns text.
     *
     * @return text.
     */
    public String getText() {
        return text.toString();
    }

    /**
     * Used to set new text.
     *
     * @param text new text.
     */
    public void setText(String text) {
        this.text.setLength(0);
        this.text.append(text);
    }

    /**
     * Returns text length.
     *
     * @return text length.
     */
    public int length() {
        return text.length();
    }

    /**
     * Returns text buffer capacity.
     *
     * @return text buffer capacity.
     */
    public int capacity() {
        return text.capacity();
    }

    /**
     * Ensures that the capacity is at least equal to the specified minimum.
     * If the current capacity is less than the argument, then a new internal
     * array is allocated with greater capacity. The new capacity is the larger of:
     * <ul>
     * <li>The minimumCapacity argument.</li>
     * <li>Twice the old capacity, plus 2.</li>
     * </ul>
     * If the minimumCapacity argument is nonpositive, this method takes no action and simply returns.
     * Note that subsequent operations on this object can reduce the actual capacity below that requested here.
     *
     * @param minimumCapacity - the minimum desired capacity.
     */
    public void ensureCapacity(int minimumCapacity) {
        text.ensureCapacity(minimumCapacity);
    }

    /**
     * Attempts to reduce storage used for the character sequence.
     * If the buffer is larger than necessary to hold its current sequence of
     * characters, then it may be resized to become more space efficient.
     * Calling this method may, but is not required to, affect the value
     * returned by a subsequent call to the {@link #capacity()} method.
     */
    public void trimToSize() {
        text.trimToSize();
    }

    /**
     * Sets the length of the character sequence.
     * The sequence is changed to a new character sequence
     * whose length is specified by the argument. For every nonnegative
     * index <i>k</i> less than {@code newLength}, the character at
     * index <i>k</i> in the new character sequence is the same as the
     * character at index <i>k</i> in the old sequence if <i>k</i> is less
     * than the length of the old character sequence; otherwise, it is the
     * null character {@code '\u005Cu0000'}.
     * <p>
     * In other words, if the {@code newLength} argument is less than
     * the current length, the length is changed to the specified length.
     * <p>
     * If the {@code newLength} argument is greater than or equal
     * to the current length, sufficient null characters
     * ({@code '\u005Cu0000'}) are appended so that
     * length becomes the {@code newLength} argument.
     * <p>
     * The {@code newLength} argument must be greater than or equal
     * to {@code 0}.
     *
     * @param newLength the new length
     * @throws IndexOutOfBoundsException if the
     *                                   {@code newLength} argument is negative.
     */
    public void setLength(int newLength) {
        text.setLength(newLength);
    }

    /**
     * Returns the <code>char</code> value at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing.
     * <p>If the <code>char</code> value specified by the index is a
     * <a href="{@docRoot}/java/lang/Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * @param index the index of the <code>char</code> value to be returned
     * @return the specified <code>char</code> value
     * @throws IndexOutOfBoundsException if the <tt>index</tt> argument is negative or not less than
     *                                   <tt>length()</tt>
     */
    public char charAt(int index) {
        return text.charAt(index);
    }

    /**
     * Returns the character (Unicode code point) at the specified
     * index. The index refers to {@code char} values
     * (Unicode code units) and ranges from {@code 0} to
     * {@link #length()}{@code  - 1}.
     * <p> If the {@code char} value specified at the given index
     * is in the high-surrogate range, the following index is less
     * than the length of this sequence, and the
     * {@code char} value at the following index is in the
     * low-surrogate range, then the supplementary code point
     * corresponding to this surrogate pair is returned. Otherwise,
     * the {@code char} value at the given index is returned.
     *
     * @param index the index to the {@code char} values
     * @return the code point value of the character at the
     * {@code index}
     * @throws IndexOutOfBoundsException if the {@code index}
     *                                   argument is negative or not less than the length of this
     *                                   sequence.
     */
    public int codePointAt(int index) {
        return text.codePointAt(index);
    }

    /**
     * Returns the character (Unicode code point) before the specified
     * index. The index refers to {@code char} values
     * (Unicode code units) and ranges from {@code 1} to {@link
     * #length()}.
     * <p> If the {@code char} value at {@code (index - 1)}
     * is in the low-surrogate range, {@code (index - 2)} is not
     * negative, and the {@code char} value at {@code (index -
     * 2)} is in the high-surrogate range, then the
     * supplementary code point value of the surrogate pair is
     * returned. If the {@code char} value at {@code index -
     * 1} is an unpaired low-surrogate or a high-surrogate, the
     * surrogate value is returned.
     *
     * @param index the index following the code point that should be returned
     * @return the Unicode code point value before the given index.
     * @throws IndexOutOfBoundsException if the {@code index}
     *                                   argument is less than 1 or greater than the length
     *                                   of this sequence.
     */
    public int codePointBefore(int index) {
        return text.codePointBefore(index);
    }


    /**
     * Returns the number of Unicode code points in the specified text
     * range of this sequence. The text range begins at the specified
     * {@code beginIndex} and extends to the {@code char} at
     * index {@code endIndex - 1}. Thus the length (in
     * {@code char}s) of the text range is
     * {@code endIndex-beginIndex}. Unpaired surrogates within
     * this sequence count as one code point each.
     *
     * @param beginIndex the index to the first {@code char} of
     *                   the text range.
     * @param endIndex   the index after the last {@code char} of
     *                   the text range.
     * @return the number of Unicode code points in the specified text
     * range
     * @throws IndexOutOfBoundsException if the
     *                                   {@code beginIndex} is negative, or {@code endIndex}
     *                                   is larger than the length of this sequence, or
     *                                   {@code beginIndex} is larger than {@code endIndex}.
     */
    public int codePointCount(int beginIndex, int endIndex) {
        return text.codePointCount(beginIndex, endIndex);
    }

    /**
     * {@inheritDoc}
     *
     * @param index           {@inheritDoc}
     * @param codePointOffset {@inheritDoc}
     * @return {@inheritDoc}
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        return text.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        text.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public void setCharAt(int index, char ch) {
        text.setCharAt(index, ch);
    }

    public StringBuffer append(Object obj) {
        return text.append(obj);
    }

    public StringBuffer append(String str) {
        return text.append(str);
    }

    public StringBuffer append(StringBuffer sb) {
        return text.append(sb);
    }

    public StringBuffer append(CharSequence s) {
        return text.append(s);
    }

    public StringBuffer append(CharSequence s, int start, int end) {
        return text.append(s, start, end);
    }

    public StringBuffer append(char[] str) {
        return text.append(str);
    }

    public StringBuffer append(char[] str, int offset, int len) {
        return text.append(str, offset, len);
    }

    public StringBuffer append(boolean b) {
        return text.append(b);
    }

    public StringBuffer append(char c) {
        return text.append(c);
    }

    public StringBuffer append(int i) {
        return text.append(i);
    }

    public StringBuffer appendCodePoint(int codePoint) {
        return text.appendCodePoint(codePoint);
    }

    public StringBuffer append(long lng) {
        return text.append(lng);
    }

    public StringBuffer append(float f) {
        return text.append(f);
    }

    public StringBuffer append(double d) {
        return text.append(d);
    }

    public StringBuffer delete(int start, int end) {
        return text.delete(start, end);
    }

    public StringBuffer deleteCharAt(int index) {
        return text.deleteCharAt(index);
    }

    public StringBuffer replace(int start, int end, String str) {
        return text.replace(start, end, str);
    }

    public String substring(int start) {
        return text.substring(start);
    }

    public CharSequence subSequence(int start, int end) {
        return text.subSequence(start, end);
    }

    public String substring(int start, int end) {
        return text.substring(start, end);
    }

    public StringBuffer insert(int index, char[] str, int offset, int len) {
        return text.insert(index, str, getOffset(offset), len);
    }

    public StringBuffer insert(int offset, Object obj) {
        return text.insert(getOffset(offset), obj);
    }

    public StringBuffer insert(int offset, String str) {
        return text.insert(getOffset(offset), str);
    }

    public StringBuffer insert(int offset, char[] str) {
        return text.insert(getOffset(offset), str);
    }

    public StringBuffer insert(int dstOffset, CharSequence s) {
        return text.insert(getOffset(dstOffset), s);
    }

    public StringBuffer insert(int dstOffset, CharSequence s, int start, int end) {
        return text.insert(getOffset(dstOffset), s, start, end);
    }

    public StringBuffer insert(int offset, boolean b) {
        return text.insert(getOffset(offset), b);
    }

    public StringBuffer insert(int offset, char c) {
        return text.insert(getOffset(offset), c);
    }

    public StringBuffer insert(int offset, int i) {
        return text.insert(getOffset(offset), i);
    }

    public StringBuffer insert(int offset, long l) {
        return text.insert(getOffset(offset), l);
    }

    public StringBuffer insert(int offset, float f) {
        return text.insert(getOffset(offset), f);
    }

    public StringBuffer insert(int offset, double d) {
        return text.insert(getOffset(offset), d);
    }

    private int getOffset(int offset) {
        return offset > text.length() ? text.length() : offset;
    }

    public int indexOf(String str) {
        return text.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return text.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return text.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return text.lastIndexOf(str, fromIndex);
    }

    public StringBuffer reverse() {
        return text.reverse();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("font", font)
                .append("fontSize", fontSize)
                .append("text", text)
                .append("horizontalAlign", horizontalAlign)
                .append("verticalAlign", verticalAlign)
                .append("textColor", textColor)
                .append("highlightColor", highlightColor)
                .append("padding", padding)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TextState textState = (TextState) o;

        return new EqualsBuilder()
                .append(fontSize, textState.fontSize)
                .append(font, textState.font)
                .append(text.toString(), textState.text.toString())
                .append(horizontalAlign, textState.horizontalAlign)
                .append(verticalAlign, textState.verticalAlign)
                .append(textColor, textState.textColor)
                .append(highlightColor, textState.highlightColor)
                .append(padding, textState.padding)
                .isEquals();
    }

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
                .append(padding)
                .toHashCode();
    }

    public void copy(TextState state) {
        this.setText(state.getText());
        this.font = state.font;
        this.fontSize = state.fontSize;
        this.setText(state.text.toString());
        this.horizontalAlign = state.horizontalAlign;
        this.verticalAlign = state.verticalAlign;
        this.textColor.set(state.textColor);
        this.highlightColor.set(state.highlightColor);
        this.padding.set(state.padding);
    }
}