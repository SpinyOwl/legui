package com.spinyowl.legui.component.misc.listener.radiobutton;

import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import com.spinyowl.legui.component.RadioButton;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;

/**
 * RadioButton {@link MouseClickEvent} event listener. Used to update state of radio buttons in
 * current radio button group.
 */
public class RadioButtonClickEventListener implements MouseClickEventListener {

  /**
   * Used to handle {@link MouseClickEvent}
   *
   * @param event event to handle.
   */
  @Override
  public void process(MouseClickEvent event) {
    if (event.getAction() == CLICK) {
      RadioButton component = (RadioButton) event.getTargetComponent();
      component.setChecked(true);
    }
  }
}
