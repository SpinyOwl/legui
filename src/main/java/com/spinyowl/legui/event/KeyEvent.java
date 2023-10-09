package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString

public class KeyEvent<T extends Component> extends Event<T> {

  private final int action;
  private final int key;
  private final int mods;
  private final int scancode;

  public KeyEvent(T component, Context context, Frame frame, int action, int key, int mods,
      int scancode) {
    super(component, context, frame);
    this.action = action;
    this.key = key;
    this.mods = mods;
    this.scancode = scancode;
  }

  public int getAction() {
    return action;
  }

  public int getKey() {
    return key;
  }

  public int getMods() {
    return mods;
  }

  public int getScancode() {
    return scancode;
  }

}
