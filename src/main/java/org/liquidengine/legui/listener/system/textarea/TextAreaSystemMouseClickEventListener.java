package org.liquidengine.legui.listener.system.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.listener.system.def.DefaultSystemMouseClickEventListener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextAreaSystemMouseClickEventListener implements SystemEventListener<TextArea, SystemMouseClickEvent> {
    private DefaultSystemMouseClickEventListener defaultSystemMouseClickEventListener = new DefaultSystemMouseClickEventListener();
    @Override
    public void update(SystemMouseClickEvent event, TextArea gui, LeguiContext leguiContext) {
        defaultSystemMouseClickEventListener.update(event,gui,leguiContext);
        if (event.action == GLFW_PRESS) {
            int mouseCaretPosition = gui.getMouseCaretPosition();
            gui.setCaretPosition(mouseCaretPosition);
            gui.setStartSelectionIndex(mouseCaretPosition);
            gui.setEndSelectionIndex(mouseCaretPosition);
        }
    }
}
