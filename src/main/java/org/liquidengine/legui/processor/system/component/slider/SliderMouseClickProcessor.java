package org.liquidengine.legui.processor.system.component.slider;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.liquidengine.legui.util.Util;

import static org.liquidengine.legui.processor.system.component.slider.SliderUpdateUtil.updateSliderValue;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public class SliderMouseClickProcessor implements LeguiComponentEventProcessor<Slider, SystemMouseClickEvent> {
    @Override
    public final void process(Slider gui, SystemMouseClickEvent event, LeguiContext leguiContext) {
        if (!gui.isVisible()) return;
        if (!gui.isEnabled()) return;
        if (event.action != GLFW_PRESS) return;

        Vector2f pos = Util.calculatePosition(gui);

        Vector2f cursorPosition = leguiContext.getCursorPosition();
        if (Orientation.VERTICAL.equals(gui.getOrientation())) {
            float value = 100f * (pos.y + gui.getSize().y - cursorPosition.y) / gui.getSize().y;
            updateSliderValue(gui, value, leguiContext);
        } else {
            float value = 100f * (cursorPosition.x - pos.x) / gui.getSize().x;
            updateSliderValue(gui, value, leguiContext);
        }
    }
}
