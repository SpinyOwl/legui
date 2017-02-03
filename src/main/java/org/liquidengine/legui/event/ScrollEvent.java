package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEvent implements Event {
    private final Component component;
    private final double    xoffset;
    private final double    yoffset;

    public ScrollEvent(Component component, double xoffset, double yoffset) {
        this.component = component;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    @Override
    public Component getComponent() {
        return component;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }
}
