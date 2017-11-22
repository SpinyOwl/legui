package org.liquidengine.legui.layout;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface Layout {

    void addComponent(Component component, LayoutRestriction restriction);

    void removeComponent(Component component);

    Vector2f getMinimumSize(Component parent);

    Vector2f getPreferredSize(Component parent);

    Vector2f getMaximumSize(Component parent);

    void layout(Component parent);

}
