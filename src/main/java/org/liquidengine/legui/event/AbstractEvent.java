package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public abstract class AbstractEvent {
    private final Component component;

    public AbstractEvent(Component component) {
        this.component = component;
    }

    public final Component getComponent() {
        return component;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("component", component)
                .toString();
    }
}
