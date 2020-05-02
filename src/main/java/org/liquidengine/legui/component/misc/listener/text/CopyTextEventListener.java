package org.liquidengine.legui.component.misc.listener.text;

import org.liquidengine.legui.component.TextComponent;
import org.liquidengine.legui.component.misc.listener.TextComponentShortcutUtil;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.listener.EventListener;

import static org.liquidengine.legui.input.KeyCode.KEY_C;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class CopyTextEventListener implements EventListener<KeyboardEvent>  {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    public void process(KeyboardEvent event) {
        boolean pressed = event.getAction() != KeyAction.RELEASE;
        if (!pressed) {
            return;
        }

        KeyCode copyKey = Keyboard.getCopyShortcut().getKey();
        KeyCode keyCode = event.getKey().getKeyCode();
        if (event.getMods().contains(KeyMod.CONTROL)
                && (copyKey != null && keyCode == copyKey || keyCode == KEY_C)) {
            TextComponentShortcutUtil.copy((TextComponent) event.getTargetComponent());
        }
    }

}
