package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowPosEvent extends AbstractEvent {
    private final int xpos;
    private final int ypos;

    public WindowPosEvent(Component controller, Frame frame, int xpos, int ypos) {
        super(controller, frame);
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }
}
