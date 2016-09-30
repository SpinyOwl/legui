package org.liquidengine.legui.processor.system.component.checkbox;

import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.MouseClickEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Alexander on 28.08.2016.
 */
public class CheckBoxMouseClickProcessor implements LeguiComponentEventProcessor<CheckBox, MouseClickEvent> {

    @Override
    public void process(CheckBox checkBox, MouseClickEvent event, LeguiContext leguiContext) {
        if (leguiContext.getFocusedGui() == checkBox) {
            if (event.action == GLFW_RELEASE) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        }
    }
}
