package org.liquidengine.legui.component.misc.listener.scrollablepanel;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.system.handler.SehUtil;

import java.util.ArrayList;

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

        if (Math.abs(event.getYoffset()) > 0)
            updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), scrollablePanel.getVerticalScrollBar());
        if (Math.abs(event.getXoffset()) > 0)
            updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), scrollablePanel.getHorizontalScrollBar());
    }
}
