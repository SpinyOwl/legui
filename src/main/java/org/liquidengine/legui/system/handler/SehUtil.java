package org.liquidengine.legui.system.handler;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for system event handlers.
 */
public final class SehUtil {

    private SehUtil() {
    }

    /**
     * Used to find target component for provided component and vector. Target means top component which intersected by provided point(vector).
     *
     * @param component component to search.
     * @param vector    point to search.
     * @return top component from component intersected by vector.
     */
    public static Component getTargetComponent(Component component, Vector2f vector) {
        Component target = component.isFocusable() ? component : null;
        List<Component> childComponents = component.getChildComponents();
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
     * @param vector    vector to point.
     * @param component component to search in.
     * @return all top visible components in component under point(vector).
     */
    public static List<Component> getTargetComponentList(Component component, Vector2f vector) {
        List<Component> targetList = new ArrayList<>();
        recursiveTargetComponentListSearch(vector, component, targetList);
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
}
