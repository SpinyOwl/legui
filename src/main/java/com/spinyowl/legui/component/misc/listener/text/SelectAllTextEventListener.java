package com.spinyowl.legui.component.misc.listener.text;

import static com.spinyowl.legui.input.KeyCode.KEY_A;

import com.spinyowl.legui.component.TextComponent;
import com.spinyowl.legui.component.optional.TextState;
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
