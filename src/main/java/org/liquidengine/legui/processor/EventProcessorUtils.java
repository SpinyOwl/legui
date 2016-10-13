package org.liquidengine.legui.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;

import java.util.List;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class EventProcessorUtils {

    public static void release(Component gui, Component focused) {
        if (gui != focused) {
            gui.setFocused(false);
        }
        if (gui instanceof ComponentContainer) {
            ComponentContainer container = ((ComponentContainer) gui);
            List<Component> all = container.getComponents();
            for (Component element : all) {
                release(element, focused);
            }
        }
    }
}
