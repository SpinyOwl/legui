package org.liquidengine.legui.processor.system.component.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.ScrollEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

/**
 * Created by Shcherbin Alexander on 9/2/2016.
 */
public class DefaultGuiScrollProcessor implements LeguiComponentEventProcessor<Component, ScrollEvent> {
    @Override
    public void process(Component gui, ScrollEvent event, LeguiContext leguiContext) {
//        ScrollablePanel scrollpanel = null;
//        if (gui instanceof ScrollablePanel) {
//            scrollpanel = (ScrollablePanel) gui;
//        } else if (!gui.isFocused() || gui.getListeners().getScrollListeners().isEmpty()) {
//            Element parent = gui.getParent();
//            while (parent != null) {
//                if (parent instanceof ScrollablePanel) {
//                    scrollpanel = (ScrollablePanel) parent;
//                    break;
//                }
//                parent = parent.getParent();
//            }
//        }
//        if (scrollpanel != null) {
//            ScrollBar scrollBar = scrollpanel.getVerticalScrollBar();
//            scrollBar.getProcessors().getScrollEventProcessor().process(scrollBar, event, processorState);
//        }
    }

}
