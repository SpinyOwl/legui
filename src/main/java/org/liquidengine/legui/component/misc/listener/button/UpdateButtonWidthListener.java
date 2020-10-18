package org.liquidengine.legui.component.misc.listener.button;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.event.button.ButtonContentChangeEvent;
import org.liquidengine.legui.component.event.button.ButtonWidthChangeEvent;
import org.liquidengine.legui.listener.EventListener;

public class UpdateButtonWidthListener implements EventListener<ButtonWidthChangeEvent> {

    /**
     * Used to handle {@link ButtonContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ButtonWidthChangeEvent event) {
        Button button = event.getTargetComponent();
        float textWidth = button.getTextState().getTextWidth();
        button.getSize().x = textWidth;
    }

}
