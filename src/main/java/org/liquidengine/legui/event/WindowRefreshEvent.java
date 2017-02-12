package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowRefreshEvent extends AbstractEvent {

    public WindowRefreshEvent(Component controller) {
        super(controller);
    }
}
