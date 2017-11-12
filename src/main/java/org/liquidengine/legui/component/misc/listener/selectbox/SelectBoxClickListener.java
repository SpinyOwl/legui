package org.liquidengine.legui.component.misc.listener.selectbox;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

/**
 * Default mouse click listener for selectbox.
 * <p>
 * Used to expand/collapse selectbox if clicked on it.
 */
public class SelectBoxClickListener implements MouseClickEventListener {

    private SelectBox selectBox;

    public SelectBoxClickListener(SelectBox selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(MouseClickEvent event) {
        SelectBox box = selectBox;
        if (event.getAction() == CLICK) {
            box.setCollapsed(!box.isCollapsed());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
