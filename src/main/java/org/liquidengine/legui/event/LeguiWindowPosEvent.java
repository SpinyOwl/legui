package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class LeguiWindowPosEvent<T extends Component> extends LeguiEvent<T> {
    private final int xpos;
    private final int ypos;

    public LeguiWindowPosEvent(T component, LeguiContext context, int xpos, int ypos) {
        super(component, context);
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
