package org.liquidengine.legui.layout.boxlayout;

import java.util.List;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.layout.Layout;
import org.liquidengine.legui.layout.LayoutConstraint;

/**
 * Created by ShchAlexander on 14.01.2018.
 */
public class BoxLayout implements Layout {

    private Orientation orientation;

    public BoxLayout() {
        this.orientation = Orientation.HORIZONTAL;
    }

    public BoxLayout(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Used to add component to layout.
     *
     * @param component component to add.
     * @param constraint layout constraint.
     */
    @Override
    public void addComponent(Component component, LayoutConstraint constraint) {

    }

    /**
     * Used to remove component from layout.
     *
     * @param component component to remove.
     */
    @Override
    public void removeComponent(Component component) {

    }

    /**
     * Used to calculate minimum size for parent component.
     *
     * @param parent component to calculate minimum size.
     *
     * @return calculated minimum size for specified component.
     */
    @Override
    public Vector2f getMinimumSize(Component parent) {
        Vector2f min = new Vector2f();

        List<Component> childComponents = parent.getChildComponents();

        return min;
    }

    /**
     * Used to calculate preferred size for parent component.
     *
     * @param parent component to calculate preferred size.
     *
     * @return calculated preferred size for specified component.
     */
    @Override
    public Vector2f getPreferredSize(Component parent) {
        return null;
    }

    /**
     * Used to calculate maximum size for parent component.
     *
     * @param parent component to calculate maximum size.
     *
     * @return calculated maximum size for specified component.
     */
    @Override
    public Vector2f getMaximumSize(Component parent) {
        return null;
    }

    /**
     * Used to lay out child components for parent component.
     *
     * @param parent component to lay out.
     */
    @Override
    public void layout(Component parent) {

    }
}
