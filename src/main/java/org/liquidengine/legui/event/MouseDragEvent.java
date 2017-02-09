package org.liquidengine.legui.event;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Controller;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class MouseDragEvent extends AbstractEvent {

    private final Vector2f delta;

    public MouseDragEvent(Controller controller, Vector2f delta) {
        super(controller);
        this.delta = delta;
    }

    public Vector2f getDelta() {
        return delta;
    }
}
