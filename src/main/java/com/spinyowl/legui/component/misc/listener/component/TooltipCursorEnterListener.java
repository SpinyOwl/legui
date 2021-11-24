package com.spinyowl.legui.component.misc.listener.component;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Tooltip;
import com.spinyowl.legui.event.CursorEnterEvent;
import com.spinyowl.legui.listener.CursorEnterEventListener;

/**
 * Default event listener for {@link CursorEnterEvent} to add tooltip to tooltip layer and make it
 * visible or not visible.
 */
public class TooltipCursorEnterListener implements CursorEnterEventListener {

  /**
   * Used to process {@link CursorEnterEvent}.
   *
   * @param event event to process.
   */
  @Override
  public void process(CursorEnterEvent event) {
    Component component = event.getTargetComponent();
    Tooltip tooltip = component.getTooltip();
    if (tooltip != null) {
      if (event.isEntered()) {
        event.getFrame().getTooltipLayer().add(tooltip);
      } else {
        event.getFrame().getTooltipLayer().remove(tooltip);
      }
    }
  }
}
