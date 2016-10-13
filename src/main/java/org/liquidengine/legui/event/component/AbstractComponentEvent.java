package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public abstract class AbstractComponentEvent {
    private final Component component;

    protected AbstractComponentEvent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractComponentEvent)) return false;
        AbstractComponentEvent that = (AbstractComponentEvent) o;
        return Objects.equal(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(component);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("component", component)
                .toString();
    }
}
