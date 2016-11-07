package org.liquidengine.legui.component.border;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector4f;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class SimpleRectangleLineBorder extends Border {
    private Vector4f borderColor;
    private float thickness;

    public SimpleRectangleLineBorder(Vector4f borderColor, float thickness) {
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    public SimpleRectangleLineBorder() {
        this(ColorConstants.black(), 1);
    }

    public Vector4f getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Vector4f borderColor) {
        this.borderColor = borderColor;
    }


    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SimpleRectangleLineBorder that = (SimpleRectangleLineBorder) o;

        return new EqualsBuilder()
                .append(thickness, that.thickness)
                .append(borderColor, that.borderColor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(borderColor)
                .append(thickness)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("borderColor", borderColor)
                .append("thickness", thickness)
                .toString();
    }
}
