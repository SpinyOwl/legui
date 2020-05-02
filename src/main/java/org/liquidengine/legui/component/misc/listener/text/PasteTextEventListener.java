package org.liquidengine.legui.component.misc.listener.text;

import org.liquidengine.legui.component.TextComponent;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.input.Keyboard;

import java.util.function.BiConsumer;

import static org.liquidengine.legui.component.misc.listener.TextComponentShortcutUtil.paste;
import static org.liquidengine.legui.input.KeyCode.KEY_V;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public abstract class PasteTextEventListener {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    public void processPaste(KeyboardEvent event, BiConsumer<String, String> eventGenerator) {
        boolean pressed = event.getAction() != KeyAction.RELEASE;
        if (!pressed) {
            return;
        }

        KeyCode pasteKey = Keyboard.getPasteShortcut().getKey();
        KeyCode keyCode = event.getKey().getKeyCode();
        if (event.getMods().contains(KeyMod.CONTROL)
                && (pasteKey != null && keyCode == pasteKey || keyCode == KEY_V))
            paste((TextComponent) event.getTargetComponent(), eventGenerator);
    }
}
