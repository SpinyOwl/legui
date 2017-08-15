package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemCharModsEvent implements SystemEvent {

    public final long window;
    public final int codepoint;
    public final int mods;

    public SystemCharModsEvent(long window, int codepoint, int mods) {
        this.window = window;
        this.codepoint = codepoint;
        this.mods = mods;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("window", window)
            .append("codepoint", codepoint)
            .append("mods", mods)
            .toString();
    }
}
