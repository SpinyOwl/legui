package org.liquidengine.legui.processor.system.component.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextInputMouseClickProcessor implements LeguiComponentEventProcessor<TextInput, SystemMouseClickEvent> {
    @Override
    public void process(TextInput gui, SystemMouseClickEvent event, LeguiContext leguiContext) {
        if (gui != leguiContext.getFocusedGui()) {
            gui.setCaretPosition(gui.getTextState().length());
        } else if (event.action == GLFW_PRESS) {
            gui.setCaretPosition(gui.getMouseCaretPosition());
        }
    }
}
