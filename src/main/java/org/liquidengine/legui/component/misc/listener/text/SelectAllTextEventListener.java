package org.liquidengine.legui.component.misc.listener.text;

import org.liquidengine.legui.component.TextComponent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.input.Keyboard;
import org.liquidengine.legui.listener.EventListener;

import static org.liquidengine.legui.input.KeyCode.KEY_A;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class SelectAllTextEventListener implements EventListener<KeyboardEvent> {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    public void process(KeyboardEvent event) {
        TextComponent gui = (TextComponent) event.getTargetComponent();
        boolean pressed = event.getAction() != KeyAction.RELEASE;
        if (!pressed) {
            return;
        }

        KeyCode selectAllKey = Keyboard.getSelectAllShortcut().getKey();
        KeyCode keyCode = event.getKey().getKeyCode();
        if (event.getMods().contains(KeyMod.CONTROL)
                && (selectAllKey != null && keyCode == selectAllKey || keyCode == KEY_A)) {
            TextState textState = gui.getTextState();
            gui.getTextState().setStartSelectionIndex(0);
            gui.getTextState().setEndSelectionIndex(textState.length());
            gui.getTextState().setCaretPosition(textState.length());
        }
    }
}
