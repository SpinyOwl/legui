package org.liquidengine.legui.processor.system.component.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CharEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.liquidengine.legui.util.Util.cpToStr;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextInputCharEventProcessor implements LeguiComponentEventProcessor<TextInput, CharEvent> {
    @Override
    public void process(TextInput gui, CharEvent event, LeguiContext processorState) {
        if (gui.isFocused() && gui.isEditable()) {
            String str = cpToStr(event.codepoint);
            int caretPosition = gui.getCaretPosition();
            gui.getTextState().insert(caretPosition, str);
            gui.setCaretPosition(caretPosition + str.length());
        }
    }
}
