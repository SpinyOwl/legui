package org.liquidengine.legui.component.event.textarea;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * @author ShchAlexander.
 */
public class TextAreaFieldContentChangeEvent<T extends TextAreaField> extends Event<T> {

    /**
     * Old value.
     */
    private final String oldValue;
    /**
     * New value.
     */
    private final String newValue;

    public TextAreaFieldContentChangeEvent(T component, Context context, Frame frame, String oldValue, String newValue) {
        super(component, context, frame);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns old value.
     *
     * @return old value.
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Returns new value.
     *
     * @return new value.
     */
    public String getNewValue() {
        return newValue;
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

        TextAreaFieldContentChangeEvent<?> that = (TextAreaFieldContentChangeEvent<?>) o;

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
