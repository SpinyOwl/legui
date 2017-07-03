package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class LeguiWindowRefreshEvent<T extends Component> extends LeguiEvent<T> {

    public LeguiWindowRefreshEvent(T component, LeguiContext context) {
        super(component, context);
    }
}