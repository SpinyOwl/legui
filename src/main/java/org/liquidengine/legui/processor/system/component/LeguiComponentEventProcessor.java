package org.liquidengine.legui.processor.system.component;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.LeguiSystemEvent;

/**
 * Created by Shcherbin Alexander on 8/25/2016.
 */
public interface LeguiComponentEventProcessor<GUI extends Component, EVENT extends LeguiSystemEvent> {
    void process(GUI gui, EVENT event, LeguiContext processorState);
}
