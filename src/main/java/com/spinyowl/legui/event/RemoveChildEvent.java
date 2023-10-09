package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RemoveChildEvent<T extends Component> extends Event<T> {

  private final Component removed;

  public RemoveChildEvent(T targetComponent, Component removed) {
    super(targetComponent, null, targetComponent.getFrame());
    this.removed = removed;
  }
}
