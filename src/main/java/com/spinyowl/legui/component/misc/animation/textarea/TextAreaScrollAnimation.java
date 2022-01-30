package com.spinyowl.legui.component.misc.animation.textarea;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.TextArea;
import com.spinyowl.legui.component.misc.animation.ViewportAnimation;


public class TextAreaScrollAnimation extends ViewportAnimation<TextArea> {

  public TextAreaScrollAnimation(TextArea textArea, double updateTime) {
    super(textArea, updateTime);
  }

  public TextAreaScrollAnimation(TextArea textArea) {
    this(textArea, 0.1d);
  }


  protected void updateViewport(TextArea scrollablePanel, double delta) {
    Component viewport = scrollablePanel.getViewport();
    Component textAreaField = scrollablePanel.getTextAreaField();
    ScrollBar verticalScrollBar = scrollablePanel.getVerticalScrollBar();
    ScrollBar horizontalScrollBar = scrollablePanel.getHorizontalScrollBar();

    super.updateViewport(viewport, textAreaField, verticalScrollBar, horizontalScrollBar, delta);
  }

}
