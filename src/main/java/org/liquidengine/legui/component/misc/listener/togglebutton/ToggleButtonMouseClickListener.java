package org.liquidengine.legui.component.misc.listener.togglebutton;

import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

/**
 * @author ShchAlexander.
 */
public class ToggleButtonMouseClickListener implements MouseClickEventListener {

    @Override
    public void process(MouseClickEvent event) {
        ToggleButton toggleButton = (ToggleButton) event.getTargetComponent();
        if (event.getAction() == MouseClickEvent.MouseClickAction.CLICK) {
            toggleButton.setToggled(!toggleButton.isToggled());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
