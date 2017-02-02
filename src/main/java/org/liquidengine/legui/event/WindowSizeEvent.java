package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowSizeEvent implements Event {
    private final Component component;
    private final int       width;
    private final int       height;

    public WindowSizeEvent(Component component, int width, int height) {
        this.component = component;
        this.width = width;
        this.height = height;
    }

    @Override
    public Component getComponent() {
        return component;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
