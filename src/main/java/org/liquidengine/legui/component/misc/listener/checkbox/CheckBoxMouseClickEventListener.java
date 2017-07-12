package org.liquidengine.legui.component.misc.listener.checkbox;

import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

/**
 * MouseClickEventListener for checkbox, used to toggle checkbox state on mouse click.
 */
public class CheckBoxMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        CheckBox checkBox = (CheckBox) event.getComponent();
        if (event.getAction() == CLICK) {
            checkBox.setChecked(!checkBox.isChecked());
        }
    }

    /**
     * Used to compare instances of this event listener.
     *
     * @param obj object to compare.
     * @return true if equals.
     */
    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof CheckBoxMouseClickEventListener;
    }
}
