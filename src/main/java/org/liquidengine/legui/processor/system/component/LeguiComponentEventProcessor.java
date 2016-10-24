package org.liquidengine.legui.processor.system.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.SystemEvent;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public interface LeguiComponentEventProcessor<GUI extends Component, EVENT extends SystemEvent> {
    void process(GUI gui, EVENT event, LeguiContext leguiContext);
}
