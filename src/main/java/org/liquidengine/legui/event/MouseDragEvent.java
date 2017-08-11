package org.liquidengine.legui.event;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
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
}
