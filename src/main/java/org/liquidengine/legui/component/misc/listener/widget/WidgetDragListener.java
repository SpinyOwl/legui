package org.liquidengine.legui.component.misc.listener.widget;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.event.component.ChangePositionEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * @author ShchAlexander.
 */
public class WidgetDragListener implements MouseDragEventListener {

    public static final float THRESHOLD = 0.0001f;
    private Widget widget;

    public WidgetDragListener(Widget widget) {
        this.widget = widget;
    }

    @Override
    public void process(MouseDragEvent event) {
        Vector2f oldPos = new Vector2f(widget.getPosition());
        widget.getPosition().add(event.getDelta());
        Vector2f newPos = widget.getPosition();
        if (!oldPos.equals(newPos, THRESHOLD)) {
            EventProcessorProvider.getInstance().pushEvent(new ChangePositionEvent(widget, event.getContext(), event.getFrame(), oldPos, newPos));
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
