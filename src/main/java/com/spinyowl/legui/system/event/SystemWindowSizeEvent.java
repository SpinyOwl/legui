package com.spinyowl.legui.system.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SystemWindowSizeEvent implements SystemEvent {

  public final long window;
  public final int width;
  public final int height;

  public SystemWindowSizeEvent(long window, int width, int height) {
    this.window = window;
    this.width = width;
    this.height = height;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("window", window)
        .append("width", width)
        .append("height", height)
        .toString();
  }

}
