package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public abstract class AbstractEvent {
    private final Component component;
    private final Context   context;

    public AbstractEvent(Component component, Context context) {
        this.component = component;
        this.context = context;
    }

    public Component getComponent() {
        return component;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("component", component)
                .toString();
    }
}
