package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector4f;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.listener.component.ProgressBarChangeEventListener;
import org.liquidengine.legui.util.ColorConstants;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class ProgressBar extends Component {
    public static final float MAX_VALUE = 100f;
    public static final float MIN_VALUE = 0f;

    private float value;

    private Vector4f progressColor = ColorConstants.green();
    private List<ProgressBarChangeEventListener> progressBarUpdateListeners = new CopyOnWriteArrayList<>();

    public ProgressBar() {
        initialize();
    }

    public ProgressBar(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    private void initialize() {
        border = new SimpleLineBorder(this, ColorConstants.darkGray(), 1);
        backgroundColor = ColorConstants.white();
        cornerRadius = 2;
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

    public boolean addProgressBarUpdateListener(ProgressBarChangeEventListener progressBarUpdateListener) {
        return progressBarUpdateListeners.add(progressBarUpdateListener);
    }

    public boolean removeProgressBarUpdateListener(ProgressBarChangeEventListener o) {
        return progressBarUpdateListeners.remove(o);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
