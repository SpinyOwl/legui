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
public class WindowCloseEvent<T extends Component> extends Event<T> {

  public WindowCloseEvent(T component, Context context, Frame frame) {
    super(component, context, frame);
  }

}
