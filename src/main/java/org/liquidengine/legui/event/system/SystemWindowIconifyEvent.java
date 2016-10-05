package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemWindowIconifyEvent implements LeguiSystemEvent {
    public final long window;
    public final boolean iconified;

    public SystemWindowIconifyEvent(long window, boolean iconified) {
        this.window = window;
        this.iconified = iconified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("iconified", iconified)
                .append("window", window)
                .toString();
    }

}
