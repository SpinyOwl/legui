package org.liquidengine.legui.processor.system.component.widget;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CursorPosEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Alexander on 28.08.2016.
 */
public class WidgetCursorPosProcessor implements LeguiComponentEventProcessor<Widget, CursorPosEvent> {
    @Override
    public void process(Widget gui, CursorPosEvent event, LeguiContext leguiContext) {
        Vector2f cp = leguiContext.getCursorPosition();
        Vector2f cpp = leguiContext.getCursorPositionPrev();
        if (gui.isVisible() && leguiContext.getFocusedGui() == gui && leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {

            Vector2f p = gui.getPosition();
            Vector2f s = gui.getSize();
            float h = gui.getTitleHeight();

            if ((cp.x >= p.x && cp.x <= p.x + s.x) || (cpp.x >= p.x && cpp.x <= p.x + s.x)) {
                if ((cp.y >= p.y && cp.y <= p.y + h) || (cpp.y >= p.y && cpp.y <= p.y + h)) {
                    Vector2f delta = new Vector2f(cp).sub(cpp);
                    Vector2f widP = gui.getPosition();
                    widP.add(delta);

                }
            }
        }
    }
}
