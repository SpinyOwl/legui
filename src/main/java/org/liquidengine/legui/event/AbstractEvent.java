package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public abstract class AbstractEvent {
    private final Component component;
    private final Frame     frame;

    public AbstractEvent(Component component, Frame frame) {
        this.component = component;
        this.frame = frame;
    }

    public final Component getComponent() {
        return component;
    }

    public Frame getFrame() {
        return frame;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("component", component)
                .append("frame", frame)
                .toString();
    }
}
