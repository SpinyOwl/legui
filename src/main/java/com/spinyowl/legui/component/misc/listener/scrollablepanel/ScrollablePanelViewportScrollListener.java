package com.spinyowl.legui.component.misc.listener.scrollablepanel;

import static com.spinyowl.legui.component.misc.listener.EventUtils.hasScrollableInChildComponentsUnderCursor;
import static com.spinyowl.legui.component.misc.listener.EventUtils.hasViewportsInAboveLayersUnderCursor;
import static com.spinyowl.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.ScrollablePanel;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.ScrollEventListener;
import org.joml.Vector2f;


public class ScrollablePanelViewportScrollListener implements ScrollEventListener {

  /**
   * Used to handle specific event.
   *
   * @param event event to handle.
   */
  @Override
  public void process(ScrollEvent event) {
    Vector2f cursorPosition = Mouse.getCursorPosition();
    Component targetComponent = event.getTargetComponent();

    if (hasViewportsInAboveLayersUnderCursor(targetComponent, cursorPosition)) {
      return;
    }
    if (hasScrollableInChildComponentsUnderCursor(targetComponent, cursorPosition)) {
      return;
    }

    ScrollablePanel scrollablePanel = (ScrollablePanel) targetComponent.getParent();

    if (Math.abs(event.getYoffset()) > 0) {
      updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(),
          scrollablePanel.getVerticalScrollBar());
    }
    if (Math.abs(event.getXoffset()) > 0) {
      updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(),
          scrollablePanel.getHorizontalScrollBar());
    }
  }

}
