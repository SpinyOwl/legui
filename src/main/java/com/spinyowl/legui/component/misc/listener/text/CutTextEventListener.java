package com.spinyowl.legui.component.misc.listener.text;

import static com.spinyowl.legui.component.misc.listener.TextComponentShortcutUtil.cut;
import static com.spinyowl.legui.input.KeyCode.KEY_X;

import com.spinyowl.legui.component.TextComponent;
import com.spinyowl.legui.event.KeyboardEvent;
import com.spinyowl.legui.input.KeyAction;
import com.spinyowl.legui.input.KeyCode;
import com.spinyowl.legui.input.KeyMod;
import com.spinyowl.legui.input.Keyboard;
import java.util.function.BiConsumer;

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
