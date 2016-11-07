package org.liquidengine.legui.event.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.LeguiEvent;

/**
 * Created by Alexander on 07.11.2016.
 */
public class FocusEvent extends LeguiEvent {
    public final boolean focusGained;
    public final Component focusTarget;

    public FocusEvent(Component component, boolean focusGained, Component focusTarget) {
        super(component);
        this.focusGained = focusGained;
        this.focusTarget = focusTarget;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("focusGained", focusGained)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FocusEvent that = (FocusEvent) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(focusGained, that.focusGained)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(focusGained)
                .toHashCode();
    }
}
