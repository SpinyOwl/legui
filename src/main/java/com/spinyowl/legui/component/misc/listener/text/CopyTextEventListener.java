package com.spinyowl.legui.component.misc.listener.text;

import static com.spinyowl.legui.input.KeyCode.KEY_C;

import com.spinyowl.legui.component.TextComponent;
import com.spinyowl.legui.component.misc.listener.TextComponentShortcutUtil;
import com.spinyowl.legui.event.KeyEvent;
import com.spinyowl.legui.event.KeyboardEvent;
import com.spinyowl.legui.input.KeyAction;
import com.spinyowl.legui.input.KeyCode;
import com.spinyowl.legui.input.KeyMod;
import com.spinyowl.legui.input.Keyboard;
import com.spinyowl.legui.listener.EventListener;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class CopyTextEventListener implements EventListener<KeyboardEvent> {

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
