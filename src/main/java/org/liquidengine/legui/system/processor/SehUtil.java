package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.*;

import java.util.List;

/**
 * Utility class for system event handlers.
 */
public final class SehUtil {
    private SehUtil() {
    }

    /**
     * Used to find target component for provided layer and vector.
     * Target means top component which intersected by provided point(vector).
     *
     * @param layer  layer to search.
     * @param vector point to search.
     * @return top component from layer intersected by vector.
     */
    public static Component getTargetComponent(Layer layer, Vector2f vector) {
        Component       target    = null;
        LayerContainer  container = layer.getContainer();
        List<Component> childs    = container.getChilds();
        for (Component child : childs) {
            target = recursiveTargetComponentSearch(vector, child, target);
        }
        return target;
    }

    private static Component recursiveTargetComponentSearch(Vector2f vector, Component component, Component target) {
        Component newtarget = target;
        if (component.isVisible() && component.isEnabled() && component.intersects(vector)) {
            newtarget = component;
            if (component instanceof Container) {
                List<Component> childs = ((Container) component).getChilds();
                for (Component child : childs) {
                    newtarget = recursiveTargetComponentSearch(vector, child, newtarget);
                }
            }
        }
        return newtarget;
    }

    public static Controller getTargetController(Layer layer, Vector2f vector) {
        Controller      target    = null;
        LayerContainer  container = layer.getContainer();
        List<Component> childs    = container.getChilds();
        for (Component child : childs) {
            if (child instanceof Controller) {
                target = recursiveTargetControllerSearch(vector, (Controller) child, target);
            }
        }
        return target;
    }

    private static Controller recursiveTargetControllerSearch(Vector2f vector, Controller component, Controller target) {
        Controller newtarget = target;
        if (component.isVisible() && component.isEnabled() && component.intersects(vector)) {
            newtarget = component;
            if (component instanceof Container) {
                List<Component> childs = ((Container) component).getChilds();
                for (Component child : childs) {
                    if (child instanceof Controller) {
                        newtarget = recursiveTargetControllerSearch(vector, (Controller) child, newtarget);
                    }
                }
            }
        }
        return newtarget;
    }

}
