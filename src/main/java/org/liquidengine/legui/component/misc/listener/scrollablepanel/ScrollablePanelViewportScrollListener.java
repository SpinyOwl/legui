package org.liquidengine.legui.component.misc.listener.scrollablepanel;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.ScrollEventListener;

import static org.liquidengine.legui.component.misc.listener.EventUtils.hasScrollableInChildComponentsUnderCursor;
import static org.liquidengine.legui.component.misc.listener.EventUtils.hasViewportsInAboveLayersUnderCursor;
import static org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

/**
 * Created by ShchAlexander on 23.07.2017.
 */
public class ScrollablePanelViewportScrollListener implements ScrollEventListener {

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ScrollEvent event) {
        Vector2f cursorPosition = Mouse.getCursorPosition();
        Component targetComponent = event.getTargetComponent();

        if (hasViewportsInAboveLayersUnderCursor(targetComponent, cursorPosition)) return;
        if (hasScrollableInChildComponentsUnderCursor(targetComponent, cursorPosition)) return;

        ScrollablePanel scrollablePanel = (ScrollablePanel) targetComponent.getParent();

        if (Math.abs(event.getYoffset()) > 0)
            updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), scrollablePanel.getVerticalScrollBar());
        if (Math.abs(event.getXoffset()) > 0)
            updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), scrollablePanel.getHorizontalScrollBar());
    }

}
