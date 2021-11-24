package com.spinyowl.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SystemScrollEvent implements SystemEvent {

  public final long window;
  public final double xoffset;
  public final double yoffset;

  public SystemScrollEvent(long window, double xoffset, double yoffset) {
    this.window = window;
    this.xoffset = xoffset;
    this.yoffset = yoffset;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("window", window)
        .append("xoffset", xoffset)
        .append("yoffset", yoffset)
        .toString();
  }
}
