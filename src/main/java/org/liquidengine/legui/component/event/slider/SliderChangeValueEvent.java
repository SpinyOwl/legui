package org.liquidengine.legui.component.event.slider;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * Slider value change event.
 *
 * @param <T> type of slider.
 */
public class SliderChangeValueEvent<T extends Slider> extends Event<T> {

    /**
     * Old slider value.
     */
    private final float oldValue;
    /**
     * New slider value.
     */
    private final float newValue;

    /**
     * Constructor. Used to create event.
     *
     * @param component slider component.
     * @param context legui context.
     * @param oldValue old slider value.
     * @param newValue new slider value.
     * @param frame frame.
     */
    public SliderChangeValueEvent(T component, Context context, Frame frame, float oldValue, float newValue) {
        super(component, context, frame);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns new slider value.
     *
     * @return new slider value.
     */
    public float getNewValue() {
        return newValue;
    }

    /**
     * Returns old slider value.
     *
     * @return old slider value.
     */
    public float getOldValue() {
        return oldValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("oldValue", oldValue)
            .append("newValue", newValue)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SliderChangeValueEvent<?> that = (SliderChangeValueEvent<?>) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(oldValue, that.oldValue)
            .append(newValue, that.newValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(oldValue)
            .append(newValue)
            .toHashCode();
    }
}
