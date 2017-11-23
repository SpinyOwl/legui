package org.liquidengine.legui.layout;

import org.joml.Vector2f;
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
     */
    void addComponent(Component component, LayoutConstraint constraint);

    /**
     * Used to remove component from layout.
     *
     * @param component component to remove.
     */
    void removeComponent(Component component);

    /**
     * Used to calculate minimum size for parent component.
     *
     * @param parent component to calculate minimum size.
     * @return calculated minimum size for specified component.
     */
    Vector2f getMinimumSize(Component parent);

    /**
     * Used to calculate preferred size for parent component.
     *
     * @param parent component to calculate preferred size.
     * @return calculated preferred size for specified component.
     */
    Vector2f getPreferredSize(Component parent);

    /**
     * Used to calculate maximum size for parent component.
     *
     * @param parent component to calculate maximum size.
     * @return calculated maximum size for specified component.
     */
    Vector2f getMaximumSize(Component parent);

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
    void layout(Component parent);

}
