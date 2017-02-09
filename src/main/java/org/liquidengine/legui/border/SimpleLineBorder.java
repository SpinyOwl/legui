package org.liquidengine.legui.border;

import org.joml.Vector4f;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Simple one line rectangle border with specified thickness and color.
 */
public class SimpleLineBorder extends Border {
    /**
     * Thickness of border.
     */
    private float    thickness;
    /**
     * Border color
     */
    private Vector4f color;

    /**
     * Creates transparent border with 0 thickness.
     */
    public SimpleLineBorder() {
        thickness = 0;
        color = ColorConstants.transparent();
    }

    /**
     * Creates border with specified color and thickness.
     *
     * @param color     color.
     * @param thickness thickness.
     */
    public SimpleLineBorder(Vector4f color, float thickness) {
        this.thickness = thickness;
        this.color = color;
    }

    /**
     * Returns border thickness.
     *
     * @return border thickness.
     */
    public float getThickness() {
        return thickness;
    }

    /**
     * Used to set border thickness.
     *
     * @param thickness border thickness to set.
     */
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    /**
     * Returns border color.
     *
     * @return border color.
     */
    public Vector4f getColor() {
        return color;
    }

    /**
     * Used to set border color.
     *
     * @param color border color to set.
     */
    public void setColor(Vector4f color) {
        this.color = color;
    }
}
