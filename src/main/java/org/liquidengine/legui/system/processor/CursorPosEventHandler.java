package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCursorPosEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class CursorPosEventHandler implements SystemEventHandler<SystemCursorPosEvent> {
    @Override
    public void process(SystemCursorPosEvent event, Frame frame, Context context) {
        Vector2f cursorPosition = new Vector2f(event.fx, event.fy);
        context.setCursorPositionPrev(new Vector2f(context.getCursorPosition()));
        context.setCursorPosition(cursorPosition);
//        context.setMouseTargetGui(context.getFrame().getComponentAt(cursorPosition));
    }
}
