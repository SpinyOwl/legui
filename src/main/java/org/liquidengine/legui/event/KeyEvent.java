package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 2/13/2017.
 */
public class KeyEvent<T extends Component> extends Event<T> {

    private final int action;
    private final int key;
    private final int mods;
    private final int scancode;

    public KeyEvent(T component, Context context, Frame frame, int action, int key, int mods, int scancode) {
        super(component, context, frame);
        this.action = action;
        this.key = key;
        this.mods = mods;
        this.scancode = scancode;
    }

    public int getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }

    public int getMods() {
        return mods;
    }

    public int getScancode() {
        return scancode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("action", action)
            .append("key", key)
            .append("mods", mods)
            .append("scancode", scancode)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        KeyEvent<?> keyEvent = (KeyEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(action, keyEvent.action)
            .append(key, keyEvent.key)
            .append(mods, keyEvent.mods)
            .append(scancode, keyEvent.scancode)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(action)
            .append(key)
            .append(mods)
            .append(scancode)
            .toHashCode();
    }
}
