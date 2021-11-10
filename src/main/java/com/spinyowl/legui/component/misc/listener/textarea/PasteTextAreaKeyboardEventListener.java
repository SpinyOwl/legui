package com.spinyowl.legui.component.misc.listener.textarea;

import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.component.event.textarea.TextAreaFieldContentChangeEvent;
import com.spinyowl.legui.component.misc.listener.text.PasteTextEventListener;
import com.spinyowl.legui.event.KeyEvent;
import com.spinyowl.legui.event.KeyboardEvent;
import com.spinyowl.legui.listener.EventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class PasteTextAreaKeyboardEventListener extends PasteTextEventListener
    implements EventListener<KeyboardEvent> {

  /**
   * Used to handle {@link KeyEvent}.
   *
   * @param event event to handle.
   */
  @Override
  public void process(KeyboardEvent event) {
    processPaste(event, (oldText, newText) ->
        EventProcessorProvider.getInstance().pushEvent(
            new TextAreaFieldContentChangeEvent<>(
                (TextAreaField) event.getTargetComponent(),
                event.getContext(), event.getFrame(), oldText, newText)));
  }
}
