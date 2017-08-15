package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemCursorEnterEvent implements SystemEvent {

    public final long window;
    public final boolean entered;

    public SystemCursorEnterEvent(long window, boolean entered) {
        this.window = window;
        this.entered = entered;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("window", window)
            .append("entered", entered)
            .toString();
    }
}
