package org.liquidengine.legui.listener.system.radiobutton;

import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public class RadioButtonSystemMouseClickListener implements SystemEventListener<RadioButton, SystemMouseClickEvent> {
    public void update(SystemMouseClickEvent event, RadioButton gui, LeguiContext leguiContext) {
        if (leguiContext.getFocusedGui() == gui) {
            if (event.action == GLFW_RELEASE) {
                gui.setSelected(true);
            }
        }
    }
}
