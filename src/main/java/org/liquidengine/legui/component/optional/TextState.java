package org.liquidengine.legui.component.optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegistry;

import java.io.Serializable;
import java.util.stream.IntStream;

/**
 * Used to hold text state of components.
 */
public class TextState implements Serializable {
    /**
     * Font name.
     */
    private String          font            = FontRegistry.DEFAULT;
    /**
     * Font size.
     */
    private float           fontSize        = 16;
    /**
     * Text data.
     */
    private StringBuffer    text            = new StringBuffer();
    /**
     * Horizontal alignment. By default used {@link HorizontalAlign#CENTER}.
     */
    private HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
    /**
     * Vertical alignment.
     */
    private VerticalAlign   verticalAlign   = VerticalAlign.MIDDLE;
    /**
     * Text color.
     */
    private Vector4f        textColor       = ColorConstants.black();
    /**
     * Highlight color (used to highlight selection).
     */
    private Vector4f        highlightColor  = ColorConstants.blue();
    /**
     * Padding in component.
     */
    private Vector4f        padding         = new Vector4f(0);

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
     * Returns the index within this sequence that is offset from the given index by codePointOffset code points.
     * Unpaired surrogates within the text range given by index and codePointOffset count as one code point each.
     *
     * @param index           the index to be offset
     * @param codePointOffset the offset in code points
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        return text.offsetByCodePoints(index, codePointOffset);
    }

    /**
     * Returns a stream of {@code int} zero-extending the {@code char} values
     * from this sequence.  Any char which maps to a <a
     * href="{@docRoot}/java/lang/Character.html#unicode">surrogate code
     * point</a> is passed through uninterpreted.
     * <p>
     * <p>If the sequence is mutated while the stream is being read, the
     * result is undefined.
     *
     * @return an IntStream of char values from this sequence
     * @since 1.8
     */
    public IntStream chars() {
        return text.chars();
    }

    /**
     * Returns a stream of code point values from this sequence.  Any surrogate
     * pairs encountered in the sequence are combined as if by {@linkplain
     * Character#toCodePoint Character.toCodePoint} and the result is passed
     * to the stream. Any other code units, including ordinary BMP characters,
     * unpaired surrogates, and undefined code units, are zero-extended to
     * {@code int} values which are then passed to the stream.
     * <p>
     * <p>If the sequence is mutated while the stream is being read, the result
     * is undefined.
     *
     * @return an IntStream of Unicode code points from this sequence
     * @since 1.8
     */
    public IntStream codePoints() {
        return text.codePoints();
    }

    /**
     * Characters are copied from this sequence into the destination character array dst.<br>
     * The first character to be copied is at index srcBegin; the last character to be copied is at index srcEnd-1.<br>
     * The total number of characters to be copied is srcEnd-srcBegin.<br>
     * The characters are copied into the subarray of dst starting at index dstBegin and ending at index:<br><br>
     * {code dstbegin + (srcEnd-srcBegin) - 1}
     *
     * @param srcBegin start copying at this offset.
     * @param srcEnd   stop copying at this offset.
     * @param dst      the array to copy the data into.
     * @param dstBegin offset into dst.
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        text.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    /**
     * The character at the specified index is set to ch.
     * This sequence is altered to represent a new character sequence that is identical to the old character sequence,
     * except that it contains the character ch at position index.
     * <p>
     * The index argument must be greater than or equal to 0, and less than the length of this sequence.
     *
     * @param index the index of the character to modify.
     * @param ch    the new character.
     */
    public void setCharAt(int index, char ch) {
        text.setCharAt(index, ch);
    }


    /**
     * Appends the string representation of the {@code Object} argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(Object)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   obj   an {@code Object}.
     * @return  a reference to this object.
     */
    public StringBuffer append(Object obj) {
        return text.append(obj);
    }

    /**
     * Appends the specified string to this character sequence.
     * <p>
     * The characters of the {@code String} argument are appended, in
     * order, increasing the length of this sequence by the length of the
     * argument. If {@code str} is {@code null}, then the four
     * characters {@code "null"} are appended.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in the new character sequence is equal to the character
     * at index <i>k</i> in the old character sequence, if <i>k</i> is less
     * than <i>n</i>; otherwise, it is equal to the character at index
     * <i>k-n</i> in the argument {@code str}.
     *
     * @param   str   a string.
     * @return  a reference to this object.
     */
    public StringBuffer append(String str) {
        return text.append(str);
    }

    /**
     * Appends the specified {@code StringBuffer} to this sequence.
     * <p>
     * The characters of the {@code StringBuffer} argument are appended,
     * in order, to the contents of this {@code StringBuffer}, increasing the
     * length of this {@code StringBuffer} by the length of the argument.
     * If {@code sb} is {@code null}, then the four characters
     * {@code "null"} are appended to this {@code StringBuffer}.
     * <p>
     * Let <i>n</i> be the length of the old character sequence, the one
     * contained in the {@code StringBuffer} just prior to execution of the
     * {@code append} method. Then the character at index <i>k</i> in
     * the new character sequence is equal to the character at index <i>k</i>
     * in the old character sequence, if <i>k</i> is less than <i>n</i>;
     * otherwise, it is equal to the character at index <i>k-n</i> in the
     * argument {@code sb}.
     * <p>
     * This method synchronizes on {@code this}, the destination
     * object, but does not synchronize on the source ({@code sb}).
     *
     * @param   sb   the {@code StringBuffer} to append.
     * @return  a reference to this object.
     * @since 1.4
     */
    public StringBuffer append(StringBuffer sb) {
        return text.append(sb);
    }


    /**
     * Appends the specified {@code CharSequence} to this
     * sequence.
     * <p>
     * The characters of the {@code CharSequence} argument are appended,
     * in order, increasing the length of this sequence by the length of the
     * argument.
     *
     * <p>The result of this method is exactly the same as if it were an
     * invocation of this.append(s, 0, s.length());
     *
     * <p>This method synchronizes on {@code this}, the destination
     * object, but does not synchronize on the source ({@code s}).
     *
     * <p>If {@code s} is {@code null}, then the four characters
     * {@code "null"} are appended.
     *
     * @param   s the {@code CharSequence} to append.
     * @return  a reference to this object.
     * @since 1.5
     */
    public StringBuffer append(CharSequence s) {
        return text.append(s);
    }

    /**
     * Appends a subsequence of the specified {@code CharSequence} to this
     * sequence.
     * <p>
     * Characters of the argument {@code s}, starting at
     * index {@code start}, are appended, in order, to the contents of
     * this sequence up to the (exclusive) index {@code end}. The length
     * of this sequence is increased by the value of {@code end - start}.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in this character sequence becomes equal to the
     * character at index <i>k</i> in this sequence, if <i>k</i> is less than
     * <i>n</i>; otherwise, it is equal to the character at index
     * <i>k+start-n</i> in the argument {@code s}.
     * <p>
     * If {@code s} is {@code null}, then this method appends
     * characters as if the s parameter was a sequence containing the four
     * characters {@code "null"}.
     *
     * @param   s the sequence to append.
     * @param   start   the starting index of the subsequence to be appended.
     * @param   end     the end index of the subsequence to be appended.
     * @return  a reference to this object.
     * @throws     IndexOutOfBoundsException if
     *             {@code start} is negative, or
     *             {@code start} is greater than {@code end} or
     *             {@code end} is greater than {@code s.length()}
     */
    public StringBuffer append(CharSequence s, int start, int end) {
        return text.append(s, start, end);
    }


    /**
     * Appends the string representation of the {@code char} array
     * argument to this sequence.
     * <p>
     * The characters of the array argument are appended, in order, to
     * the contents of this sequence. The length of this sequence
     * increases by the length of the argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(char[])},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   str   the characters to be appended.
     * @return  a reference to this object.
     */
    public StringBuffer append(char[] str) {
        return text.append(str);
    }

    /**
     * Appends the string representation of a subarray of the
     * {@code char} array argument to this sequence.
     * <p>
     * Characters of the {@code char} array {@code str}, starting at
     * index {@code offset}, are appended, in order, to the contents
     * of this sequence. The length of this sequence increases
     * by the value of {@code len}.
     * <p>
     * The overall effect is exactly as if the arguments were converted
     * to a string by the method {@link String#valueOf(char[],int,int)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   str      the characters to be appended.
     * @param   offset   the index of the first {@code char} to append.
     * @param   len      the number of {@code char}s to append.
     * @return  a reference to this object.
     * @throws IndexOutOfBoundsException
     *         if {@code offset < 0} or {@code len < 0}
     *         or {@code offset+len > str.length}
     */
    public StringBuffer append(char[] str, int offset, int len) {
        return text.append(str, offset, len);
    }

    /**
     * Appends the string representation of the {@code boolean}
     * argument to the sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(boolean)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   b   a {@code boolean}.
     * @return  a reference to this object.
     */
    public StringBuffer append(boolean b) {
        return text.append(b);
    }

    /**
     * Appends the string representation of the {@code char}
     * argument to this sequence.
     * <p>
     * The argument is appended to the contents of this sequence.
     * The length of this sequence increases by {@code 1}.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(char)},
     * and the character in that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   c   a {@code char}.
     * @return  a reference to this object.
     */
    public StringBuffer append(char c) {
        return text.append(c);
    }

    /**
     * Appends the string representation of the {@code int}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(int)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   i   an {@code int}.
     * @return  a reference to this object.
     */
    public StringBuffer append(int i) {
        return text.append(i);
    }

    /**
     * Appends the string representation of the {@code codePoint}
     * argument to this sequence.
     *
     * <p> The argument is appended to the contents of this sequence.
     * The length of this sequence increases by
     * {@link Character#charCount(int) Character.charCount(codePoint)}.
     *
     * <p> The overall effect is exactly as if the argument were
     * converted to a {@code char} array by the method
     * {@link Character#toChars(int)} and the character in that array
     * were then {@link #append(char[]) appended} to this character
     * sequence.
     *
     * @param   codePoint   a Unicode code point
     * @return  a reference to this object.
     * @exception IllegalArgumentException if the specified
     * {@code codePoint} isn't a valid Unicode code point
     */
    public StringBuffer appendCodePoint(int codePoint) {
        return text.appendCodePoint(codePoint);
    }

    /**
     * Appends the string representation of the {@code long}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(long)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   l   a {@code long}.
     * @return  a reference to this object.
     */
    public StringBuffer append(long l) {
        return text.append(l);
    }


    /**
     * Appends the string representation of the {@code float}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(float)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   f   a {@code float}.
     * @return  a reference to this object.
     */
    public StringBuffer append(float f) {
        return text.append(f);
    }

    /**
     * Appends the string representation of the {@code double}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(double)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * @param   d   a {@code double}.
     * @return  a reference to this object.
     */
    public StringBuffer append(double d) {
        return text.append(d);
    }

    /**
     * Removes the characters in a substring of this sequence.
     * The substring begins at the specified {@code start} and extends to
     * the character at index {@code end - 1} or to the end of the
     * sequence if no such character exists. If
     * {@code start} is equal to {@code end}, no changes are made.
     *
     * @param      start  The beginning index, inclusive.
     * @param      end    The ending index, exclusive.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             is negative, greater than {@code length()}, or
     *             greater than {@code end}.
     */
    public StringBuffer delete(int start, int end) {
        return text.delete(start, end);
    }

    /**
     * Removes the {@code char} at the specified position in this
     * sequence. This sequence is shortened by one {@code char}.
     *
     * <p>Note: If the character at the given index is a supplementary
     * character, this method does not remove the entire character. If
     * correct handling of supplementary characters is required,
     * determine the number of {@code char}s to remove by calling
     * {@code Character.charCount(thisSequence.codePointAt(index))},
     * where {@code thisSequence} is this sequence.
     *
     * @param       index  Index of {@code char} to remove
     * @return      This object.
     * @throws      StringIndexOutOfBoundsException  if the {@code index}
     *              is negative or greater than or equal to
     *              {@code length()}.
     */
    public StringBuffer deleteCharAt(int index) {
        return text.deleteCharAt(index);
    }

    /**
     * Replaces the characters in a substring of this sequence
     * with characters in the specified {@code String}. The substring
     * begins at the specified {@code start} and extends to the character
     * at index {@code end - 1} or to the end of the
     * sequence if no such character exists. First the
     * characters in the substring are removed and then the specified
     * {@code String} is inserted at {@code start}. (This
     * sequence will be lengthened to accommodate the
     * specified String if necessary.)
     *
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @param      str   String that will replace previous contents.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             is negative, greater than {@code length()}, or
     *             greater than {@code end}.
     */
    public StringBuffer replace(int start, int end, String str) {
        return text.replace(start, end, str);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this character sequence. The
     * substring begins at the specified index and extends to the end of
     * this sequence.
     *
     * @param      start    The beginning index, inclusive.
     * @return     The new string.
     * @throws     StringIndexOutOfBoundsException  if {@code start} is
     *             less than zero, or greater than the length of this object.
     */
    public String substring(int start) {
        return text.substring(start);
    }

    /**
     * Returns a new character sequence that is a subsequence of this sequence.
     *
     * <p> An invocation of this method of the form
     *
     * <pre>{@code
     * sb.subSequence(begin,&nbsp;end)}</pre>
     *
     * behaves in exactly the same way as the invocation
     *
     * <pre>{@code
     * sb.substring(begin,&nbsp;end)}</pre>
     *
     * This method is provided so that this class can
     * implement the {@link CharSequence} interface.
     *
     * @param      start   the start index, inclusive.
     * @param      end     the end index, exclusive.
     * @return     the specified subsequence.
     *
     * @throws  IndexOutOfBoundsException
     *          if {@code start} or {@code end} are negative,
     *          if {@code end} is greater than {@code length()},
     *          or if {@code start} is greater than {@code end}
     * @spec JSR-51
     */
    public CharSequence subSequence(int start, int end) {
        return text.subSequence(start, end);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this sequence. The
     * substring begins at the specified {@code start} and
     * extends to the character at index {@code end - 1}.
     *
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @return     The new string.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             or {@code end} are negative or greater than
     *             {@code length()}, or {@code start} is
     *             greater than {@code end}.
     */
    public String substring(int start, int end) {
        return text.substring(start, end);
    }

    /**
     * Inserts the string representation of a subarray of the {@code str}
     * array argument into this sequence. The subarray begins at the
     * specified {@code offset} and extends {@code len} {@code char}s.
     * The characters of the subarray are inserted into this sequence at
     * the position indicated by {@code index}. The length of this
     * sequence increases by {@code len} {@code char}s.
     *
     * @param      index    position at which to insert subarray.
     * @param      str       A {@code char} array.
     * @param      offset   the index of the first {@code char} in subarray to
     *             be inserted.
     * @param      len      the number of {@code char}s in the subarray to
     *             be inserted.
     * @return     This object
     * @throws     StringIndexOutOfBoundsException  if {@code index}
     *             is negative or greater than {@code length()}, or
     *             {@code offset} or {@code len} are negative, or
     *             {@code (offset+len)} is greater than
     *             {@code str.length}.
     */
    public StringBuffer insert(int index, char[] str, int offset, int len) {
        return text.insert(index, str, getOffset(offset), len);
    }

    /**
     * Inserts the string representation of the {@code Object}
     * argument into this character sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(Object)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      obj      an {@code Object}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, Object obj) {
        return text.insert(getOffset(offset), obj);
    }

    /**
     * Inserts the string into this character sequence.
     * <p>
     * The characters of the {@code String} argument are inserted, in
     * order, into this sequence at the indicated offset, moving up any
     * characters originally above that position and increasing the length
     * of this sequence by the length of the argument. If
     * {@code str} is {@code null}, then the four characters
     * {@code "null"} are inserted into this sequence.
     * <p>
     * The character at index <i>k</i> in the new character sequence is
     * equal to:
     * <ul>
     * <li>the character at index <i>k</i> in the old character sequence, if
     * <i>k</i> is less than {@code offset}
     * <li>the character at index <i>k</i>{@code -offset} in the
     * argument {@code str}, if <i>k</i> is not less than
     * {@code offset} but is less than {@code offset+str.length()}
     * <li>the character at index <i>k</i>{@code -str.length()} in the
     * old character sequence, if <i>k</i> is not less than
     * {@code offset+str.length()}
     * </ul><p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      str      a string.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, String str) {
        return text.insert(getOffset(offset), str);
    }

    /**
     * Inserts the string representation of the {@code char} array
     * argument into this sequence.
     * <p>
     * The characters of the array argument are inserted into the
     * contents of this sequence at the position indicated by
     * {@code offset}. The length of this sequence increases by
     * the length of the argument.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(char[])},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      str      a character array.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, char[] str) {
        return text.insert(getOffset(offset), str);
    }

    /**
     * Inserts the specified {@code CharSequence} into this sequence.
     * <p>
     * The characters of the {@code CharSequence} argument are inserted,
     * in order, into this sequence at the indicated offset, moving up
     * any characters originally above that position and increasing the length
     * of this sequence by the length of the argument s.
     * <p>
     * The result of this method is exactly the same as if it were an
     * invocation of this object's
     * {@link #insert(int,CharSequence,int,int) insert}(dstOffset, s, 0, s.length())
     * method.
     *
     * <p>If {@code s} is {@code null}, then the four characters
     * {@code "null"} are inserted into this sequence.
     *
     * @param      dstOffset   the offset.
     * @param      s the sequence to be inserted
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int dstOffset, CharSequence s) {
        return text.insert(getOffset(dstOffset), s);
    }

    /**
     * Inserts a subsequence of the specified {@code CharSequence} into
     * this sequence.
     * <p>
     * The subsequence of the argument {@code s} specified by
     * {@code start} and {@code end} are inserted,
     * in order, into this sequence at the specified destination offset, moving
     * up any characters originally above that position. The length of this
     * sequence is increased by {@code end - start}.
     * <p>
     * The character at index <i>k</i> in this sequence becomes equal to:
     * <ul>
     * <li>the character at index <i>k</i> in this sequence, if
     * <i>k</i> is less than {@code dstOffset}
     * <li>the character at index <i>k</i>{@code +start-dstOffset} in
     * the argument {@code s}, if <i>k</i> is greater than or equal to
     * {@code dstOffset} but is less than {@code dstOffset+end-start}
     * <li>the character at index <i>k</i>{@code -(end-start)} in this
     * sequence, if <i>k</i> is greater than or equal to
     * {@code dstOffset+end-start}
     * </ul><p>
     * The {@code dstOffset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     * <p>The start argument must be nonnegative, and not greater than
     * {@code end}.
     * <p>The end argument must be greater than or equal to
     * {@code start}, and less than or equal to the length of s.
     *
     * <p>If {@code s} is {@code null}, then this method inserts
     * characters as if the s parameter was a sequence containing the four
     * characters {@code "null"}.
     *
     * @param      dstOffset   the offset in this sequence.
     * @param      s       the sequence to be inserted.
     * @param      start   the starting index of the subsequence to be inserted.
     * @param      end     the end index of the subsequence to be inserted.
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if {@code dstOffset}
     *             is negative or greater than {@code this.length()}, or
     *              {@code start} or {@code end} are negative, or
     *              {@code start} is greater than {@code end} or
     *              {@code end} is greater than {@code s.length()}
     */
    public StringBuffer insert(int dstOffset, CharSequence s, int start, int end) {
        return text.insert(getOffset(dstOffset), s, start, end);
    }

    /**
     * Inserts the string representation of the {@code boolean}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(boolean)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      b        a {@code boolean}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, boolean b) {
        return text.insert(getOffset(offset), b);
    }

    /**
     * Inserts the string representation of the {@code char}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(char)},
     * and the character in that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      c        a {@code char}.
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, char c) {
        return text.insert(getOffset(offset), c);
    }

    /**
     * Inserts the string representation of the second {@code int}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(int)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      i        an {@code int}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, int i) {
        return text.insert(getOffset(offset), i);
    }

    /**
     * Inserts the string representation of the {@code long}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(long)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      l        a {@code long}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public StringBuffer insert(int offset, long l) {
        return text.insert(getOffset(offset), l);
    }

    /**
     * Inserts the string representation of the {@code float}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(float)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * @param      offset   the offset.
     * @param      f        a {@code float}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
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
}