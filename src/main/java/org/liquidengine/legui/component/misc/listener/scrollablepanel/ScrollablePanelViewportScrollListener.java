package org.liquidengine.legui.component.misc.listener.scrollablepanel;

import java.util.ArrayList;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.misc.listener.textarea.TextAreaViewportScrollListener;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.system.handler.SehUtil;

import static org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

/**
 * Created by ShchAlexander on 23.07.2017.
 */
public class ScrollablePanelViewportScrollListener implements EventListener<ScrollEvent> {

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ScrollEvent event) {
        ArrayList<Component> targetList = new ArrayList<>();
        SehUtil.recursiveTargetComponentListSearch(Mouse.getCursorPosition(), event.getTargetComponent(), targetList);
        for (Component component : targetList) {
            if ((component instanceof TextArea) || (component instanceof ScrollablePanel)) {
                return;
            }
        }

        ScrollablePanel scrollablePanel = (ScrollablePanel) event.getTargetComponent().getParent();

        updateScrollBarValue(event, scrollablePanel.getVerticalScrollBar());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
