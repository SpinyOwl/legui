package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Controller;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class CursorEnterEvent extends AbstractEvent {
    public CursorEnterEvent(Controller controller) {
        super(controller);
    }
}
