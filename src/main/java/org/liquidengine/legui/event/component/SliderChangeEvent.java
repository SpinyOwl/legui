package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Component;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class SliderChangeEvent implements LeguiComponentEvent {

    private final Component component;
    private final float oldValue;
    private final float newValue;

    public SliderChangeEvent(Component component, float oldValue, float newValue) {
        this.component = component;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Component getComponent() {
        return component;
    }

    public float getNewValue() {
        return newValue;
    }

    public float getOldValue() {
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SliderChangeEvent)) return false;
        SliderChangeEvent that = (SliderChangeEvent) o;
        return Float.compare(that.oldValue, oldValue) == 0 &&
                Float.compare(that.newValue, newValue) == 0 &&
                Objects.equal(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(component, oldValue, newValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("component", component)
                .append("oldValue", oldValue)
                .append("newValue", newValue)
                .toString();
    }
}
