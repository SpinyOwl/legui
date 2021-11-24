package com.spinyowl.legui.component.misc.listener.scrollbar;

import static com.spinyowl.legui.component.misc.listener.EventUtils.hasViewportsInAboveLayersUnderCursor;
import static com.spinyowl.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.ScrollEventListener;
import org.joml.Vector2f;

/**
 * Default mouse scroll event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent}
 * event.
 */
public class ScrollBarScrollListener implements ScrollEventListener {

  public void process(ScrollEvent event) {
    Vector2f cursorPosition = Mouse.getCursorPosition();
    Component targetComponent = event.getTargetComponent();
    if (hasViewportsInAboveLayersUnderCursor(targetComponent, cursorPosition)) {
      return;
    }

    ScrollBar scrollBar = (ScrollBar) targetComponent;

    if (Math.abs(event.getYoffset()) > 0) {
      updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(), scrollBar);
    } else if (Math.abs(event.getXoffset()) > 0) {
      updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(), scrollBar);
    }
  }
}
