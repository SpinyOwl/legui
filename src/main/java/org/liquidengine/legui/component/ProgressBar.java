package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;

/**
 * Progress bar. Used to display for example progress of loading or something else.
 */
public class ProgressBar extends Controller {
    public static final float MAX_VALUE = 100f;
    public static final float MIN_VALUE = 0f;

    /**
     * Used to display current status.
     */
    private float value;

    /**
     * Color of progress bar.
     */
    private Vector4f progressColor = ColorConstants.green();

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public ProgressBar() {
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public ProgressBar(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public ProgressBar(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    private void initialize() {
    }

    /**
     * Returns current progress status.
     *
     * @return current progress status.
     */
    public float getValue() {
        return value;
    }

    /**
     * Used to set current progress status.
     *
     * @param value current progress status.
     */
    public void setValue(float value) {
        this.value = value < MIN_VALUE ? MIN_VALUE : value > MAX_VALUE ? MAX_VALUE : value;
    }

    /**
     * Returns progress bar color which used to draw completed progress.
     *
     * @return progress bar color which used to draw completed progress.
     */
    public Vector4f getProgressColor() {
        return progressColor;
    }

    /**
     * Used to set progress bar color.
     *
     * @param progressColor progress bar color.
     */
    public void setProgressColor(Vector4f progressColor) {
        this.progressColor = progressColor;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
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

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .append(progressColor)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", value)
                .append("progressColor", progressColor)
                .toString();
    }
}
