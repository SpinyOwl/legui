package org.liquidengine.legui.component.misc.listener.textarea;

import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldContentChangeEvent;
import org.liquidengine.legui.component.misc.listener.text.CutTextEventListener;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class CutTextAreaKeyboardEventListener extends CutTextEventListener
        implements EventListener<KeyboardEvent> {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyboardEvent event) {
        processCut(event, (oldText, newText) -> EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldContentChangeEvent<>((TextAreaField) event.getTargetComponent(), event.getContext(), event.getFrame(), oldText, newText)));
    }
}
