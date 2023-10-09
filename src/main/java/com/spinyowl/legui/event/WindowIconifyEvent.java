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

public class WindowIconifyEvent<T extends Component> extends Event<T> {

  private final boolean iconified;

  public WindowIconifyEvent(T component, Context context, Frame frame, boolean iconified) {
    super(component, context, frame);
    this.iconified = iconified;
  }

}
