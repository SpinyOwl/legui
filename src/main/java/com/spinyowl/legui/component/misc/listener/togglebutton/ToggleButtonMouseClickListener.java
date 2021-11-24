package com.spinyowl.legui.component.misc.listener.togglebutton;

import com.spinyowl.legui.component.ToggleButton;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;


public class ToggleButtonMouseClickListener implements MouseClickEventListener {

  @Override
  public void process(MouseClickEvent event) {
    ToggleButton toggleButton = (ToggleButton) event.getTargetComponent();
    if (event.getAction() == MouseClickEvent.MouseClickAction.CLICK && toggleButton.isEnabled()) {
      toggleButton.setToggled(!toggleButton.isToggled());
    }
  }
}
