package org.liquidengine.legui.style.border;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector4f;
import org.liquidengine.legui.style.Border;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Simple one line rectangle border with specified thickness and color.
 */
public class SimpleLineBorder extends Border {

    /**
     * Thickness of border.
     */
    private float thickness;
    /**
     * Border color.
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
     * @param color color.
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("thickness", thickness)
            .append("color", color)
            .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(thickness)
            .append(color)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SimpleLineBorder that = (SimpleLineBorder) obj;

        return new EqualsBuilder()
            .append(thickness, that.thickness)
            .append(color, that.color)
            .isEquals();
    }
}
