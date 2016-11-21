package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 11/17/2016.
 */
public class ComponentState {
    protected transient boolean focused = false;
    protected transient boolean pressed = false;
    protected transient boolean hovered = false;

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ComponentState)) return false;

        ComponentState that = (ComponentState) o;

        return new EqualsBuilder()
                .append(focused, that.focused)
                .append(pressed, that.pressed)
                .append(hovered, that.hovered)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(focused)
                .append(pressed)
                .append(hovered)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("focused", focused)
                .append("pressed", pressed)
                .append("hovered", hovered)
                .toString();
    }
}
