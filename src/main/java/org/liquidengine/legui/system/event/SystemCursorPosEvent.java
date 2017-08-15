package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemCursorPosEvent implements SystemEvent {

    public final long window;
    public final double xpos;
    public final double ypos;
    public final float fx;
    public final float fy;

    public SystemCursorPosEvent(long window, double xpos, double ypos) {
        this.window = window;
        this.xpos = xpos;
        this.ypos = ypos;
        fx = (float) xpos;
        fy = (float) ypos;
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
