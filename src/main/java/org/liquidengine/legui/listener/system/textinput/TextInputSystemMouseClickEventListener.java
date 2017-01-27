package org.liquidengine.legui.listener.system.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.listener.system.def.DefaultSystemMouseClickEventListener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextInputSystemMouseClickEventListener implements SystemEventListener<TextInput, SystemMouseClickEvent> {
    private DefaultSystemMouseClickEventListener defaultSystemMouseClickEventListener = new DefaultSystemMouseClickEventListener();

    @Override
    public void update(SystemMouseClickEvent event, TextInput gui, LeguiContext leguiContext) {
        defaultSystemMouseClickEventListener.update(event, gui, leguiContext);
        int mouseCaretPosition = gui.getMouseCaretPosition();
        if (event.action == GLFW_PRESS) {
            gui.setCaretPosition(mouseCaretPosition);
            gui.setStartSelectionIndex(mouseCaretPosition);
            gui.setEndSelectionIndex(mouseCaretPosition);
        }
    }
}
