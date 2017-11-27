package org.liquidengine.legui.layout;

import java.util.List;
import org.liquidengine.legui.component.Component;

/**
 * Default layout manager.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class DefaultLayoutManager extends LayoutManager {

    /**
     * Used to layout component and all of his child components.
     *
     * @param component component to lay out.
     */
    @Override
    public void layout(Component component) {
        if (component != null && component.isVisible()) {
            Layout layout = component.getLayout();
            if (layout != null) {
                layout.layout(component);
            }

            if (!component.isEmpty()) {
                List<Component> childs = component.getChilds();
                for (Component child : childs) {
                    layout(child);
                }
            }
        }
    }
}
