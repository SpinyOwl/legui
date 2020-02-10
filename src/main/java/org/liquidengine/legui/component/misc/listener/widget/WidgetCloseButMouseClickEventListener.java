package org.liquidengine.legui.component.misc.listener.widget;

import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.event.widget.WidgetCloseEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * @author ShchAlexander.
 */
public class WidgetCloseButMouseClickEventListener implements MouseClickEventListener {

    private Widget widget;

    public WidgetCloseButMouseClickEventListener(Widget widget) {
        this.widget = widget;
    }

    @Override
    public void process(MouseClickEvent event) {
        if (CLICK == event.getAction()) {
            widget.hide();
            EventProcessorProvider.getInstance().pushEvent(new WidgetCloseEvent<>(widget, event.getContext(), event.getFrame()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
