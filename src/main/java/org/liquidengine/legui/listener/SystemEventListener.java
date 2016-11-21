package org.liquidengine.legui.listener;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.SystemEvent;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public interface SystemEventListener<C extends Component, E extends SystemEvent> extends Serializable {

    void update(E event, C component, LeguiContext context);
}
