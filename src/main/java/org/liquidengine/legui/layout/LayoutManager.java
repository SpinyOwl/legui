package org.liquidengine.legui.layout;

import org.liquidengine.legui.component.Component;

/**
 * Layout manager. Used to layout component and it's child components.
 * @author Aliaksandr_Shcherbin.
 */
public abstract class LayoutManager {

    /**
     * Instance of layout manager.
     */
    private static LayoutManager instance = new DefaultLayoutManager();

    /**
     * Returns layout manager instance.
     *
     * @return layout manager instance.
     */
    public static LayoutManager getInstance() {
        return instance;
    }

    /**
     * Used to set layout manager instance.
     *
     * @param instance layout manager instance to set.
     */
    public static void setInstance(LayoutManager instance) {
        LayoutManager.instance = instance;
    }

    /**
     * Used to layout component and all of his child components.
     *
     * @param component component to lay out.
     */
    public abstract void layout(Component component);
}
