package com.spinyowl.legui.component.misc.listener.dialog;

import com.spinyowl.legui.component.Dialog;
import com.spinyowl.legui.component.event.widget.WidgetCloseEvent;
import com.spinyowl.legui.component.event.widget.WidgetCloseEventListener;

/**
 * Close event listener for dialog. When dialog closed dialog layer with dialog should be removed
 * from frame.
 */
public class DialogCloseEventListener implements WidgetCloseEventListener<WidgetCloseEvent> {

  private Dialog dialog;

  public DialogCloseEventListener(Dialog dialog) {
    this.dialog = dialog;
  }

  /**
   * Used to handle {@link WidgetCloseEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  public void process(WidgetCloseEvent event) {
    dialog.close();
  }

}
