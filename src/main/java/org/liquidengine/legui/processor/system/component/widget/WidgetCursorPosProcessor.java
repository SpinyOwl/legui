package org.liquidengine.legui.processor.system.component.widget;

import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

/**
 * Created by Alexander on 28.08.2016.
 */
public class WidgetCursorPosProcessor implements LeguiComponentEventProcessor<Widget, SystemCursorPosEvent> {
    @Override
    public void process(Widget gui, SystemCursorPosEvent event, LeguiContext leguiContext) {
//        Vector2f cp = leguiContext.getCursorPosition();
//        Vector2f cpp = leguiContext.getCursorPositionPrev();
//        if (gui.isVisible() && leguiContext.getFocusedGui() == gui && leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
//
//            Vector2f p = gui.getPosition();
//            Vector2f s = gui.getSize();
//            float h = gui.getTitleHeight();
//
//            if ((cp.x >= p.x && cp.x <= p.x + s.x) || (cpp.x >= p.x && cpp.x <= p.x + s.x)) {
//                if ((cp.y >= p.y && cp.y <= p.y + h) || (cpp.y >= p.y && cpp.y <= p.y + h)) {
//                    Vector2f delta = new Vector2f(cp).sub(cpp);
//                    Vector2f widP = gui.getPosition();
//                    widP.add(delta);
//
//                }
//            }
//        }
    }
}
