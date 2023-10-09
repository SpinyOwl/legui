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
public class ScrollEvent<T extends Component> extends Event<T> {

  private final double xoffset;
  private final double yoffset;

  public ScrollEvent(T component, Context context, Frame frame, double xoffset, double yoffset) {
    super(component, context, frame);
    this.xoffset = xoffset;
    this.yoffset = yoffset;
  }
}
