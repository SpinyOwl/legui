package com.spinyowl.legui.component.event.label;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.event.Event;
import com.spinyowl.legui.system.context.Context;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LabelWidthChangeEvent extends Event<Label> {

  private final float width;

  public LabelWidthChangeEvent(Label targetComponent, Context context, Frame frame, float width) {
    super(targetComponent, context, frame);
    this.width = width;
  }

  public float getWidth() {
    return width;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("width", width)
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
    if (!super.equals(o)) {
      return false;
    }
    LabelWidthChangeEvent that = (LabelWidthChangeEvent) o;
    return Float.compare(that.width, width) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), width);
  }
}
