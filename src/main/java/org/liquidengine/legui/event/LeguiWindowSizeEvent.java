package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class LeguiWindowSizeEvent<T extends Component> extends LeguiEvent<T> {
    private final int width;
    private final int height;

    public LeguiWindowSizeEvent(T component, LeguiContext context, int width, int height) {
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
