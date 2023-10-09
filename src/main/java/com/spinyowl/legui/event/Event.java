package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import lombok.Data;

/**
 * An abstraction for UI events.
 *
 * @param <T> target component type.
 */
@Data
public abstract class Event<T extends Component> {
  private final T targetComponent;
  private final Context context;
  private final Frame frame;
}
