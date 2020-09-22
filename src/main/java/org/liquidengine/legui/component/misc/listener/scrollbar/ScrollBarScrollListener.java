package org.liquidengine.legui.component.misc.listener.scrollbar;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.ScrollEventListener;

import static org.liquidengine.legui.component.misc.listener.EventUtils.hasViewportsInAboveLayersUnderCursor;
import static org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

/**
 * Default mouse scroll event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent} event.
 */
public class ScrollBarScrollListener implements ScrollEventListener {

    public void process(ScrollEvent event) {
        Vector2f cursorPosition = Mouse.getCursorPosition();
        Component targetComponent = event.getTargetComponent();
        if (hasViewportsInAboveLayersUnderCursor(targetComponent, cursorPosition)) return;

        ScrollBar scrollBar = (ScrollBar) targetComponent;

        if (Math.abs(event.getYoffset()) > 0)
            updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), scrollBar);
        else if (Math.abs(event.getXoffset()) > 0)
            updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), scrollBar);
    }
}
