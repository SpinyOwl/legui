package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;


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

  public boolean isEntered() {
    return entered;
  }

  public Vector2f getDelta() {
    return delta;
  }

  public Vector2f getCursorPosition() {
    return cursorPosition;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("targetComponent", getTargetComponent().getClass().getSimpleName())
        .append("entered", entered)
        .append("delta", delta)
        .append("cursorPosition", cursorPosition)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CursorEnterEvent<?> that = (CursorEnterEvent<?>) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(entered, that.entered)
        .append(delta, that.delta)
        .append(cursorPosition, that.cursorPosition)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(entered)
        .append(delta)
        .append(cursorPosition)
        .toHashCode();
  }
}
