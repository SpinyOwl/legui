package com.spinyowl.legui.component.misc.listener.text;

import static com.spinyowl.legui.component.misc.listener.TextComponentShortcutUtil.paste;
import static com.spinyowl.legui.input.KeyCode.KEY_V;

import com.spinyowl.legui.component.TextComponent;
import com.spinyowl.legui.event.KeyEvent;
import com.spinyowl.legui.event.KeyboardEvent;
import com.spinyowl.legui.input.KeyAction;
import com.spinyowl.legui.input.KeyCode;
import com.spinyowl.legui.input.KeyMod;
import com.spinyowl.legui.input.Keyboard;
import java.util.function.BiConsumer;

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
        && (pasteKey != null && keyCode == pasteKey || keyCode == KEY_V)) {
      paste((TextComponent) event.getTargetComponent(), eventGenerator);
    }
  }
}
