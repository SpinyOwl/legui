package org.liquidengine.legui.processor.pre;

import org.joml.Vector2f;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.processor.SystemEventPreprocessor;

/**
 * Created by Alexander on 31.10.2016.
 */
public class SystemCursorPosEventPreprocessor implements SystemEventPreprocessor<SystemCursorPosEvent> {
    @Override
    public void process(SystemCursorPosEvent event, LeguiContext context) {
        context.setCursorPositionPrev(new Vector2f(context.getCursorPosition()));
        context.setCursorPosition(new Vector2f(event.fx, event.fy));
    }
}
