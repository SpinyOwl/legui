package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public final class SehUtil {
    private SehUtil() {
    }

    public static Component getIntersectedComponent(Layer layer, Vector2f vector) {
        Component       target = null;
        List<Component> childs = layer.getChilds();
        for (Component child : childs) {
            target = getTargetComponent(vector, child);
        }
        return target;
    }

    private static  Component getTargetComponent(Vector2f vector, Component component) {
        Component target = null;
        if (component.isVisible()) {
            if (component.intersects(vector)) {
                target = component;
                if (component instanceof Container) {
                    List<Component> childs = ((Container) component).getChilds();
                    for (Component child : childs) {
                        target = getTargetComponent(vector, child);
                    }
                }
            }
        }
        return target;
    }
}
