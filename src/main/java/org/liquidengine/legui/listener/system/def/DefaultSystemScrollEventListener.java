package org.liquidengine.legui.listener.system.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.MouseScrollEvent;
import org.liquidengine.legui.event.system.SystemScrollEvent;
import org.liquidengine.legui.listener.SystemEventListener;

/**
 * Created by Shcherbin Alexander on 9/2/2016.
 */
public class DefaultSystemScrollEventListener implements SystemEventListener<Component, SystemScrollEvent> {
    @Override
    public void update(SystemScrollEvent event, Component gui, LeguiContext context) {
        ScrollablePanel scrollpanel = null;
        if (gui instanceof ScrollablePanel) {
            scrollpanel = (ScrollablePanel) gui;
        } else {
            Component target = null;
            if (gui instanceof ComponentContainer) {
                target = gui.getComponentAt(context.getCursorPosition());
                if (target != gui && target != null) {
                    target.getSystemEventListeners().getListener(event.getClass()).update(event, target, context);
                }
            }
            if ((!(gui instanceof ComponentContainer) || target == null || target == gui) &&
                    (!gui.getState().isFocused() || gui.getLeguiEventListeners().getListeners(MouseScrollEvent.class).isEmpty())) {
                Component parent = gui.getParent();
                while (parent != null) {
                    if (parent instanceof ScrollablePanel) {
                        scrollpanel = (ScrollablePanel) parent;
                        break;
                    }
                    parent = parent.getParent();
                }
            }
        }
        if (scrollpanel != null) {
            ScrollBar scrollBar = scrollpanel.getVerticalScrollBar();
            scrollBar.getSystemEventListeners().getListener(event.getClass()).update(event, scrollBar, context);
        }
    }

}
