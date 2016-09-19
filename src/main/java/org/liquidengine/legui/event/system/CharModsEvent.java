package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class CharModsEvent implements LeguiSystemEvent {
    public final long window;
    public final int codepoint;
    public final int mods;

    public CharModsEvent(long window, int codepoint, int mods) {
        this.window = window;
        this.codepoint = codepoint;
        this.mods = mods;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("window", window)
                .append("codepoint", codepoint)
                .append("mods", mods)
                .toString();
    }
}
