package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemWindowFocusEvent implements SystemEvent {

    public final long window;
    public final boolean focused;

    public SystemWindowFocusEvent(long window, boolean focused) {
        this.window = window;
        this.focused = focused;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("window", window)
            .append("focused", focused)
            .toString();
    }

}
