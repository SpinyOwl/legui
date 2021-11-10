package com.spinyowl.legui.component.event.textarea;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.event.Event;
import com.spinyowl.legui.system.context.Context;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextAreaFieldWidthChangeEvent extends Event<TextAreaField> {

  private final float width;

  public TextAreaFieldWidthChangeEvent(TextAreaField targetComponent, Context context, Frame frame,
      float width) {
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
    TextAreaFieldWidthChangeEvent that = (TextAreaFieldWidthChangeEvent) o;
    return Float.compare(that.width, width) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), width);
  }
}
