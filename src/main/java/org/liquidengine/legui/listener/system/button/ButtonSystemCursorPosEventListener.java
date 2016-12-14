package org.liquidengine.legui.listener.system.button;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.listener.system.def.DefaultSystemCursorPosEventListener;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Shcherbin Alexander on 9/30/2016.
 */
public class ButtonSystemCursorPosEventListener implements SystemEventListener<Button, SystemCursorPosEvent> {
    private DefaultSystemCursorPosEventListener defaultSystemCursorPosEventListener = new DefaultSystemCursorPosEventListener();

    @Override
    public void update(SystemCursorPosEvent event, Button component, LeguiContext context) {
        defaultSystemCursorPosEventListener.update(event, component, context);
        if (context.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && context.getFocusedGui() == component) {
            component.getState().setPressed(true);
        }
    }
}
