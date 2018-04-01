package org.liquidengine.legui.layout;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.layout.flex.FlexLayout;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.util.Utilites;

/**
 * Default layout manager.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class DefaultLayoutManager extends LayoutManager {

    private Map<DisplayType, Layout> layoutMap = new ConcurrentHashMap<>();

    public DefaultLayoutManager() {
        registerLayout(DisplayType.FLEX, new FlexLayout());
    }

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

    @Override
    public void registerLayout(DisplayType displayType, Layout layout) {
        if (displayType == null) {
            return;
        }
        if (layout == null) {
            layoutMap.remove(displayType);
        } else {
            layoutMap.put(displayType, layout);
        }
    }

    /**
     * Used to layout component and all of his child components.
     *
     * @param component component to lay out.
     */
    public void layout(Component component) {
        if (component != null && component.isVisible() && Utilites.visibleInParents(component)) {
            Layout layout = layoutMap.get(component.getStyle().getDisplay());
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
