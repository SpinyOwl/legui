package com.spinyowl.legui.component.misc.animation.scrollablepanel;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.ScrollablePanel;
import com.spinyowl.legui.component.misc.animation.ViewportAnimation;


public class ScrollablePanelAnimation extends ViewportAnimation<ScrollablePanel> {

  public ScrollablePanelAnimation(ScrollablePanel scrollablePanel, double updateTime) {
    super(scrollablePanel, updateTime);
  }

  public ScrollablePanelAnimation(ScrollablePanel scrollablePanel) {
    this(scrollablePanel, 0.1d);
  }


  protected void updateViewport(ScrollablePanel scrollablePanel, double delta) {
    Component viewport = scrollablePanel.getViewport();
    Component container = scrollablePanel.getContainer();
    ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
    ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();

    super.updateViewport(viewport, container, verticalScrollBar, horizontalScrollBar, delta);
  }

}
