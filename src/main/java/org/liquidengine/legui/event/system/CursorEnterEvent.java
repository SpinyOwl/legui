package org.liquidengine.legui.event.system;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class CursorEnterEvent implements LeguiSystemEvent {
    public final long window;
    public final boolean entered;

    public CursorEnterEvent(long window, boolean entered) {
        this.window = window;
        this.entered = entered;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("window", window)
                .append("entered", entered)
                .toString();
    }
}
