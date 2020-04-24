package org.liquidengine.legui.component.misc.listener.splitpanel;

import static org.liquidengine.legui.component.optional.Orientation.HORIZONTAL;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.PRESS;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.RELEASE;

import java.util.Objects;
import org.liquidengine.legui.component.SplitPanel;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.cursor.Cursor;
import org.liquidengine.legui.cursor.CursorServiceProvider;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.EventListener;

public class SplitPanelSeparatorListenerDelegate implements EventListener<Event> {

    private SplitPanel splitPanel;
    private boolean dragging = false;

    public SplitPanelSeparatorListenerDelegate(SplitPanel splitPanel) {
        this.splitPanel = Objects.requireNonNull(splitPanel);
    }

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(Event event) {
        if (!event.getTargetComponent().equals(splitPanel.getSeparator())) {
            return;
        }

        if (event instanceof MouseClickEvent) {
            MouseClickEvent e = (MouseClickEvent) event;
            if (e.getAction() == PRESS) {
                dragging = true;
            }
            if (e.getAction() == RELEASE) {
                dragging = false;
                CursorServiceProvider.getInstance().setCursor(Cursor.ARROW, e.getContext());
            }
        }

        if (event instanceof CursorEnterEvent) {
            CursorEnterEvent e = (CursorEnterEvent) event;
            Orientation orientation = splitPanel.getOrientation();
            if (e.isEntered() && orientation == HORIZONTAL) {
                CursorServiceProvider.getInstance().setCursor(Cursor.H_RESIZE, e.getContext());
            } else if (e.isEntered() && orientation != HORIZONTAL) {
                CursorServiceProvider.getInstance().setCursor(Cursor.V_RESIZE, e.getContext());
            } else if (!dragging) {
                CursorServiceProvider.getInstance().setCursor(Cursor.ARROW, e.getContext());
            }
        }
    }
}
