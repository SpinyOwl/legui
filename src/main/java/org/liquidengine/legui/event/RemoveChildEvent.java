package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;

import java.util.Objects;

public class RemoveChildEvent<T extends Component> extends Event<T> {
    private final Component removed;

    public RemoveChildEvent(T targetComponent, Component removed) {
        super(targetComponent, null, targetComponent.getFrame());
        this.removed = removed;
    }

    public Component getRemoved() {
        return removed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("added", removed)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RemoveChildEvent<?> that = (RemoveChildEvent<?>) o;
        return Objects.equals(removed, that.removed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), removed);
    }
}
