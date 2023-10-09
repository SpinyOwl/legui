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
public class WindowPosEvent<T extends Component> extends Event<T> {

  private final int xpos;
  private final int ypos;

  public WindowPosEvent(T component, Context context, Frame frame, int xpos, int ypos) {
    super(component, context, frame);
    this.xpos = xpos;
    this.ypos = ypos;
  }

}
