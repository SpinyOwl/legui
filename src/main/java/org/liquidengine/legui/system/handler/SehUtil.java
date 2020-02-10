package org.liquidengine.legui.system.handler;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.LayerContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for system event handlers.
 */
public final class SehUtil {

    private SehUtil() {
    }

    /**
     * Used to find target component for provided layer and vector. Target means top component which intersected by provided point(vector).
     *
     * @param layer  layer to search.
     * @param vector point to search.
     * @return top component from layer intersected by vector.
     */
    public static Component getTargetComponent(Layer layer, Vector2f vector) {
        LayerContainer container = layer.getContainer();
        Component target = container.isFocusable() ? container : null;
        List<Component> childComponents = container.getChildComponents();
        for (Component child : childComponents) {
            target = recursiveTargetComponentSearch(vector, child, target);
        }
        return target;
    }

    /**
     * Used to search target component (under point) in component. Target means top component which intersected by provided point(vector).
     *
     * @param vector    vector to point.
     * @param component source component to search target.
     * @param target    current target.
     * @return the top visible component under point.
     */
    private static Component recursiveTargetComponentSearch(Vector2f vector, Component component, Component target) {
        Component newtarget = target;
        if (component.isVisible() && component.intersects(vector)) {
            if (component.isFocusable()) {
                newtarget = component;
            }
            List<Component> childComponents = component.getChildComponents();
            for (Component child : childComponents) {
                newtarget = recursiveTargetComponentSearch(vector, child, newtarget);
            }
        }
        return newtarget;
    }


    /**
     * Used to search all components (under point) in component.
     *
     * @param vector vector to point.
     * @param layer  layer to search.
     * @return all top visible components in layer under point(vector).
     */
    public static List<Component> getTargetComponentList(Layer layer, Vector2f vector) {
        Component target = layer.getContainer();
        List<Component> targetList = new ArrayList<>();
        recursiveTargetComponentListSearch(vector, target, targetList);
        return targetList;
    }


    /**
     * Used to search all components (under point) in component. New located target component will be added to target list.
     *
     * @param vector     vector to point.
     * @param component  source component to search target.
     * @param targetList current target list.
     */
    public static void recursiveTargetComponentListSearch(Vector2f vector, Component component, List<Component> targetList) {
        if (component.isVisible() /*&& component.isEnabled()*/ && component.intersects(vector)) {
            targetList.add(component);
            List<Component> childComponents = component.getChildComponents();
            for (Component child : childComponents) {
                recursiveTargetComponentListSearch(vector, child, targetList);
            }
        }
    }

//
//    public static Controller getTargetController(Layer layer, Vector2f vector) {
//        Controller target = null;
//        LayerContainer container = layer.getContainer();
//        List<Component> childComponents = container.getChildComponents();
//        for (Component child : childComponents) {
//            if (child instanceof Controller) {
//                target = recursiveTargetControllerSearch(vector, (Controller) child, target);
//            }
//        }
//        return target;
//    }
//
//    private static Controller recursiveTargetControllerSearch(Vector2f vector, Controller component, Controller target) {
//        Controller newtarget = target;
//        if (component.isVisible() && component.isEnabled() && component.intersects(vector)) {
//            newtarget = component;
//            if (component instanceof Container) {
//                List<Component> childComponents = ((Container) component).getChildComponents();
//                for (Component child : childComponents) {
//                    if (child instanceof Controller) {
//                        newtarget = recursiveTargetControllerSearch(vector, (Controller) child, newtarget);
//                    }
//                }
//            }
//        }
//        return newtarget;
//    }

}
