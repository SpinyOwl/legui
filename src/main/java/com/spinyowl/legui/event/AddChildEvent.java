package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddChildEvent<T extends Component> extends Event<T> {

  private final Component added;

  public AddChildEvent(T targetComponent, Component added) {
    super(targetComponent, null, targetComponent.getFrame());
    this.added = added;
  }

  public Component getAdded() {
    return added;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("added", added)
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
    AddChildEvent<?> that = (AddChildEvent<?>) o;
    return Objects.equals(added, that.added);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), added);
  }
}
