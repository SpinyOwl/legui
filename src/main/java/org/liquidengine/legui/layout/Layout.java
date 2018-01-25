package org.liquidengine.legui.layout;

import org.liquidengine.legui.component.Component;

/**
 * Layout interface.
 * @author Aliaksandr_Shcherbin.
 */
public interface Layout {

    /**
     * Used to add component to layout.
     *
     * @param component component to add.
     * @param constraint layout constraint.
     *
     * @throws IllegalArgumentException if provided constraint is not supported by this layout.
     */
    void addComponent(Component component, LayoutConstraint constraint) throws IllegalArgumentException;

    /**
     * Used to remove component from layout.
     *
     * @param component component to remove.
     */
    void removeComponent(Component component);

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
    void layout(Component parent);

}
