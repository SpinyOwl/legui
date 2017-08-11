package org.liquidengine.legui.component.misc.event.slider;

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
     * @param context   legui context.
     * @param oldValue  old slider value.
     * @param newValue  new slider value.
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
}
