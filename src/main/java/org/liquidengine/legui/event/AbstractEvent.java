package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Controller;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public abstract class AbstractEvent {
    private final Controller controller;

    public AbstractEvent(Controller controller) {
        this.controller = controller;
    }

    public final Controller getController() {
        return controller;
    }
}
