package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class MouseClickEvent implements LeguiSystemEvent {
    public final long window;
    public final int button;
    public final int action;
    public final int mods;

    public MouseClickEvent(long window, int button, int action, int mods) {
        this.window = window;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("window", window)
                .append("button", button)
                .append("action", action)
                .append("mods", mods)
                .toString();
    }
}
