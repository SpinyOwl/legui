package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEvent<T extends Component> extends Event<T> {

    private final double xoffset;
    private final double yoffset;

    public ScrollEvent(T component, Context context, Frame frame, double xoffset, double yoffset) {
        super(component, context, frame);
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
