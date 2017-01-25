package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class LeguiSystemCharEvent implements LeguiSystemEvent {

    public final long window;
    public final int codepoint;

    public LeguiSystemCharEvent(long window, int codepoint) {
        this.window = window;
        this.codepoint = codepoint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("window", window)
                .append("codepoint", codepoint)
                .toString();
    }
}
