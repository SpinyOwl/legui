package com.spinyowl.legui.component.misc.listener.widget;

import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.component.event.widget.WidgetCloseEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;


public class WidgetCloseButMouseClickEventListener implements MouseClickEventListener {

  private Widget widget;

  public WidgetCloseButMouseClickEventListener(Widget widget) {
    this.widget = widget;
  }

  @Override
  public void process(MouseClickEvent event) {
    if (CLICK == event.getAction()) {
      widget.hide();
      EventProcessorProvider.getInstance()
          .pushEvent(new WidgetCloseEvent<>(widget, event.getContext(), event.getFrame()));
    }
  }
}
