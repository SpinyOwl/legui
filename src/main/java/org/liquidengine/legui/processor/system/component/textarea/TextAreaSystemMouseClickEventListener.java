package org.liquidengine.legui.processor.system.component.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextAreaSystemMouseClickEventListener implements SystemEventListener<TextArea, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, TextArea gui, LeguiContext leguiContext) {
        if (gui != leguiContext.getFocusedGui()) {
            gui.setCaretPosition(gui.getTextState().length());
        } else if (event.action == GLFW_PRESS) {
            gui.setCaretPosition(gui.getMouseCaretPosition());
        }
    }
}
