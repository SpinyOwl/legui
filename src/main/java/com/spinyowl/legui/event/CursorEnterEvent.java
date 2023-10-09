package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joml.Vector2f;

@Getter
@EqualsAndHashCode
@ToString
public class CursorEnterEvent<T extends Component> extends Event<T> {

  private final boolean entered;
  private final Vector2f delta;
  private final Vector2f cursorPosition;

  public CursorEnterEvent(T component, Context context, Frame frame, boolean entered,
      Vector2f delta, Vector2f cursorPosition) {
    super(component, context, frame);
    this.entered = entered;
    this.delta = delta;
    this.cursorPosition = cursorPosition;
  }

}
