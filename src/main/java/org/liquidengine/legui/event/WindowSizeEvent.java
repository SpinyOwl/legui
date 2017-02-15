package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowSizeEvent extends AbstractEvent {
    private final int width;
    private final int height;

    public WindowSizeEvent(Component controller, Context context, int width, int height) {
        super(controller, context);
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
