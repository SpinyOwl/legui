package org.liquidengine.legui.layout;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.util.Utilites;

/**
 * Default layout manager.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class DefaultLayoutManager extends LayoutManager {

    /**
     * Used to layout frame layers and all of their child components.
     *
     * @param frame frame to lay out.
     */
    @Override
    public void layout(Frame frame) {
        for (Layer layer : frame.getAllLayers()) {
            layout(layer.getContainer());
        }
    }

    /**
     * Used to layout component and all of his child components.
     *
     * @param component component to lay out.
     */
    public void layout(Component component) {
        if (component != null && component.isVisible() && Utilites.visibleInParents(component)) {
            Layout layout = component.getLayout();
            if (layout != null) {
                layout.layout(component);
            }

            if (!component.isEmpty()) {
                List<Component> childComponents = component.getChildComponents();
                for (Component child : childComponents) {
                    layout(child);
                }
            }
        }
    }
}
