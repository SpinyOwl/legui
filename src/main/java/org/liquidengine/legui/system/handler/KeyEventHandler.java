package org.liquidengine.legui.system.handler;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyboardKey;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemKeyEvent;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

/**
 * System handler that used to update key states of {@link KeyboardKey}.
 */
public class KeyEventHandler implements SystemEventHandler<SystemKeyEvent> {

    @Override
    public void handle(SystemKeyEvent event, Frame frame, Context context) {
        int keyCode = event.key;
        KeyboardKey key = new KeyboardKey(Keyboard.getKeyCode(event.key), event.key);

        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) {
            return;
        }

        KeyAction action = null;
        if (event.action == GLFW_RELEASE) action = KeyAction.RELEASE;
        else if (event.action == GLFW_PRESS) action = KeyAction.PRESS;
        else if (event.action == GLFW_REPEAT) action = KeyAction.REPEAT;

        Set<KeyMod> modSet = new HashSet<>();
        if (isMod(event.mods, GLFW_MOD_SHIFT)) modSet.add(KeyMod.SHIFT);
        if (isMod(event.mods, GLFW_MOD_ALT)) modSet.add(KeyMod.ALT);
        if (isMod(event.mods, GLFW_MOD_CONTROL)) modSet.add(KeyMod.CONTROL);
        if (isMod(event.mods, GLFW_MOD_CAPS_LOCK)) modSet.add(KeyMod.CAPS_LOCK);
        if (isMod(event.mods, GLFW_MOD_NUM_LOCK)) modSet.add(KeyMod.NUM_LOCK);

        EventProcessorProvider.getInstance().pushEvent(new KeyEvent(focusedGui, context, frame, event.action, keyCode, event.mods, event.scancode));
        EventProcessorProvider.getInstance().pushEvent(new KeyboardEvent(focusedGui, context, frame, action, key, modSet));
    }

    private boolean isMod(int mods, int modToTest) {
        return (mods & modToTest) != 0;
    }
}
