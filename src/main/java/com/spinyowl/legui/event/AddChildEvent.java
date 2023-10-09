package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AddChildEvent<T extends Component> extends Event<T> {

  private final Component added;

  public AddChildEvent(T targetComponent, Component added) {
    super(targetComponent, null, targetComponent.getFrame());
    this.added = added;
  }
}
