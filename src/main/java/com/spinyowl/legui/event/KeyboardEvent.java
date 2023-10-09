package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.input.KeyAction;
import com.spinyowl.legui.input.KeyMod;
import com.spinyowl.legui.input.KeyboardKey;
import com.spinyowl.legui.system.context.Context;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class KeyboardEvent<T extends Component> extends Event<T> {

  private final KeyAction action;
  private final KeyboardKey key;
  private final Set<KeyMod> mods;

  public KeyboardEvent(T component, Context context, Frame frame, KeyAction action, KeyboardKey key,
      Set<KeyMod> mods) {
    super(component, context, frame);
    this.action = action;
    this.key = key;
    this.mods = mods;
  }

}
