package com.spinyowl.legui.component.event.widget;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.event.Event;
import com.spinyowl.legui.system.context.Context;


public class WidgetCloseEvent<T extends Widget> extends Event<T> {

  public WidgetCloseEvent(T component, Context context, Frame frame) {
    super(component, context, frame);
  }
}
