package org.liquidengine.legui.event.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.event.LeguiEvent;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public class SliderChangeEvent extends LeguiEvent {
    private final float oldValue;
    private final float newValue;

    public SliderChangeEvent(Slider slider, float oldValue, float newValue) {
        super(slider);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public float getOldValue() {
        return oldValue;
    }

    public float getNewValue() {
        return newValue;
    }

    public Slider getSlider() {
        return (Slider) getComponent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SliderChangeEvent)) return false;
        if (!super.equals(o)) return false;
        SliderChangeEvent that = (SliderChangeEvent) o;
        return Float.compare(that.oldValue, oldValue) == 0 &&
                Float.compare(that.newValue, newValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), oldValue, newValue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("oldValue", oldValue)
                .append("newValue", newValue)
                .toString();
    }
}
