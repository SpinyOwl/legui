package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ProgressBar;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class ProgressBarChangeEvent implements LeguiComponentEvent {
    private final ProgressBar progressBar;
    private final float oldValue;
    private final float newValue;

    public ProgressBarChangeEvent(ProgressBar progressBar, float oldValue, float newValue) {
        this.progressBar = progressBar;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public float getOldValue() {
        return oldValue;
    }

    public float getNewValue() {
        return newValue;
    }

    public Component getComponent() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgressBarChangeEvent)) return false;
        ProgressBarChangeEvent that = (ProgressBarChangeEvent) o;
        return Float.compare(that.oldValue, oldValue) == 0 &&
                Float.compare(that.newValue, newValue) == 0 &&
                Objects.equal(progressBar, that.progressBar);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(progressBar, oldValue, newValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("progressBar", progressBar)
                .append("oldValue", oldValue)
                .append("newValue", newValue)
                .toString();
    }
}
