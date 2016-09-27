package org.liquidengine.legui.event.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.ProgressBar;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class ProgressBarChangeEvent {
    public final ProgressBar progressBar;
    public final float oldValue;
    public final float newValue;

    public ProgressBarChangeEvent(ProgressBar progressBar, float oldValue, float newValue) {
        this.progressBar = progressBar;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
