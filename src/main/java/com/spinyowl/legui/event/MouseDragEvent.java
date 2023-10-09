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
public class MouseDragEvent<T extends Component> extends Event<T> {

  private final Vector2f delta;

  public MouseDragEvent(T component, Context context, Frame frame, Vector2f delta) {
    super(component, context, frame);
    this.delta = delta;
  }
}
