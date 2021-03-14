package org.liquidengine.legui.component.event.tooltip;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

import java.util.Objects;

public class TooltipTextSizeChangeEvent extends Event<Tooltip> {
    private final float width;
    private final float height;

    public TooltipTextSizeChangeEvent(Tooltip targetComponent, Context context, Frame frame, float width, float height) {
        super(targetComponent, context, frame);
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("width", width)
            .append("height", height)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TooltipTextSizeChangeEvent that = (TooltipTextSizeChangeEvent) o;
        return Float.compare(that.width, width) == 0 && Float.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height);
    }
}
