package com.spinyowl.legui.component.event.textarea;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.event.Event;
import com.spinyowl.legui.system.context.Context;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextAreaFieldHeightChangeEvent extends Event<TextAreaField> {

  private final float height;

  public TextAreaFieldHeightChangeEvent(TextAreaField targetComponent, Context context, Frame frame,
      float height) {
    super(targetComponent, context, frame);
    this.height = height;
  }

  public float getWidth() {
    return height;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("height", height)
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
    TextAreaFieldHeightChangeEvent that = (TextAreaFieldHeightChangeEvent) o;
    return Float.compare(that.height, height) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), height);
  }
}
