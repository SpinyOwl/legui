package com.spinyowl.legui.component.misc.listener.textinput;

import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.component.event.textinput.TextInputContentChangeEvent;
import com.spinyowl.legui.component.misc.listener.text.CutTextEventListener;
import com.spinyowl.legui.event.KeyEvent;
import com.spinyowl.legui.event.KeyboardEvent;
import com.spinyowl.legui.listener.EventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class CutTextInputKeyboardEventListener extends CutTextEventListener
    implements EventListener<KeyboardEvent> {

  /**
   * Used to handle {@link KeyEvent}.
   *
   * @param event event to handle.
   */
  @Override
  public void process(KeyboardEvent event) {
    processCut(event, (oldText, newText) -> EventProcessorProvider.getInstance().pushEvent(
        new TextInputContentChangeEvent<>((TextInput) event.getTargetComponent(),
            event.getContext(), event.getFrame(), oldText, newText)));
  }
}
