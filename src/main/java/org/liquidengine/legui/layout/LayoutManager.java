package org.liquidengine.legui.layout;

import org.liquidengine.legui.component.Component;

/**
 * Layout manager.
 * @author Aliaksandr_Shcherbin.
 */
public abstract class LayoutManager {

    private static LayoutManager instance = new DefaultLayoutManager();

    public static LayoutManager getInstance() {
        return instance;
    }

    /**
     * Used to layout component and all of his child components.
     *
     * @param component component to lay out.
     */
    public abstract void layout(Component component);
}
