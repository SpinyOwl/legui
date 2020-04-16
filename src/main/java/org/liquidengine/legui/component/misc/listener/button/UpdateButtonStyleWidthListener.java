package org.liquidengine.legui.component.misc.listener.button;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.event.button.ButtonContentChangeEvent;
import org.liquidengine.legui.listener.EventListener;

public class UpdateButtonStyleWidthListener implements EventListener<ButtonContentChangeEvent> {

    /**
     * Used to handle {@link ButtonContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ButtonContentChangeEvent event) {
        Button button = event.getTargetComponent();
        float textWidth = button.getTextState().getTextWidth();
        button.getStyle().setWidth(textWidth);
    }

}
