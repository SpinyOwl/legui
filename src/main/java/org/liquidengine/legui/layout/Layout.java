package org.liquidengine.legui.layout;

import org.liquidengine.legui.component.Component;

/**
 * Layout interface.
 *
 * @author Aliaksandr_Shcherbin.
 */
public interface Layout {

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
    void layout(Component parent);

}
