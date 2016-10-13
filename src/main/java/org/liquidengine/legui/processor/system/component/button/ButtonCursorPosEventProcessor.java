package org.liquidengine.legui.processor.system.component.button;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Shcherbin Alexander on 9/30/2016.
 */
public class ButtonCursorPosEventProcessor implements LeguiComponentEventProcessor<Button, SystemCursorPosEvent> {
    @Override
    public void process(Button button, SystemCursorPosEvent event, LeguiContext leguiContext) {
        if(leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && leguiContext.getFocusedGui() == button){
            button.setPressed(true);
        }
    }
}
