package org.liquidengine.legui.processor.system.component.radiobutton;

import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.MouseClickEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public class RadioButtonMouseClickProcessor implements LeguiComponentEventProcessor<RadioButton, MouseClickEvent> {
    public void process(RadioButton gui, MouseClickEvent event, LeguiContext processorState) {
        if (processorState.getFocusedGui() == gui) {
            if (event.action == GLFW_RELEASE) {
                gui.setSelected(true);
            }
        }
    }
}
