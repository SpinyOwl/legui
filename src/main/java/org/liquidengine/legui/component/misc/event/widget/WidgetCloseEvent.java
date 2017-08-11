package org.liquidengine.legui.component.misc.event.widget;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class WidgetCloseEvent<T extends Widget> extends Event<T> {
    public WidgetCloseEvent(T component, Context context, Frame frame) {
        super(component, context, frame);
    }
}
