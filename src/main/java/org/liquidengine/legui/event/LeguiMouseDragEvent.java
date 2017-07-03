package org.liquidengine.legui.event;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class LeguiMouseDragEvent<T extends Component> extends LeguiEvent<T> {

    private final Vector2f delta;

    public LeguiMouseDragEvent(T component, LeguiContext context, Vector2f delta) {
        super(component, context);
        this.delta = delta;
    }

    public Vector2f getDelta() {
        return delta;
    }
}
