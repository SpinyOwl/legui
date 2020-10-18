package org.liquidengine.legui.system.handler;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.DropEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemDropEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class DropEventHandler extends AbstractSystemEventHandler<SystemDropEvent> {

    @Override
    protected boolean handle(SystemDropEvent event, Layer layer, Context context, Frame frame) {
        List<Component> targetComponentList = SehUtil.getTargetComponentList(layer, Mouse.getCursorPosition());
        List<String> strings = Arrays.stream(event.strings).collect(Collectors.toList());
        for (Component component : targetComponentList) {
            EventProcessorProvider.getInstance().pushEvent(new DropEvent<>(component, context, frame, strings));
        }
        return false;
    }
}
