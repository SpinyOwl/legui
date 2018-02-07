package org.liquidengine.legui.component.optional;

import java.io.Serializable;
import java.util.stream.IntStream;
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
    private StringBuffer text = new StringBuffer();
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
     * Padding in component.
     */
    private Vector4f padding = new Vector4f(0);

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
     * Returns padding. <ul> <li>x - left</li> <li>y - top</li> <li>z - right</li> <li>w - bottom</li> </ul>
     *
     * @return padding
     */
    public Vector4f getPadding() {
        return padding;
    }

    /**
     * Used to set padding. <ul> <li>x - left</li> <li>y - top</li> <li>z - right</li> <li>w - bottom</li> </ul>
     *
     * @param padding text padding.
     */
    public void setPadding(Vector4f padding) {
        this.padding = padding;
    }

    /**
     * Used to set padding.
     *
     * @param left left padding
     * @param top top padding
     * @param right right padding
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
     * Returns text buffer capacity.
     *
     * @return text buffer capacity.
     * @see StringBuffer#capacity()
     */
    public int capacity() {
        return text.capacity();
    }

    /**
     * Ensures that the capacity is at least equal to the specified minimum.
     *
     * @param minimumCapacity - the minimum desired capacity.
     * @see StringBuffer#ensureCapacity(int)
     */
    public void ensureCapacity(int minimumCapacity) {
        text.ensureCapacity(minimumCapacity);
    }

    /**
     * Attempts to reduce storage used for the character sequence.
     *
     * @see StringBuffer#trimToSize()
     */
    public void trimToSize() {
        text.trimToSize();
    }

    /**
     * Sets the length of the character sequence.
     *
     * @param newLength the new length
     * @throws IndexOutOfBoundsException if the {@code newLength} argument is negative.
     * @see StringBuffer#setLength(int)
     */
    public void setLength(int newLength) {
        text.setLength(newLength);
    }

    /**
     * Returns the <code>char</code> value at the specified index.
     *
     * @param index the index of the char value to be returned
     * @return the specified <code>char</code> value
     * @throws IndexOutOfBoundsException if the <tt>index</tt> argument is negative or not less than <tt>length()</tt>
     * @see StringBuffer#charAt(int)
     */
    public char charAt(int index) {
        return text.charAt(index);
    }

    /**
     * Returns the character (Unicode code point) at the specified index.
     *
     * @param index the index to the {@code char} values
     * @return the code point value of the character at the {@code index}
     * @throws IndexOutOfBoundsException if the {@code index} argument is negative or not less than the length of this sequence.
     * @see StringBuffer#codePointAt(int)
     */
    public int codePointAt(int index) {
        return text.codePointAt(index);
    }

    /**
     * Returns the character (Unicode code point) before the specified index.
     *
     * @param index the index following the code point that should be returned
     * @return the Unicode code point value before the given index.
     * @throws IndexOutOfBoundsException if the {@code index} argument is less than 1 or greater than the length of this sequence.
     * @see StringBuffer#codePointBefore(int)
     */
    public int codePointBefore(int index) {
        return text.codePointBefore(index);
    }


    /**
     * Returns the number of Unicode code points in the specified text range of this sequence.
     *
     * @param beginIndex the index to the first {@code char} of the text range.
     * @param endIndex the index after the last {@code char} of the text range.
     * @return the number of Unicode code points in the specified text range
     * @throws IndexOutOfBoundsException if the {@code beginIndex} is negative, or {@code endIndex} is larger than the length of this sequence, or {@code
     * beginIndex} is larger than {@code endIndex}.
     * @see StringBuffer#codePointCount(int, int)
     */
    public int codePointCount(int beginIndex, int endIndex) {
        return text.codePointCount(beginIndex, endIndex);
    }

    /**
     * Returns the index within this sequence that is offset from the given index by codePointOffset code points.
     *
     * @param index the index to be offset
     * @param codePointOffset the offset in code points
     * @return the index within this sequence that is offset from the given index by codePointOffset code points.
     * @see StringBuffer#offsetByCodePoints(int, int)
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        return text.offsetByCodePoints(index, codePointOffset);
    }

    /**
     * Returns a stream of {@code int} zero-extending the {@code char} values from this sequence.
     *
     * @return an IntStream of char values from this sequence
     * @see StringBuffer#chars()
     */
    public IntStream chars() {
        return text.chars();
    }

    /**
     * Returns a stream of code point values from this sequence.
     *
     * @return an IntStream of Unicode code points from this sequence
     * @see StringBuffer#codePoints()
     */
    public IntStream codePoints() {
        return text.codePoints();
    }

    /**
     * Characters are copied from this sequence into the destination character array dst.
     *
     * @param srcBegin start copying at this offset.
     * @param srcEnd stop copying at this offset.
     * @param dst the array to copy the data into.
     * @param dstBegin offset into dst.
     * @see StringBuffer#getChars(int, int, char[], int)
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        text.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    /**
     * The character at the specified index is set to ch.
     *
     * @param index the index of the character to modify.
     * @param ch the new character.
     * @see StringBuffer#setCharAt(int, char)
     */
    public void setCharAt(int index, char ch) {
        text.setCharAt(index, ch);
    }


    /**
     * Appends the string representation of the {@code Object} argument.
     *
     * @param obj an {@code Object}.
     * @return a reference to this object.
     * @see StringBuffer#append(Object)
     */
    public StringBuffer append(Object obj) {
        return text.append(obj);
    }

    /**
     * Appends the specified string to this character sequence.
     *
     * @param str a string.
     * @return a reference to this object.
     * @see StringBuffer#append(String)
     */
    public StringBuffer append(String str) {
        return text.append(str);
    }

    /**
     * Appends the specified {@code StringBuffer} to this sequence.
     *
     * @param sb the {@code StringBuffer} to append.
     * @return a reference to this object.
     * @see StringBuffer#append(StringBuffer)
     */
    public StringBuffer append(StringBuffer sb) {
        return text.append(sb);
    }


    /**
     * Appends the specified {@code CharSequence} to this sequence.
     *
     * @param s the {@code CharSequence} to append.
     * @return a reference to this object.
     * @see StringBuffer#append(CharSequence)
     */
    public StringBuffer append(CharSequence s) {
        return text.append(s);
    }

    /**
     * Appends a subsequence of the specified {@code CharSequence} to this sequence.
     *
     * @param s the sequence to append.
     * @param start the starting index of the subsequence to be appended.
     * @param end the end index of the subsequence to be appended.
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if {@code start} is negative, or {@code start} is greater than {@code end} or {@code end} is greater than {@code
     * s.length()}
     * @see StringBuffer#append(CharSequence, int, int)
     */
    public StringBuffer append(CharSequence s, int start, int end) {
        return text.append(s, start, end);
    }


    /**
     * Appends the string representation of the {@code char} array argument to this sequence.
     *
     * @param str the characters to be appended.
     * @return a reference to this object.
     * @see StringBuffer#append(char[])
     */
    public StringBuffer append(char[] str) {
        return text.append(str);
    }

    /**
     * Appends the string representation of a subarray of the {@code char} array argument to this sequence.
     *
     * @param str the characters to be appended.
     * @param offset the index of the first {@code char} to append.
     * @param len the number of {@code char}s to append.
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if {@code offset < 0} or {@code len < 0} or {@code offset+len > str.length}
     * @see StringBuffer#append(char[], int, int)
     */
    public StringBuffer append(char[] str, int offset, int len) {
        return text.append(str, offset, len);
    }

    /**
     * Appends the string representation of the {@code boolean} argument to the sequence.
     *
     * @param b a {@code boolean}.
     * @return a reference to this object.
     * @see StringBuffer#append(boolean)
     */
    public StringBuffer append(boolean b) {
        return text.append(b);
    }

    /**
     * Appends the string representation of the {@code char} argument to this sequence.
     *
     * @param c a {@code char}.
     * @return a reference to this object.
     * @see StringBuffer#append(char)
     */
    public StringBuffer append(char c) {
        return text.append(c);
    }

    /**
     * Appends the string representation of the {@code int} argument to this sequence.
     *
     * @param i an {@code int}.
     * @return a reference to this object.
     * @see StringBuffer#append(int)
     */
    public StringBuffer append(int i) {
        return text.append(i);
    }

    /**
     * Appends the string representation of the {@code codePoint} argument to this sequence.
     *
     * @param codePoint a Unicode code point
     * @return a reference to this object.
     * @throws IllegalArgumentException if the specified {@code codePoint} isn't a valid Unicode code point
     * @see StringBuffer#appendCodePoint(int)
     */
    public StringBuffer appendCodePoint(int codePoint) {
        return text.appendCodePoint(codePoint);
    }

    /**
     * Appends the string representation of the {@code long} argument to this sequence.
     *
     * @param l a {@code long}.
     * @return a reference to this object.
     * @see StringBuffer#append(long)
     */
    public StringBuffer append(long l) {
        return text.append(l);
    }


    /**
     * Appends the string representation of the {@code float} argument to this sequence.
     *
     * @param f a {@code float}.
     * @return a reference to this object.
     * @see StringBuffer#append(float)
     */
    public StringBuffer append(float f) {
        return text.append(f);
    }

    /**
     * Appends the string representation of the {@code double} argument to this sequence.
     *
     * @param d a {@code double}.
     * @return a reference to this object.
     * @see StringBuffer#append(double)
     */
    public StringBuffer append(double d) {
        return text.append(d);
    }

    /**
     * Removes the characters in a substring of this sequence.
     *
     * @param start The beginning index, inclusive.
     * @param end The ending index, exclusive.
     * @return This object.
     * @throws StringIndexOutOfBoundsException if {@code start} is negative, greater than {@code length()}, or greater than {@code end}.
     * @see StringBuffer#delete(int, int)
     */
    public StringBuffer delete(int start, int end) {
        return text.delete(start, end);
    }

    /**
     * Removes the {@code char} at the specified position in this sequence.
     *
     * @param index Index of {@code char} to remove
     * @return This object.
     * @throws StringIndexOutOfBoundsException if the {@code index} is negative or greater than or equal to {@code length()}.
     * @see StringBuffer#deleteCharAt(int)
     */
    public StringBuffer deleteCharAt(int index) {
        return text.deleteCharAt(index);
    }

    /**
     * Replaces the characters in a substring of this sequence with characters in the specified {@code String}.
     *
     * @param start The beginning index, inclusive.
     * @param end The ending index, exclusive.
     * @param str String that will replace previous contents.
     * @return This object.
     * @throws StringIndexOutOfBoundsException if {@code start} is negative, greater than {@code length()}, or greater than {@code end}.
     * @see StringBuffer#replace(int, int, String)
     */
    public StringBuffer replace(int start, int end, String str) {
        return text.replace(start, end, str);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of characters currently contained in this character sequence.
     *
     * @param start The beginning index, inclusive.
     * @return The new string.
     * @throws StringIndexOutOfBoundsException if {@code start} is less than zero, or greater than the length of this object.
     * @see StringBuffer#substring(int)
     */
    public String substring(int start) {
        return text.substring(start);
    }

    /**
     * Returns a new character sequence that is a subsequence of this sequence.
     *
     * @param start the start index, inclusive.
     * @param end the end index, exclusive.
     * @return the specified subsequence.
     * @throws IndexOutOfBoundsException if {@code start} or {@code end} are negative, if {@code end} is greater than {@code length()}, or if {@code start} is
     * greater than {@code end}
     * @see StringBuffer#subSequence(int, int)
     */
    public CharSequence subSequence(int start, int end) {
        return text.subSequence(start, end);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of characters currently contained in this sequence.
     *
     * @param start The beginning index, inclusive.
     * @param end The ending index, exclusive.
     * @return The new string.
     * @throws StringIndexOutOfBoundsException if {@code start} or {@code end} are negative or greater than {@code length()}, or {@code start} is greater than
     * {@code end}.
     * @see StringBuffer#substring(int, int)
     */
    public String substring(int start, int end) {
        return text.substring(start, end);
    }

    /**
     * Inserts the string representation of a subarray of the {@code str} array argument into this sequence.
     *
     * @param index position at which to insert subarray.
     * @param str A {@code char} array.
     * @param offset the index of the first {@code char} in subarray to be inserted.
     * @param len the number of {@code char}s in the subarray to be inserted.
     * @return This object
     * @throws StringIndexOutOfBoundsException if {@code index} is negative or greater than {@code length()}, or {@code offset} or {@code len} are negative, or
     * {@code (offset+len)} is greater than {@code str.length}.
     * @see StringBuffer#insert(int, char[], int, int)
     */
    public StringBuffer insert(int index, char[] str, int offset, int len) {
        return text.insert(index, str, getOffset(offset), len);
    }

    /**
     * Inserts the string representation of the {@code Object} argument into this character sequence.
     *
     * @param offset the offset.
     * @param obj an {@code Object}.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, Object)
     */
    public StringBuffer insert(int offset, Object obj) {
        return text.insert(getOffset(offset), obj);
    }

    /**
     * Inserts the string into this character sequence.
     *
     * @param offset the offset.
     * @param str a string.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, String)
     */
    public StringBuffer insert(int offset, String str) {
        return text.insert(getOffset(offset), str);
    }

    /**
     * Inserts the string representation of the {@code char} array argument into this sequence.
     *
     * @param offset the offset.
     * @param str a character array.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, char[])
     */
    public StringBuffer insert(int offset, char[] str) {
        return text.insert(getOffset(offset), str);
    }

    /**
     * Inserts the specified {@code CharSequence} into this sequence.
     *
     * @param dstOffset the offset.
     * @param s the sequence to be inserted
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, CharSequence)
     */
    public StringBuffer insert(int dstOffset, CharSequence s) {
        return text.insert(getOffset(dstOffset), s);
    }

    /**
     * Inserts a subsequence of the specified {@code CharSequence} into this sequence.
     *
     * @param dstOffset the offset in this sequence.
     * @param s the sequence to be inserted.
     * @param start the starting index of the subsequence to be inserted.
     * @param end the end index of the subsequence to be inserted.
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if {@code dstOffset} is negative or greater than {@code this.length()}, or {@code start} or {@code end} are negative,
     * or {@code start} is greater than {@code end} or {@code end} is greater than {@code s.length()}
     * @see StringBuffer#insert(int, CharSequence, int, int)
     */
    public StringBuffer insert(int dstOffset, CharSequence s, int start, int end) {
        return text.insert(getOffset(dstOffset), s, start, end);
    }

    /**
     * Inserts the string representation of the {@code boolean} argument into this sequence.
     *
     * @param offset the offset.
     * @param b a {@code boolean}.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, boolean)
     */
    public StringBuffer insert(int offset, boolean b) {
        return text.insert(getOffset(offset), b);
    }

    /**
     * Inserts the string representation of the {@code char} argument into this sequence.
     *
     * @param offset the offset.
     * @param c a {@code char}.
     * @return a reference to this object.
     * @throws IndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, char)
     */
    public StringBuffer insert(int offset, char c) {
        return text.insert(getOffset(offset), c);
    }

    /**
     * Inserts the string representation of the second {@code int} argument into this sequence.
     *
     * @param offset the offset.
     * @param i an {@code int}.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, int)
     */
    public StringBuffer insert(int offset, int i) {
        return text.insert(getOffset(offset), i);
    }

    /**
     * Inserts the string representation of the {@code long} argument into this sequence.
     *
     * @param offset the offset.
     * @param l a {@code long}.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, long)
     */
    public StringBuffer insert(int offset, long l) {
        return text.insert(getOffset(offset), l);
    }

    /**
     * Inserts the string representation of the {@code float} argument into this sequence.
     *
     * @param offset the offset.
     * @param f a {@code float}.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, float)
     */
    public StringBuffer insert(int offset, float f) {
        return text.insert(getOffset(offset), f);
    }

    /**
     * Inserts the string representation of the {@code double} argument into this sequence.
     *
     * @param offset the offset.
     * @param d a {@code double}.
     * @return a reference to this object.
     * @throws StringIndexOutOfBoundsException if the offset is invalid.
     * @see StringBuffer#insert(int, double)
     */
    public StringBuffer insert(int offset, double d) {
        return text.insert(getOffset(offset), d);
    }

    /**
     * Filter for provided offset to make it no greater than text length.
     *
     * @param offset offset to check.
     * @return offset if it less than text length or text length.
     */
    private int getOffset(int offset) {
        return offset > text.length() ? text.length() : offset;
    }

    /**
     * Returns the index within this string of the first occurrence of the specified substring.
     *
     * @param str any string.
     * @return if the string argument occurs as a substring within this object, then the index of the first character of the first such substring is returned;
     * if it does not occur as a substring, {@code -1} is returned.
     * @see StringBuffer#indexOf(String)
     */
    public int indexOf(String str) {
        return text.indexOf(str);
    }

    /**
     * Returns the index within this string of the first occurrence of the specified substring, starting at the specified index.
     *
     * @param str the substring for which to search.
     * @param fromIndex the index from which to start the search.
     * @return the index within this string of the first occurrence of the specified substring, starting at the specified index.
     * @see StringBuffer#indexOf(String, int)
     */
    public int indexOf(String str, int fromIndex) {
        return text.indexOf(str, fromIndex);
    }

    /**
     * Returns the index within this string of the rightmost occurrence of the specified substring.
     *
     * @param str the substring to search for.
     * @return if the string argument occurs one or more times as a substring within this object, then the index of the first character of the last such
     * substring is returned. If it does not occur as a substring, {@code -1} is returned.
     * @see StringBuffer#lastIndexOf(String)
     */
    public int lastIndexOf(String str) {
        return text.lastIndexOf(str);
    }

    /**
     * Returns the index within this string of the last occurrence of the specified substring.
     *
     * @param str the substring to search for.
     * @param fromIndex the index to start the search from.
     * @return the index within this sequence of the last occurrence of the specified substring.
     * @see StringBuffer#lastIndexOf(String, int)
     */
    public int lastIndexOf(String str, int fromIndex) {
        return text.lastIndexOf(str, fromIndex);
    }

    /**
     * Causes this character sequence to be replaced by the reverse of the sequence.
     *
     * @return a reference to this object.
     * @see StringBuffer#reverse()
     */
    public StringBuffer reverse() {
        return text.reverse();
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
            .append("padding", padding)
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
            .append(text.toString(), textState.text.toString())
            .append(horizontalAlign, textState.horizontalAlign)
            .append(verticalAlign, textState.verticalAlign)
            .append(textColor, textState.textColor)
            .append(highlightColor, textState.highlightColor)
            .append(padding, textState.padding)
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
            .append(padding)
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
        this.setText(state.text.toString());
        this.horizontalAlign = state.horizontalAlign;
        this.verticalAlign = state.verticalAlign;
        this.textColor.set(state.textColor);
        this.highlightColor.set(state.highlightColor);
        this.padding.set(state.padding);
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