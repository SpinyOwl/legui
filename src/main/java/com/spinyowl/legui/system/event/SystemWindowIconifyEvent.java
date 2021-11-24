package com.spinyowl.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SystemWindowIconifyEvent implements SystemEvent {

  public final long window;
  public final boolean iconified;

  public SystemWindowIconifyEvent(long window, boolean iconified) {
    this.window = window;
    this.iconified = iconified;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("iconified", iconified)
        .append("window", window)
        .toString();
  }

}
