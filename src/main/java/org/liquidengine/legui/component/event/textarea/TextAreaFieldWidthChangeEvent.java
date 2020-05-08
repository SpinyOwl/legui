package org.liquidengine.legui.component.event.textarea;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

import java.util.Objects;

public class TextAreaFieldWidthChangeEvent extends Event<TextAreaField> {
    private final float width;

    public TextAreaFieldWidthChangeEvent(TextAreaField targetComponent, Context context, Frame frame, float width) {
        super(targetComponent, context, frame);
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("width", width)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TextAreaFieldWidthChangeEvent that = (TextAreaFieldWidthChangeEvent) o;
        return Float.compare(that.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width);
    }
}
