package org.liquidengine.legui.component.misc.listener.widget;

import org.liquidengine.legui.component.misc.event.widget.WidgetCloseEvent;
import org.liquidengine.legui.listener.EventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface WidgetCloseEventListener<T extends WidgetCloseEvent> extends EventListener<T> {

    void process(WidgetCloseEvent event);
}
