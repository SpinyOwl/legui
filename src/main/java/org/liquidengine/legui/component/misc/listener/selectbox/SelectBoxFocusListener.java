package org.liquidengine.legui.component.misc.listener.selectbox;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.listener.FocusEventListener;

/**
 * Default focus listener for selectbox. Used to collapse selectbox if it loses focus.
 */
public class SelectBoxFocusListener implements FocusEventListener {

    private SelectBox selectBox;

    public SelectBoxFocusListener(SelectBox selectBox) {
        this.selectBox = selectBox;
    }

    @Override
    public void process(FocusEvent event) {
        if (!event.isFocused() && !selectBox.isCollapsed()) {
            boolean   collapse  = true;
            Component nextFocus = event.getNextFocus();
            for (SelectBox.SelectBoxElement selectBoxElement : selectBox.getSelectBoxElements()) {
                if (nextFocus == selectBoxElement) {
                    collapse = false;
                }
            }
            if (nextFocus == selectBox.getExpandButton() ||
                    nextFocus == selectBox.getSelectionButton() ||
                    nextFocus == selectBox.getSelectionListPanel() ||
                    nextFocus == selectBox.getSelectionListPanel().getVerticalScrollBar()) {
                collapse = false;
            }
            if (selectBox.isCollapsed() != collapse) {
                selectBox.setCollapsed(collapse);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
