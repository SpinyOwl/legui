package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.*;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public final class SehUtil {
    private SehUtil() {
    }

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
        if (component.isVisible() && component.isEnabled() && component.intersects(vector)) {
            target = component;
            if (component instanceof Container) {
                List<Component> childs = ((Container) component).getChilds();
                for (Component child : childs) {
                    target = recursiveTargetComponentSearch(vector, child, target);
                }
            }
        }
        return target;
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
        if (component.isVisible() && component.isEnabled() && component.intersects(vector)) {
            target = component;
            if (component instanceof Container) {
                List<Component> childs = ((Container) component).getChilds();
                for (Component child : childs) {
                    if (child instanceof Controller) {
                        target = recursiveTargetControllerSearch(vector, (Controller) child, target);
                    }
                }
            }
        }
        return target;
    }

}
