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
public class CharEvent<T extends Component> extends Event<T> {

  private final int codepoint;

  public CharEvent(T component, Context context, Frame frame, int codepoint) {
    super(component, context, frame);
    this.codepoint = codepoint;
  }
}
