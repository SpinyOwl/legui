package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 2/9/2017.
 */
public class MouseDragEvent<T extends Component> extends Event<T> {

    private final Vector2f delta;

    public MouseDragEvent(T component, Context context, Frame frame, Vector2f delta) {
        super(component, context, frame);
        this.delta = delta;
    }

    public Vector2f getDelta() {
        return delta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("targetComponent", getTargetComponent().getClass().getSimpleName())
            .append("delta", delta)
            .toString();
    }
}
