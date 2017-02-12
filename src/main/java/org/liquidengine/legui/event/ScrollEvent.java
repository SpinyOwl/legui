package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEvent extends AbstractEvent {
    private final double xoffset;
    private final double yoffset;

    public ScrollEvent(Component controller, Frame frame, double xoffset, double yoffset) {
        super(controller, frame);
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
