package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class WindowPosEvent<T extends Component> extends Event<T> {

  private final int xpos;
  private final int ypos;

  public WindowPosEvent(T component, Context context, Frame frame, int xpos, int ypos) {
    super(component, context, frame);
    this.xpos = xpos;
    this.ypos = ypos;
  }

  public int getXpos() {
    return xpos;
  }

  public int getYpos() {
    return ypos;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("targetComponent", getTargetComponent().getClass().getSimpleName())
        .append("xpos", xpos)
        .append("ypos", ypos)
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

    WindowPosEvent<?> that = (WindowPosEvent<?>) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(xpos, that.xpos)
        .append(ypos, that.ypos)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(xpos)
        .append(ypos)
        .toHashCode();
  }
}
