package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class ProgressBar extends Controller {
    public static final float MAX_VALUE = 100f;
    public static final float MIN_VALUE = 0f;

    private float value;

    private Vector4f progressColor = ColorConstants.green();

    public ProgressBar() {
        initialize();
    }

    public ProgressBar(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    private void initialize() {
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value < MIN_VALUE ? MIN_VALUE : value > MAX_VALUE ? MAX_VALUE : value;
    }

    public Vector4f getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(Vector4f progressColor) {
        this.progressColor = progressColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProgressBar that = (ProgressBar) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(value, that.value)
                .append(progressColor, that.progressColor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .append(progressColor)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("value", value)
                .append("progressColor", progressColor)
                .toString();
    }
}
