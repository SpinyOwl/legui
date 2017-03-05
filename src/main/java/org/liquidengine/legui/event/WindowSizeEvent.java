package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowSizeEvent<T extends Component> extends AbstractEvent<T> {
    private final int width;
    private final int height;

    public WindowSizeEvent(T component, Context context, int width, int height) {
        super(component, context);
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
