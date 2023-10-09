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
public class WindowSizeEvent<T extends Component> extends Event<T> {

  private final int width;
  private final int height;

  public WindowSizeEvent(T component, Context context, Frame frame, int width, int height) {
    super(component, context, frame);
    this.width = width;
    this.height = height;
  }

}
