package org.liquidengine.legui.processor.system.component.checkbox;

import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Alexander on 28.08.2016.
 */
public class CheckBoxMouseClickListener implements SystemEventListener<CheckBox, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, CheckBox checkBox, LeguiContext leguiContext) {
        if (leguiContext.getFocusedGui() == checkBox) {
            if (event.action == GLFW_RELEASE) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        }
    }
}
