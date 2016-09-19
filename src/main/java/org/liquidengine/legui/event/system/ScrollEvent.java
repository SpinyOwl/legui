package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class ScrollEvent implements LeguiSystemEvent {
    public final long window;
    public final double xoffset;
    public final double yoffset;

    public ScrollEvent(long window, double xoffset, double yoffset) {
        this.window = window;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("window", window)
                .append("xoffset", xoffset)
                .append("yoffset", yoffset)
                .toString();
    }
}
