package org.liquidengine.legui.component.misc.listener.selectbox;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * Default mouse click listener for selectbox. Used to collapse selectbox if it loses focus and to expand/collapse if clicked on it.
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
