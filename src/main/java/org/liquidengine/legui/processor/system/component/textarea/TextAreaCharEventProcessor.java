package org.liquidengine.legui.processor.system.component.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCharEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.liquidengine.legui.util.Util.cpToStr;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextAreaCharEventProcessor implements LeguiComponentEventProcessor<TextArea, SystemCharEvent> {
    @Override
    public void process(TextArea gui, SystemCharEvent event, LeguiContext leguiContext) {
        if (gui.isFocused() && gui.isEditable()) {
            String str = cpToStr(event.codepoint);
            int caretPosition = gui.getCaretPosition();
            gui.getTextState().insert(caretPosition, str);
            gui.setCaretPosition(caretPosition + str.length());
        }
    }
}
