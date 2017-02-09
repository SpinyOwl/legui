package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Layer;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public final class SehUtil {
    private SehUtil() {
    }

    public static Component getTargetComponent(Layer layer, Vector2f vector) {
        return recursiveTargetComponentSearch(vector, layer.getContainer());
    }

    private static Component recursiveTargetComponentSearch(Vector2f vector, Component component) {
        Component target = null;
        if (component.isVisible() && component.intersects(vector)) {
            target = component;
            if (component instanceof Container) {
                List<Component> childs = ((Container) component).getChilds();
                for (Component child : childs) {
                    target = recursiveTargetComponentSearch(vector, child);
                }
            }
        }
        return target;
    }

    public static Controller getTargetController(Layer layer, Vector2f vector) {
        return recursiveTargetControllerSearch(vector, layer.getContainer());
    }

    private static Controller recursiveTargetControllerSearch(Vector2f vector, Controller component) {
        final Controller[] target = {null};
        if (component.isVisible() && component.intersects(vector)) {
            target[0] = component;
            if (component instanceof Container) {
                List<Component> childs = ((Container) component).getChilds();
                childs.stream().filter(c -> c instanceof Controller).forEach(c -> target[0] = recursiveTargetControllerSearch(vector, (Controller) c));
            }
        }
        return target[0];
    }

}
