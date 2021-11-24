package com.spinyowl.legui.component.event.label;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.event.Event;
import com.spinyowl.legui.system.context.Context;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class LabelContentChangeEvent extends Event<Label> {

  /**
   * Old value.
   */
  private final String oldValue;
  /**
   * New value.
   */
  private final String newValue;

  public LabelContentChangeEvent(Label component, Context context, Frame frame, String oldValue,
      String newValue) {
    super(component, context, frame);
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  /**
   * Returns old value.
   *
   * @return old value.
   */
  public String getOldValue() {
    return oldValue;
  }

  /**
   * Returns new value.
   *
   * @return new value.
   */
  public String getNewValue() {
    return newValue;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("oldValue", oldValue)
        .append("newValue", newValue)
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

    LabelContentChangeEvent that = (LabelContentChangeEvent) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(oldValue, that.oldValue)
        .append(newValue, that.newValue)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(oldValue)
        .append(newValue)
        .toHashCode();
  }
}
