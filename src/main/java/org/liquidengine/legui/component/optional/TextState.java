package org.liquidengine.legui.component.optional;

import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class TextState {
    protected String font = FontRegister.DEFAULT;
    protected float fontSize = 16;
    protected StringBuffer text = new StringBuffer();
    protected HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;
    protected VerticalAlign verticalAlign = VerticalAlign.MIDDLE;
    protected Vector4f textColor = ColorConstants.black();
    protected Vector4f highlightColor = ColorConstants.transparent();
    protected Vector4f padding = new Vector4f(1);

    public TextState() {
    }


    public TextState(String text) {
        setText(text);
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }


    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }


    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }


    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }


    public Vector4f getTextColor() {
        return textColor;
    }

    public void setTextColor(Vector4f textColor) {
        this.textColor = textColor;
    }


    public void setTextColor(float r, float g, float b, float a) {
        this.textColor = new Vector4f(r, g, b, a);
    }


    public Vector4f getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Vector4f highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setHighlightColor(float r, float g, float b, float a) {
        this.highlightColor = new Vector4f(r, g, b, a);
    }


    public Vector4f getPadding() {
        return padding;
    }

    public void setPadding(Vector4f padding) {
        this.padding = padding;
    }

    public void setPadding(float left, float top, float right, float bottom) {
        this.padding = new Vector4f(left, top, right, bottom);
    }

    // textState component methods


    public void append(Character c) {
        text.append(c);
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String text) {
        this.text.setLength(0);
        this.text.append(text);
    }

    public int length() {
        return text.length();
    }

    public int capacity() {
        return text.capacity();
    }

    public void ensureCapacity(int minimumCapacity) {
        text.ensureCapacity(minimumCapacity);
    }

    public void trimToSize() {
        text.trimToSize();
    }

    public void setLength(int newLength) {
        text.setLength(newLength);
    }

    public char charAt(int index) {
        return text.charAt(index);
    }

    public int codePointAt(int index) {
        return text.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return text.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return text.codePointCount(beginIndex, endIndex);
    }

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
}
