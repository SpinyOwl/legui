package org.liquidengine.legui.component.misc.listener.checkbox;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.event.checkbox.CheckBoxChangeValueEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

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
        CheckBox checkBox = (CheckBox) event.getTargetComponent();
        if (event.getAction() == CLICK && checkBox.isEnabled()) {
            boolean checked = checkBox.isChecked();
            checkBox.setChecked(!checked);
            EventProcessorProvider.getInstance().pushEvent(new CheckBoxChangeValueEvent<>(checkBox, event.getContext(), event.getFrame(), checked, !checked));
        }
    }
}
