package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.ProgressBar;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class ProgressBarChangeEvent extends AbstractComponentEvent {
    private final float oldValue;
    private final float newValue;

    public ProgressBarChangeEvent(ProgressBar progressBar, float oldValue, float newValue) {
        super(progressBar);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public float getOldValue() {
        return oldValue;
    }

    public float getNewValue() {
        return newValue;
    }

    public ProgressBar getProgressBar() {
        return (ProgressBar) getComponent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgressBarChangeEvent)) return false;
        if (!super.equals(o)) return false;
        ProgressBarChangeEvent that = (ProgressBarChangeEvent) o;
        return Float.compare(that.oldValue, oldValue) == 0 &&
                Float.compare(that.newValue, newValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), oldValue, newValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("oldValue", oldValue)
                .append("newValue", newValue)
                .toString();
    }
}
