package org.liquidengine.legui.style;

import org.joml.Vector4f;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.liquidengine.legui.style.font.Font;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.style.length.Unit;
import org.liquidengine.legui.style.shadow.Shadow;

import static org.liquidengine.legui.style.length.LengthType.PIXEL;

/**
 * The type Style.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class Style {

    private DisplayType display = DisplayType.MANUAL;
    private PositionType position = PositionType.ABSOLUTE;

    private FlexStyle flexStyle = new FlexStyle();
    private Background background = new Background();
    private Border border = new SimpleLineBorder(ColorConstants.gray(), 1);
    private Font font = FontRegistry.getFont(FontRegistry.DEFAULT);

    private Float borderTopLeftRadius;
    private Float borderTopRightRadius;
    private Float borderBottomRightRadius;
    private Float borderBottomLeftRadius;

    private Float width;
    private Float height;

    private Float minWidth;
    private Float minHeight;

    private Float maxWidth;
    private Float maxHeight;

    private Length paddingTop;
    private Length paddingBottom;
    private Length paddingRight;
    private Length paddingLeft;

    private Unit marginTop;
    private Unit marginBottom;
    private Unit marginRight;
    private Unit marginLeft;

    private Float top;
    private Float bottom;
    private Float right;
    private Float left;

    private Shadow shadow;

    /**
     * Stroke color. Used to render stroke if component is focused.
     */
    private Vector4f focusedStrokeColor = ColorConstants.lightBlue();

    /**
     * Used to set border radius.
     *
     * @param topLeftBottomRight top left and bottom right radius.
     * @param topRightBottomLeft top right and bottom left radius.
     */
    public void setBorderRadius(Float topLeftBottomRight, Float topRightBottomLeft) {
        borderTopLeftRadius = borderBottomRightRadius = topLeftBottomRight;
        borderTopRightRadius = borderBottomLeftRadius = topRightBottomLeft;
    }

    /**
     * Used to set border radius.
     *
     * @param topLeft            top left radius.
     * @param bottomRight        bottom right radius.
     * @param topRightBottomLeft top right and bottom left radius.
     */
    public void setBorderRadius(Float topLeft, Float topRightBottomLeft, Float bottomRight) {
        borderTopLeftRadius = topLeft;
        borderTopRightRadius = borderBottomLeftRadius = topRightBottomLeft;
        borderBottomRightRadius = bottomRight;
    }

    /**
     * Used to set border radius.
     *
     * @param topLeft     top left radius.
     * @param topRight    top right radius.
     * @param bottomRight bottom right radius.
     * @param bottomLeft  bottom left radius.
     */
    public void setBorderRadius(Float topLeft, Float topRight, Float bottomRight, Float bottomLeft) {
        borderTopLeftRadius = topLeft;
        borderTopRightRadius = topRight;
        borderBottomRightRadius = bottomRight;
        borderBottomLeftRadius = bottomLeft;
    }

    /**
     * Returns vector of four border radius elements where: x = top left, y = top right, z = bottom right, w = bottom left
     *
     * @return vector of four border radius.
     */
    public Vector4f getBorderRadius() {
        return new Vector4f(borderTopLeftRadius == null ? 0 : borderTopLeftRadius,
            borderTopRightRadius == null ? 0 : borderTopRightRadius,
            borderBottomRightRadius == null ? 0 : borderBottomRightRadius,
            borderBottomLeftRadius == null ? 0 : borderBottomLeftRadius);
    }

    /**
     * Used to set border radius for all four corners.
     *
     * @param radius radius to set. Sets border radius to all corners.
     */
    public void setBorderRadius(Float radius) {
        borderTopLeftRadius = borderTopRightRadius =
            borderBottomRightRadius = borderBottomLeftRadius = radius;
    }

    /**
     * Returns top left border radius.
     *
     * @return top left border radius.
     */
    public Float getBorderTopLeftRadius() {
        return borderTopLeftRadius;
    }

    /**
     * Used to set top left border radius.
     *
     * @param borderTopLeftRadius top left border radius.
     */
    public void setBorderTopLeftRadius(Float borderTopLeftRadius) {
        this.borderTopLeftRadius = borderTopLeftRadius;
    }

    /**
     * Returns top right border radius.
     *
     * @return top right border radius.
     */
    public Float getBorderTopRightRadius() {
        return borderTopRightRadius;
    }


    /**
     * Used to set top right border radius.
     *
     * @param borderTopRightRadius top right border radius.
     */
    public void setBorderTopRightRadius(Float borderTopRightRadius) {
        this.borderTopRightRadius = borderTopRightRadius;
    }

    /**
     * Returns bottom right border radius.
     *
     * @return bottom right border radius.
     */
    public Float getBorderBottomRightRadius() {
        return borderBottomRightRadius;
    }


    /**
     * Used to set bottom right border radius.
     *
     * @param borderBottomRightRadius bottom right border radius.
     */
    public void setBorderBottomRightRadius(Float borderBottomRightRadius) {
        this.borderBottomRightRadius = borderBottomRightRadius;
    }

    /**
     * Returns bottom left border radius.
     *
     * @return bottom left border radius.
     */
    public Float getBorderBottomLeftRadius() {
        return borderBottomLeftRadius;
    }


    /**
     * Used to set bottom left border radius.
     *
     * @param borderBottomLeftRadius bottom left border radius.
     */
    public void setBorderBottomLeftRadius(Float borderBottomLeftRadius) {
        this.borderBottomLeftRadius = borderBottomLeftRadius;
    }

    /**
     * Returns width.
     *
     * @return width.
     */
    public Float getWidth() {
        return width;
    }

    /**
     * Used to set width.
     *
     * @param width width to set.
     */
    public void setWidth(Float width) {
        this.width = width;
    }

    /**
     * Used to set height.
     *
     * @return height to set.
     */
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

    public void setPadding(Float padding) {
        this.setPadding(PIXEL.length(padding));
    }

    public void setPadding(Length padding) {
        paddingLeft = paddingRight =
            paddingTop = paddingBottom = padding;
    }

    public void setPadding(Float topBottom, Float leftRight) {
        this.setPadding(PIXEL.length(topBottom), PIXEL.length(leftRight));
    }

    public void setPadding(Length topBottom, Length leftRight) {
        paddingLeft = paddingRight = leftRight;
        paddingTop = paddingBottom = topBottom;
    }

    public void setPadding(Float top, Float right, Float bottom, Float left) {
        this.setPadding(PIXEL.length(top), PIXEL.length(right), PIXEL.length(bottom), PIXEL.length(left));
    }

    public void setPadding(Length top, Length right, Length bottom, Length left) {
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
        paddingLeft = left;
    }

    public Length getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(Length paddingTop) {
        this.paddingTop = paddingTop;
    }


    public Length getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(Length paddingBottom) {
        this.paddingBottom = paddingBottom;
    }


    public Length getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(Length paddingRight) {
        this.paddingRight = paddingRight;
    }


    public Length getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(Length paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setMargin(Float margin) {
        setMargin(PIXEL.length(margin));
    }

    public void setMargin(Unit margin) {
        marginLeft = marginRight = marginTop = marginBottom = margin;
    }

    public void setMargin(Float topBottom, Float leftRight) {
        setMargin(PIXEL.length(topBottom), PIXEL.length(leftRight));
    }

    public void setMargin(Unit topBottom, Unit leftRight) {
        marginLeft = marginRight = leftRight;
        marginTop = marginBottom = topBottom;
    }

    public void setMargin(Float top, Float right, Float bottom, Float left) {
        setMargin(PIXEL.length(top), PIXEL.length(right), PIXEL.length(bottom), PIXEL.length(left));
    }

    public void setMargin(Unit top, Unit right, Unit bottom, Unit left) {
        marginTop = top;
        marginRight = right;
        marginBottom = bottom;
        marginLeft = left;
    }

    public Unit getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(Unit marginTop) {
        this.marginTop = marginTop;
    }

    public Unit getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(Unit marginBottom) {
        this.marginBottom = marginBottom;
    }

    public Unit getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(Unit marginRight) {
        this.marginRight = marginRight;
    }

    public Unit getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(Unit marginLeft) {
        this.marginLeft = marginLeft;
    }

    /**
     * Returns top style.
     *
     * @return top style.
     */
    public Float getTop() {
        return top;
    }

    /**
     * Used tp set top style.
     *
     * @param top top style.
     */
    public void setTop(Float top) {
        this.top = top;
    }

    /**
     * Returns bottom style.
     *
     * @return bottom style.
     */
    public Float getBottom() {
        return bottom;
    }

    /**
     * Used tp set bottom style.
     *
     * @param bottom bottom style.
     */
    public void setBottom(Float bottom) {
        this.bottom = bottom;
    }

    /**
     * Returns right style.
     *
     * @return right style.
     */
    public Float getRight() {
        return right;
    }

    /**
     * Used tp set right style.
     *
     * @param right right style.
     */
    public void setRight(Float right) {
        this.right = right;
    }

    /**
     * Returns left style.
     *
     * @return left style.
     */
    public Float getLeft() {
        return left;
    }

    /**
     * Used tp set left style.
     *
     * @param left left style.
     */
    public void setLeft(Float left) {
        this.left = left;
    }

    /**
     * Returns display style.
     *
     * @return display style.
     */
    public DisplayType getDisplay() {
        return display;
    }

    /**
     * Used to set display type.
     *
     * @param display display to set.
     */
    public void setDisplay(DisplayType display) {
        if (display == null) {
            this.display = DisplayType.MANUAL;
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

    /**
     * Flex style object.
     *
     * @return flex style object.
     */
    public FlexStyle getFlexStyle() {
        return flexStyle;
    }

    /**
     * Returns position type or null.
     *
     * @return position type or null.
     */
    public PositionType getPosition() {
        return position;
    }

    /**
     * Used to set position style.
     *
     * @param position position type to set.
     */
    public void setPosition(PositionType position) {
        if (position != null) {
            this.position = position;
        }
    }

    /**
     * Used to set minimum width and height
     *
     * @param width  minimum width to set.
     * @param height minimum height to set.
     */
    public void setMinimumSize(float width, float height) {
        setMinWidth(width);
        setMinHeight(height);
    }

    /**
     * sed to set max width and height.
     *
     * @param width  max width to set.
     * @param height max height to set.
     */
    public void setMaximumSize(float width, float height) {
        setMaxWidth(width);
        setMaxHeight(height);
    }

    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

    /**
     * Css display type.
     */
    public enum DisplayType {
        /**
         * Flex display means that it is parent for flex child.
         */
        FLEX,
        /**
         * Manual display type.
         */
        MANUAL,
        /**
         * None display means that component with such style will not be rendered and used during laying out.
         */
        NONE
    }

    /**
     * Css position type.
     */
    public enum PositionType {
        RELATIVE,
        ABSOLUTE
    }

}
