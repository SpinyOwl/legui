package com.spinyowl.legui.component.misc.listener.checkbox;

import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import com.spinyowl.legui.component.CheckBox;
import com.spinyowl.legui.component.event.checkbox.CheckBoxChangeValueEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

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
      EventProcessorProvider.getInstance().pushEvent(
          new CheckBoxChangeValueEvent<>(checkBox, event.getContext(), event.getFrame(), checked,
              !checked));
    }
  }
}
