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
//public class ContainerSystemMouseClickEventListener implements SystemEventListener<ComponentContainer, SystemMouseClickEvent> {
//    @Override
//    public void update(SystemMouseClickEvent event, ComponentContainer component, LeguiContext context) {
//        if (!component.isVisible() || !component.isEnabled()) return;
//
//        Component target = null;
//        List<Component> components = component.getComponents();
//        if (!components.isEmpty()) {
//            for (Component child : components) {
//                if (child.isVisible() && child.getIntersector().intersects(child, context.getCursorPosition())) {
//                    target = child;
//                }
//            }
//            if (target != null) {
//                target.getSystemEventListeners().getListener(event.getClass()).update(event, target, context);
//            }
//        }
//    }
//
//}
