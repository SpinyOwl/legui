package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.input.KeyAction;
import com.spinyowl.legui.input.KeyMod;
import com.spinyowl.legui.input.KeyboardKey;
import com.spinyowl.legui.system.context.Context;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


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

  public KeyAction getAction() {
    return action;
  }

  public KeyboardKey getKey() {
    return key;
  }

  public Set<KeyMod> getMods() {
    return mods;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("targetComponent", getTargetComponent().getClass().getSimpleName())
        .append("action", action)
        .append("key", key)
        .append("mods", Arrays.toString(mods.toArray()))
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

    KeyboardEvent<?> keyEvent = (KeyboardEvent<?>) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(action, keyEvent.action)
        .append(key, keyEvent.key)
        .append(mods, keyEvent.mods)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(action)
        .append(key)
        .append(mods)
        .toHashCode();
  }
}
