package org.liquidengine.legui.processor.pre;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.SystemEventPreprocessor;

/**
 * Created by Shcherbin Alexander on 10/31/2016.
 */
public class SystemMouseClickEventPreprocessor implements SystemEventPreprocessor<SystemMouseClickEvent> {
    @Override
    public void process(SystemMouseClickEvent event, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        Component mouseTargetGui = context.getMouseTargetGui();
        if (focusedGui != null && focusedGui != mouseTargetGui) {
            focusedGui.setFocused(false);
            context.setFocusedGui(null);
        }
        if (mouseTargetGui != null) {
            mouseTargetGui.setFocused(true);
            context.setFocusedGui(mouseTargetGui);
        }
    }
}
