package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class CharEvent<T extends Component> extends Event<T> {

  private final int codepoint;

  public CharEvent(T component, Context context, Frame frame, int codepoint) {
    super(component, context, frame);
    this.codepoint = codepoint;
  }

  public int getCodepoint() {
    return codepoint;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("targetComponent", getTargetComponent().getClass().getSimpleName())
        .append("codepoint", codepoint)
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

    CharEvent<?> charEvent = (CharEvent<?>) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(codepoint, charEvent.codepoint)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(codepoint)
        .toHashCode();
  }
}
