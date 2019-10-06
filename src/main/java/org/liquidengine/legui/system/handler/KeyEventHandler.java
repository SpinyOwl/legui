package org.liquidengine.legui.system.handler;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemKeyEvent;

/**
 * System handler that used to update key states of {@link Keyboard.Key}.
 */
public class KeyEventHandler implements SystemEventHandler<SystemKeyEvent> {

    @Override
    public void handle(SystemKeyEvent event, Frame frame, Context context) {
        int keyCode = event.key;
        if (keyCode != -1) {
            Keyboard.Key key = Keyboard.Key.getByCode(keyCode);
            if (key != null) {
                key.setPressed(event.action != GLFW_RELEASE);
            }
        }

        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) {
            return;
        }

        EventProcessorProvider.getInstance().pushEvent(new KeyEvent(focusedGui, context, frame, event.action, keyCode, event.mods, event.scancode));
    }
}
