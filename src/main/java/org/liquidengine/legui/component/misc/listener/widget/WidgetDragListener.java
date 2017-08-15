package org.liquidengine.legui.component.misc.listener.widget;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.MouseDragEventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class WidgetDragListener implements MouseDragEventListener {

    private Widget widget;

    public WidgetDragListener(Widget widget) {
        this.widget = widget;
    }

    @Override
    public void process(MouseDragEvent event) {
        widget.getPosition().add(event.getDelta());
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
