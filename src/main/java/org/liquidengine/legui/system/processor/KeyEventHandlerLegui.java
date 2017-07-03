package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.LeguiKeyEvent;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemKeyEvent;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * System handler that used to update key states of {@link Keyboard.Key}.
 */
public class KeyEventHandlerLegui implements LeguiSystemEventHandler<LeguiSystemKeyEvent> {
    @Override
    public void handle(LeguiSystemKeyEvent event, Frame frame, LeguiContext context) {
        int keyCode = event.key;
        if (keyCode != -1) {
            Keyboard.Key key = Keyboard.Key.getByCode(keyCode);
            if (key != null) {
                key.setPressed(event.action != GLFW_RELEASE);
            }
        }

        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) return;

        context.getEventProcessor().pushEvent(new LeguiKeyEvent(focusedGui, context, event.action, keyCode, event.mods, event.scancode));
    }
}
