package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemWindowFocusEvent implements LeguiSystemEvent {
    public final long window;
    public final boolean focused;

    public SystemWindowFocusEvent(long window, boolean focused) {
        this.window = window;
        this.focused = focused;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("window", window)
                .append("focused", focused)
                .toString();
    }

}
