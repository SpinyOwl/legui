package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemKeyEvent implements SystemEvent {

    public final long window;
    public final int key;
    public final int scancode;
    public final int action;
    public final int mods;

    public SystemKeyEvent(long window, int key, int scancode, int action, int mods) {
        this.window = window;
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("window", window)
            .append("key", key)
            .append("scancode", scancode)
            .append("action", action)
            .append("mods", mods)
            .toString();
    }
}
