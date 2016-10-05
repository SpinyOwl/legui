package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemCursorPosEvent implements LeguiSystemEvent {
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
        return new ToStringBuilder(this)
                .append("window", window)
                .append("xpos", xpos)
                .append("ypos", ypos)
                .toString();
    }

}
