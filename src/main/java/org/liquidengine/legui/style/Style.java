package org.liquidengine.legui.style;

import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.style.length.Unit;
import org.liquidengine.legui.style.shadow.Shadow;

import static org.liquidengine.legui.style.length.LengthType.pixel;

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

    private Length borderTopLeftRadius;
    private Length borderTopRightRadius;
    private Length borderBottomRightRadius;
    private Length borderBottomLeftRadius;

    private Length width;
    private Length height;

    private Length minWidth;
    private Length minHeight;

    private Length maxWidth;
    private Length maxHeight;

    private Length paddingTop;
    private Length paddingBottom;
    private Length paddingRight;
    private Length paddingLeft;

    private Unit marginTop;
    private Unit marginBottom;
    private Unit marginRight;
    private Unit marginLeft;

    private Length top;
    private Length bottom;
    private Length right;
    private Length left;

    private Shadow shadow;

    /**
     * Stroke color. Used to render stroke if component is focused.
     */
    private Vector4f focusedStrokeColor = ColorConstants.lightBlue();

    // TEXT RELATED STYLE PROPERTIES.

    private String font = FontRegistry.DEFAULT;
    private Float fontSize = 16f;
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
     * Used to set border radius for all four corners. (PIXEL VERSION)
     *
     * @param radius radius to set. Sets border radius to all corners.
     */
    public void setBorderRadius(float radius) {
        setBorderRadius(pixel(radius));
    }


    /**
     * Used to set border radius for all four corners.
     *
     * @param radius radius to set. Sets border radius to all corners.
     */
    public void setBorderRadius(Length radius) {
        borderTopLeftRadius = borderTopRightRadius =
                borderBottomRightRadius = borderBottomLeftRadius = radius;
    }

    /**
     * Used to set border radius. (PIXEL VERSION)
     *
     * @param topLeftBottomRight top left and bottom right radius.
     * @param topRightBottomLeft top right and bottom left radius.
     */
    public void setBorderRadius(float topLeftBottomRight, float topRightBottomLeft) {
        setBorderRadius(pixel(topLeftBottomRight), pixel(topRightBottomLeft));
    }

    /**
     * Used to set border radius.
     *
     * @param topLeftBottomRight top left and bottom right radius.
     * @param topRightBottomLeft top right and bottom left radius.
     */
    public void setBorderRadius(Length topLeftBottomRight, Length topRightBottomLeft) {
        borderTopLeftRadius = borderBottomRightRadius = topLeftBottomRight;
        borderTopRightRadius = borderBottomLeftRadius = topRightBottomLeft;
    }

    /**
     * Used to set border radius. (PIXEL VERSION)
     *
     * @param topLeft            top left radius.
     * @param bottomRight        bottom right radius.
     * @param topRightBottomLeft top right and bottom left radius.
     */
    public void setBorderRadius(float topLeft, float topRightBottomLeft, float bottomRight) {
        setBorderRadius(pixel(topLeft), pixel(topRightBottomLeft), pixel(bottomRight));
    }

    /**
     * Used to set border radius.
     *
     * @param topLeft            top left radius.
     * @param bottomRight        bottom right radius.
     * @param topRightBottomLeft top right and bottom left radius.
     */
    public void setBorderRadius(Length topLeft, Length topRightBottomLeft, Length bottomRight) {
        borderTopLeftRadius = topLeft;
        borderTopRightRadius = borderBottomLeftRadius = topRightBottomLeft;
        borderBottomRightRadius = bottomRight;
    }

    /**
     * Used to set border radius. (PIXEL VERSION)
     *
     * @param topLeft     top left radius.
     * @param topRight    top right radius.
     * @param bottomRight bottom right radius.
     * @param bottomLeft  bottom left radius.
     */
    public void setBorderRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        setBorderRadius(pixel(topLeft), pixel(topRight), pixel(bottomRight), pixel(bottomLeft));
    }

    /**
     * Used to set border radius.
     *
     * @param topLeft     top left radius.
     * @param topRight    top right radius.
     * @param bottomRight bottom right radius.
     * @param bottomLeft  bottom left radius.
     */
    public void setBorderRadius(Length topLeft, Length topRight, Length bottomRight, Length bottomLeft) {
        borderTopLeftRadius = topLeft;
        borderTopRightRadius = topRight;
        borderBottomRightRadius = bottomRight;
        borderBottomLeftRadius = bottomLeft;
    }

    /**
     * Returns top left border radius.
     *
     * @return top left border radius.
     */
    public Length getBorderTopLeftRadius() {
        return borderTopLeftRadius;
    }

    /**
     * Used to set top left border radius. (PIXEL VERSION)
     *
     * @param borderTopLeftRadius top left border radius.
     */
    public void setBorderTopLeftRadius(float borderTopLeftRadius) {
        setBorderTopLeftRadius(pixel(borderTopLeftRadius));
    }

    /**
     * Used to set top left border radius.
     *
     * @param borderTopLeftRadius top left border radius.
     */
    public void setBorderTopLeftRadius(Length borderTopLeftRadius) {
        this.borderTopLeftRadius = borderTopLeftRadius;
    }

    /**
     * Returns top right border radius.
     *
     * @return top right border radius.
     */
    public Length getBorderTopRightRadius() {
        return borderTopRightRadius;
    }

    /**
     * Used to set top right border radius. (PIXEL VERSION)
     *
     * @param borderTopRightRadius top right border radius.
     */
    public void setBorderTopRightRadius(float borderTopRightRadius) {
        setBorderTopRightRadius(pixel(borderTopRightRadius));
    }

    /**
     * Used to set top right border radius.
     *
     * @param borderTopRightRadius top right border radius.
     */
    public void setBorderTopRightRadius(Length borderTopRightRadius) {
        this.borderTopRightRadius = borderTopRightRadius;
    }

    /**
     * Returns bottom right border radius.
     *
     * @return bottom right border radius.
     */
    public Length getBorderBottomRightRadius() {
        return borderBottomRightRadius;
    }

    /**
     * Used to set bottom right border radius. (PIXEL VERSION)
     *
     * @param borderBottomRightRadius bottom right border radius.
     */
    public void setBorderBottomRightRadius(float borderBottomRightRadius) {
        setBorderBottomRightRadius(pixel(borderBottomRightRadius));
    }

    /**
     * Used to set bottom right border radius.
     *
     * @param borderBottomRightRadius bottom right border radius.
     */
    public void setBorderBottomRightRadius(Length borderBottomRightRadius) {
        this.borderBottomRightRadius = borderBottomRightRadius;
    }

    /**
     * Returns bottom left border radius.
     *
     * @return bottom left border radius.
     */
    public Length getBorderBottomLeftRadius() {
        return borderBottomLeftRadius;
    }

    /**
     * Used to set bottom left border radius. (PIXEL VERSION)
     *
     * @param borderBottomLeftRadius bottom left border radius.
     */
    public void setBorderBottomLeftRadius(float borderBottomLeftRadius) {
        setBorderBottomLeftRadius(pixel(borderBottomLeftRadius));
    }

    /**
     * Used to set bottom left border radius.
     *
     * @param borderBottomLeftRadius bottom left border radius.
     */
    public void setBorderBottomLeftRadius(Length borderBottomLeftRadius) {
        this.borderBottomLeftRadius = borderBottomLeftRadius;
    }

    /**
     * Returns width.
     *
     * @return width.
     */
    public Length getWidth() {
        return width;
    }

    /**
     * Used to set width. (PIXEL VERSION)
     *
     * @param width width to set.
     */
    public void setWidth(float width) {
        setWidth(pixel(width));
    }

    /**
     * Used to set width.
     *
     * @param width width to set.
     */
    public void setWidth(Length width) {
        this.width = width;
    }

    /**
     * Used to set height.
     *
     * @return height to set.
     */
    public Length getHeight() {
        return height;
    }

    public void setHeight(float height) {
        setHeight(pixel(height));
    }

    public void setHeight(Length height) {
        this.height = height;
    }

    public Length getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(float minWidth) {
        setMinWidth(pixel(minWidth));
    }

    public void setMinWidth(Length minWidth) {
        this.minWidth = minWidth;
    }

    public Length getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(float minHeight) {
        setMinHeight(pixel(minHeight));
    }

    public void setMinHeight(Length minHeight) {
        this.minHeight = minHeight;
    }

    public Length getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(float maxWidth) {
        setMaxWidth(pixel(maxWidth));
    }

    public void setMaxWidth(Length maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Length getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(float maxHeight) {
        setMaxHeight(pixel(maxHeight));
    }

    public void setMaxHeight(Length maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setPadding(float padding) {
        this.setPadding(pixel(padding));
    }

    public void setPadding(Length padding) {
        paddingLeft = paddingRight =
                paddingTop = paddingBottom = padding;
    }

    public void setPadding(float topBottom, float leftRight) {
        this.setPadding(pixel(topBottom), pixel(leftRight));
    }

    public void setPadding(Length topBottom, Length leftRight) {
        paddingLeft = paddingRight = leftRight;
        paddingTop = paddingBottom = topBottom;
    }

    public void setPadding(float top, float right, float bottom, float left) {
        this.setPadding(pixel(top), pixel(right), pixel(bottom), pixel(left));
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

    public void setMargin(float margin) {
        setMargin(pixel(margin));
    }

    public void setMargin(Unit margin) {
        marginLeft = marginRight = marginTop = marginBottom = margin;
    }

    public void setMargin(float topBottom, float leftRight) {
        setMargin(pixel(topBottom), pixel(leftRight));
    }

    public void setMargin(Unit topBottom, Unit leftRight) {
        marginLeft = marginRight = leftRight;
        marginTop = marginBottom = topBottom;
    }

    public void setMargin(float top, float right, float bottom, float left) {
        setMargin(pixel(top), pixel(right), pixel(bottom), pixel(left));
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

    public void setMarginTop(Float marginTop) {
        setMarginTop(pixel(marginTop));
    }

    public Unit getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(Unit marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMarginBottom(Float marginBottom) {
        setMarginBottom(pixel(marginBottom));
    }

    public Unit getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(Unit marginRight) {
        this.marginRight = marginRight;
    }

    public void setMarginRight(Float marginRight) {
        setMarginRight(pixel(marginRight));
    }

    public Unit getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(Unit marginLeft) {
        this.marginLeft = marginLeft;
    }

    public void setMarginLeft(Float marginLeft) {
        setMarginLeft(pixel(marginLeft));
    }

    /**
     * Returns top style.
     *
     * @return top style.
     */
    public Length getTop() {
        return top;
    }

    /**
     * Used tp set top style. (PIXEL VERSION)
     *
     * @param top top style.
     */
    public void setTop(float top) {
        setTop(pixel(top));
    }

    /**
     * Used tp set top style.
     *
     * @param top top style.
     */
    public void setTop(Length top) {
        this.top = top;
    }

    /**
     * Returns bottom style.
     *
     * @return bottom style.
     */
    public Length getBottom() {
        return bottom;
    }

    /**
     * Used tp set bottom style. (PIXEL VERSION)
     *
     * @param bottom bottom style.
     */
    public void setBottom(float bottom) {
        setBottom(pixel(bottom));
    }

    /**
     * Used tp set bottom style.
     *
     * @param bottom bottom style.
     */
    public void setBottom(Length bottom) {
        this.bottom = bottom;
    }

    /**
     * Returns right style.
     *
     * @return right style.
     */
    public Length getRight() {
        return right;
    }

    /**
     * Used tp set right style. (PIXEL VERSION)
     *
     * @param right right style.
     */
    public void setRight(float right) {
        setRight(pixel(right));
    }

    /**
     * Used tp set right style.
     *
     * @param right right style.
     */
    public void setRight(Length right) {
        this.right = right;
    }

    /**
     * Returns left style.
     *
     * @return left style.
     */
    public Length getLeft() {
        return left;
    }

    /**
     * Used tp set left style. (PIXEL VERSION)
     *
     * @param left left style.
     */
    public void setLeft(float left) {
        setLeft(pixel(left));
    }

    /**
     * Used tp set left style.
     *
     * @param left left style.
     */
    public void setLeft(Length left) {
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
    public String getFont() {
        return font;
    }

    /**
     * Sets font.
     *
     * @param font the font
     */
    public void setFont(String font) {
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
     * Used to set max width and height.
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

    public Float getFontSize() {
        return fontSize;
    }

    public void setFontSize(Float fontSize) {
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
