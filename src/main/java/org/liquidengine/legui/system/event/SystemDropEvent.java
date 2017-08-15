package org.liquidengine.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.lwjgl.PointerBuffer;

/**
 * Created by Shcherbin Alexander on 6/10/2016.
 */
public class SystemDropEvent implements SystemEvent {

    public final long window;
    public final int count;
    public final long names;
    public final String[] strings;

    public SystemDropEvent(long window, int count, long names) {
        this.window = window;
        this.count = count;
        this.names = names;
        PointerBuffer pb = PointerBuffer.create(names, count);
        strings = new String[count];
        for (int i = 0; i < count; i++) {
            strings[i] = pb.getStringUTF8(i);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
