package org.liquidengine.legui.style;

import org.joml.Vector4f;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.liquidengine.legui.style.font.Font;
import org.liquidengine.legui.style.font.FontRegistry;

/**
 * The type Style.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class Style {

    private DisplayType display = DisplayType.NONE;
    private PositionType position = PositionType.ABSOLUTE;

    private FlexStyle flexStyle = new FlexStyle();
    private Background background = new Background();
    private Border border = new SimpleLineBorder(ColorConstants.gray(), 1);
    private Font font = FontRegistry.getFont(FontRegistry.DEFAULT);
    private Vector4f cornerRadius = null;

    private Float width;
    private Float height;

    private Float minWidth;
    private Float minHeight;

    private Float maxWidth;
    private Float maxHeight;

    private Float paddingTop;
    private Float paddingBottom;
    private Float paddingRight;
    private Float paddingLeft;

    private Float marginTop;
    private Float marginBottom;
    private Float marginRight;
    private Float marginLeft;

    private Float top;
    private Float bottom;
    private Float right;
    private Float left;
    /**
     * Stroke color. Used to render stroke if component is focused.
     */
    private Vector4f focusedStrokeColor = ColorConstants.lightBlue();

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Float minWidth) {
        this.minWidth = minWidth;
    }

    public Float getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Float minHeight) {
        this.minHeight = minHeight;
    }

    public Float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setPadding(float topBottom, float leftRight) {
        paddingLeft = paddingRight = leftRight;
        paddingTop = paddingBottom = topBottom;
    }


    public void setPadding(float top, float right, float bottom, float left) {
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
        paddingLeft = left;
    }

    public Float getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(Float paddingTop) {
        this.paddingTop = paddingTop;
    }

    public Float getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(Float paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public Float getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(Float paddingRight) {
        this.paddingRight = paddingRight;
    }

    public Float getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(Float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setMargin(float topBottom, float leftRight) {
        marginLeft = marginRight = leftRight;
        marginTop = marginBottom = topBottom;
    }


    public void setMargin(float top, float right, float bottom, float left) {
        marginTop = top;
        marginRight = right;
        marginBottom = bottom;
        marginLeft = left;
    }

    public Float getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(Float marginTop) {
        this.marginTop = marginTop;
    }

    public Float getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(Float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public Float getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(Float marginRight) {
        this.marginRight = marginRight;
    }

    public Float getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(Float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public Float getTop() {
        return top;
    }

    public void setTop(Float top) {
        this.top = top;
    }

    public Float getBottom() {
        return bottom;
    }

    public void setBottom(Float bottom) {
        this.bottom = bottom;
    }

    public Float getRight() {
        return right;
    }

    public void setRight(Float right) {
        this.right = right;
    }

    public Float getLeft() {
        return left;
    }

    public void setLeft(Float left) {
        this.left = left;
    }

    public DisplayType getDisplay() {
        return display;
    }

    public void setDisplay(DisplayType display) {
        if (display == null) {
            this.display = DisplayType.NONE;
        }
        this.display = display;
    }

    /**
     * Gets background.
     *
     * @return the background
     */
    public Background getBackground() {
        return background;
    }

    /**
     * Sets background.
     *
     * @param background the background
     */
    public void setBackground(Background background) {
        if (background != null) {
            this.background = background;
        } else {
            this.background = new Background();
        }
    }

    /**
     * Returns corner radius for all corners as vector <code>new Vector4f(topLeftCornerRadius, topRightCornerRadius, bottomRightCornerRadius,
     * bottomLeftCornerRadius)</code>.
     *
     * @return vector of corner radius for all corners.
     */
    public Vector4f getCornerRadius() {
        return cornerRadius;
    }

    /**
     * Sets corner radius.
     *
     * @param radius the radius
     */
    public void setCornerRadius(float radius) {
        if (cornerRadius != null) {
            cornerRadius.set(radius);
        } else {
            cornerRadius = new Vector4f(radius);
        }
    }

    /**
     * Sets corner radius.
     *
     * @param cornerRadius the radius.
     */
    public void setCornerRadius(Vector4f cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    /**
     * Gets border.
     *
     * @return the border
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Sets border.
     *
     * @param border the border
     */
    public void setBorder(Border border) {
        this.border = border;
    }

    /**
     * Gets font.
     *
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets font.
     *
     * @param font the font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Returns {@link Vector4f} focused stroke color vector where x,y,z,w mapped to r,g,b,a values. <ul> <li>vector.x - red.</li> <li>vector.y - green.</li>
     * <li>vector.z - blue.</li> <li>vector.a - alpha.</li> </ul>
     *
     * @return background color vector.
     */
    public Vector4f getFocusedStrokeColor() {
        return focusedStrokeColor;
    }

    /**
     * Used to set focused stroke color vector where x,y,z,w mapped to r,g,b,a values. <ul> <li>vector.x - red.</li> <li>vector.y - green.</li> <li>vector.z -
     * blue.</li> <li>vector.a - alpha.</li> </ul>
     *
     * @param focusedStrokeColor focused stroke color vector.
     */
    public void setFocusedStrokeColor(Vector4f focusedStrokeColor) {
        this.focusedStrokeColor = focusedStrokeColor;
    }

    /**
     * Used to set focused stroke color vector.
     *
     * @param r red value.
     * @param g green value.
     * @param b blue value.
     * @param a alpha value.
     */
    public void setFocusedStrokeColor(float r, float g, float b, float a) {
        focusedStrokeColor.set(r, g, b, a);
    }

    public FlexStyle getFlexStyle() {
        return flexStyle;
    }

    public PositionType getPosition() {
        return position;
    }

    public void setPosition(PositionType position) {
        if (position != null) {
            this.position = position;
        }
    }

    public void setMinimumSize(float width, float height) {
        setMinWidth(width);
        setMinHeight(height);
    }

    public void setMaximumSize(float width, float height) {
        setMaxWidth(width);
        setMaxHeight(height);
    }

    public enum DisplayType {
        FLEX,
        NONE
    }

    public enum PositionType {
        RELATIVE,
        ABSOLUTE
    }

}
