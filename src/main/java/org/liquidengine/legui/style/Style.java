package org.liquidengine.legui.style;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.font.Font;

/**
 * The type Style.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class Style {

    private Background background;
    private Border border;
    private Font font;

    private float topLeftCornerRadius;
    private float topRightCornerRadius;
    private float bottomLeftCornerRadius;
    private float bottomRightCornerRadius;

    private Vector4f padding;
    private Vector4f margin;

    private Vector2f minSize;
    private Vector2f maxSize;
    private Vector2f size;

    /**
     * Stroke color. Used to render stroke if component is focused.
     */
    private Vector4f focusedStrokeColor = ColorConstants.lightBlue();

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
        this.background = background;
    }

    /**
     * Sets corner radius.
     *
     * @param radius the radius
     */
    public void setCornerRadius(float radius) {
        topLeftCornerRadius = topRightCornerRadius =
            bottomLeftCornerRadius = bottomRightCornerRadius = radius;
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
     * Gets top left corner radius.
     *
     * @return the top left corner radius
     */
    public float getTopLeftCornerRadius() {
        return topLeftCornerRadius;
    }

    /**
     * Sets top left corner radius.
     *
     * @param topLeftCornerRadius the top left corner radius
     */
    public void setTopLeftCornerRadius(float topLeftCornerRadius) {
        this.topLeftCornerRadius = topLeftCornerRadius;
    }

    /**
     * Gets top right corner radius.
     *
     * @return the top right corner radius
     */
    public float getTopRightCornerRadius() {
        return topRightCornerRadius;
    }

    /**
     * Sets top right corner radius.
     *
     * @param topRightCornerRadius the top right corner radius
     */
    public void setTopRightCornerRadius(float topRightCornerRadius) {
        this.topRightCornerRadius = topRightCornerRadius;
    }

    /**
     * Gets bottom left corner radius.
     *
     * @return the bottom left corner radius
     */
    public float getBottomLeftCornerRadius() {
        return bottomLeftCornerRadius;
    }

    /**
     * Sets bottom left corner radius.
     *
     * @param bottomLeftCornerRadius the bottom left corner radius
     */
    public void setBottomLeftCornerRadius(float bottomLeftCornerRadius) {
        this.bottomLeftCornerRadius = bottomLeftCornerRadius;
    }

    /**
     * Gets bottom right corner radius.
     *
     * @return the bottom right corner radius
     */
    public float getBottomRightCornerRadius() {
        return bottomRightCornerRadius;
    }

    /**
     * Sets bottom right corner radius.
     *
     * @param bottomRightCornerRadius the bottom right corner radius
     */
    public void setBottomRightCornerRadius(float bottomRightCornerRadius) {
        this.bottomRightCornerRadius = bottomRightCornerRadius;
    }

    /**
     * Gets padding.
     *
     * @return the padding
     */
    public Vector4f getPadding() {
        return padding;
    }

    /**
     * Sets padding.
     *
     * @param padding the padding
     */
    public void setPadding(Vector4f padding) {
        this.padding = padding;
    }

    /**
     * Gets margin.
     *
     * @return the margin
     */
    public Vector4f getMargin() {
        return margin;
    }

    /**
     * Sets margin.
     *
     * @param margin the margin
     */
    public void setMargin(Vector4f margin) {
        this.margin = margin;
    }

    /**
     * Gets min size.
     *
     * @return the min size
     */
    public Vector2f getMinSize() {
        return minSize;
    }

    /**
     * Sets min size.
     *
     * @param minSize the min size
     */
    public void setMinSize(Vector2f minSize) {
        this.minSize = minSize;
    }

    /**
     * Gets max size.
     *
     * @return the max size
     */
    public Vector2f getMaxSize() {
        return maxSize;
    }

    /**
     * Sets max size.
     *
     * @param maxSize the max size
     */
    public void setMaxSize(Vector2f maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Vector2f size) {
        this.size = size;
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

}
