package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowCloseEvent extends AbstractEvent {

    public WindowCloseEvent(Component controller, Frame frame) {
        super(controller, frame);
    }

}
