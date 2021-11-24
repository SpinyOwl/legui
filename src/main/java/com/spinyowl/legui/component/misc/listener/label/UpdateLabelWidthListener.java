package com.spinyowl.legui.component.misc.listener.label;

import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.event.label.LabelContentChangeEvent;
import com.spinyowl.legui.component.event.label.LabelWidthChangeEvent;
import com.spinyowl.legui.listener.EventListener;

public class UpdateLabelWidthListener implements EventListener<LabelWidthChangeEvent> {

  /**
   * Used to handle {@link LabelContentChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  public void process(LabelWidthChangeEvent event) {
    Label label = event.getTargetComponent();
    float textWidth = label.getTextState().getTextWidth();
    label.getSize().x = textWidth;
  }

}
