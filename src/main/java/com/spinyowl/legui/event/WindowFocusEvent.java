package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class WindowFocusEvent<T extends Component> extends Event<T> {

  private final boolean focused;

  public WindowFocusEvent(T component, Context context, Frame frame, boolean focused) {
    super(component, context, frame);
    this.focused = focused;
  }

}
