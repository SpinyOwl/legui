package org.liquidengine.legui.processor.system.component.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.MouseScrollEvent;
import org.liquidengine.legui.event.system.SystemScrollEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

/**
 * Created by Shcherbin Alexander on 9/2/2016.
 */
public class DefaultGuiScrollProcessor implements LeguiComponentEventProcessor<Component, SystemScrollEvent> {
    @Override
    public void process(Component gui, SystemScrollEvent event, LeguiContext leguiContext) {
        ScrollablePanel scrollpanel = null;
        if (gui instanceof ScrollablePanel) {
            scrollpanel = (ScrollablePanel) gui;
        } else if (!gui.isFocused() || gui.getListenerList().getListeners(MouseScrollEvent.class).isEmpty()) {
            Component parent = gui.getParent();
            while (parent != null) {
                if (parent instanceof ScrollablePanel) {
                    scrollpanel = (ScrollablePanel) parent;
                    break;
                }
                parent = parent.getParent();
            }
        }
        if (scrollpanel != null) {
            ScrollBar scrollBar = scrollpanel.getVerticalScrollBar();
            scrollBar.getProcessors().getScrollEventProcessor().process(scrollBar, event, leguiContext);
        }
    }

}
