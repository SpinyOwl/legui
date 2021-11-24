package com.spinyowl.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SystemWindowCloseEvent implements SystemEvent {

  public final long window;

  public SystemWindowCloseEvent(long window) {
    this.window = window;
  }

  public long getWindow() {
    return window;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("window", window)
        .toString();
  }
}
