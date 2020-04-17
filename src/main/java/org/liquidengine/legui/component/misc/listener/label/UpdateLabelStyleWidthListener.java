package org.liquidengine.legui.component.misc.listener.label;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.event.label.LabelContentChangeEvent;
import org.liquidengine.legui.listener.EventListener;

public class UpdateLabelStyleWidthListener implements EventListener<LabelContentChangeEvent> {

    /**
     * Used to handle {@link LabelContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(LabelContentChangeEvent event) {
        Label label = event.getTargetComponent();
        float textWidth = label.getTextState().getTextWidth();
        label.getStyle().setWidth(textWidth);
    }

}
