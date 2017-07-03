package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowPosEvent<T extends Component> extends Event<T> {
    private final int xpos;
    private final int ypos;

    public WindowPosEvent(T component, Context context, int xpos, int ypos) {
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
