package org.liquidengine.legui.component.misc.listener.controller;

import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;

/**
 * Default event listener for {@link CursorEnterEvent} to add tooltip to tooltip layer and make it visible or not visible.
 */
public class TooltipCursorEnterListener implements CursorEnterEventListener {

    /**
     * Used to process {@link CursorEnterEvent}.
     *
     * @param event event to process.
     */
    @Override
    public void process(CursorEnterEvent event) {
        Controller controller = (Controller) event.getComponent();
        Tooltip tooltip = controller.getTooltip();
        if (tooltip != null) {
            if (event.isEntered()) {
                event.getFrame().getTooltipLayer().getContainer().add(tooltip);
            } else {
                event.getFrame().getTooltipLayer().getContainer().remove(tooltip);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @param obj object to compare.
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
