package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemWindowPosEvent implements SystemEvent {

    public final long window;
    public final int xpos;
    public final int ypos;

    public SystemWindowPosEvent(long window, int xpos, int ypos) {
        this.window = window;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("window", window)
            .append("xpos", xpos)
            .append("ypos", ypos)
            .toString();
    }

}
