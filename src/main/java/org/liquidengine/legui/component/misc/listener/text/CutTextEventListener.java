package org.liquidengine.legui.component.misc.listener.text;

import org.liquidengine.legui.component.TextComponent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.input.Keyboard;

import java.util.function.BiConsumer;

import static org.liquidengine.legui.component.misc.listener.TextComponentShortcutUtil.cut;
import static org.liquidengine.legui.input.KeyCode.KEY_X;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public abstract class CutTextEventListener {

    public void processCut(KeyboardEvent event, BiConsumer<String, String> eventGenerator) {
        boolean pressed = event.getAction() != KeyAction.RELEASE;
        if (!pressed) {
            return;
        }

        KeyCode cutKey = Keyboard.getCutShortcut().getKey();
        KeyCode keyCode = event.getKey().getKeyCode();
        if (event.getMods().contains(KeyMod.CONTROL)
                && (cutKey != null && keyCode == cutKey || keyCode == KEY_X)) {
            cut((TextComponent) event.getTargetComponent(), eventGenerator);
        }
    }
}
