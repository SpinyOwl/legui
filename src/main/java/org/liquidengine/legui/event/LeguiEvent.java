package org.liquidengine.legui.event;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public abstract class LeguiEvent implements Serializable {
    private final Component component;

    protected LeguiEvent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeguiEvent)) return false;
        LeguiEvent that = (LeguiEvent) o;
        return Objects.equal(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(component);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("component", component)
                .toString();
    }
}
