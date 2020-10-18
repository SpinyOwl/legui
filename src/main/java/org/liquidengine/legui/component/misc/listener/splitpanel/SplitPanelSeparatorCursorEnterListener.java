package org.liquidengine.legui.component.misc.listener.splitpanel;

import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.listener.EventListener;

public class SplitPanelSeparatorCursorEnterListener implements EventListener<CursorEnterEvent> {
    private SplitPanelSeparatorListenerDelegate delegate;

    public SplitPanelSeparatorCursorEnterListener(SplitPanelSeparatorListenerDelegate delegate) {
        this.delegate = delegate;
    }

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(CursorEnterEvent event) {
        delegate.process(event);
    }
}
