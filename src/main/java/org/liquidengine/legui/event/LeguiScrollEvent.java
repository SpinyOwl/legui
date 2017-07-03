package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class LeguiScrollEvent<T extends Component> extends LeguiEvent<T> {
    private final double xoffset;
    private final double yoffset;

    public LeguiScrollEvent(T component, LeguiContext context, double xoffset, double yoffset) {
        super(component, context);
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }
}
