package org.liquidengine.legui.listener.system.container;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import java.util.List;

/**
 * Created by Alexander on 30.10.2016.
 */
public class ContainerSystemMouseClickEventListener implements SystemEventListener<ComponentContainer, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, ComponentContainer component, LeguiContext context) {
        if (!component.isVisible() || !component.isEnabled()) return;

        Component target = null;
        List<Component> components = component.getComponents();
        if (components.isEmpty()) {

        } else {
            for (Component child : components) {
                if (child.isVisible() && child.getIntersector().intersects(child, context.getCursorPosition())) {
                    target = child;
                }
            }
            if (target != null) {
                target.getSystemEventListeners().getListener(event.getClass()).update(event, target, context);
            }
        }
    }

//    public static Component getMouseTarget(Component target, Component component, Vector2f cursorPosition) {
//        if (component instanceof ComponentContainer) {
//            if (component.isVisible()) {
//                ComponentContainer container = ((ComponentContainer) component);
//                if (component.getIntersector().intersects(component, cursorPosition)) {
//                    target = component;
//                    for (Component element : container.getComponents()) {
//                        target = getMouseTarget(target, element, cursorPosition);
//                    }
//                }
//            }
//        } else {
//            if (component.isVisible() && component.isEnabled() && component.getIntersector().intersects(component, cursorPosition)) {
//                target = component;
//            }
//        }
//        return target;
//    }
}
