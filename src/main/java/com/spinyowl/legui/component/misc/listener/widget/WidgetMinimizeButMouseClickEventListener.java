package com.spinyowl.legui.component.misc.listener.widget;

import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;


public class WidgetMinimizeButMouseClickEventListener implements MouseClickEventListener {

  private Widget widget;

  public WidgetMinimizeButMouseClickEventListener(Widget widget) {
    this.widget = widget;
  }

  public void process(MouseClickEvent event) {
    if (CLICK == event.getAction()) {
      boolean newValue = !widget.isMinimized();
      widget.getMinimizeButton().getStyle().getBackground()
          .setIcon(newValue ? widget.getMaximizeIcon() : widget.getMinimizeIcon());
      widget.setMinimized(newValue);
    }
  }
}
