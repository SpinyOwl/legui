package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;


public class WindowCloseEvent<T extends Component> extends Event<T> {

  public WindowCloseEvent(T component, Context context, Frame frame) {
    super(component, context, frame);
  }

}
