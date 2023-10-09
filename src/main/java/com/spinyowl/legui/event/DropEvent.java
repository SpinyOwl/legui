package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class DropEvent<T extends Component> extends Event<T> {

  @NonNull private final List<String> files;

  public DropEvent(T component, Context context, Frame frame, @NonNull List<String> files) {
    super(component, context, frame);
    this.files = files;
  }
}
