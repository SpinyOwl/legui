package com.spinyowl.legui.component.misc.listener.splitpanel;

import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.EventListener;

public class SplitPanelSeparatorClickListener implements EventListener<MouseClickEvent> {

  private SplitPanelSeparatorListenerDelegate delegate;

  public SplitPanelSeparatorClickListener(SplitPanelSeparatorListenerDelegate delegate) {
    this.delegate = delegate;
  }

  /**
   * Used to handle specific event.
   *
   * @param event event to handle.
   */
  @Override
  public void process(MouseClickEvent event) {
    delegate.process(event);
  }
}
