package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowPosEvent implements Event {
    private final Component component;
    private final int xpos;
    private final int ypos;

    public WindowPosEvent(Component component, int xpos, int ypos) {
        this.component = component;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    @Override
    public Component getComponent() {
        return component;
    }
}
