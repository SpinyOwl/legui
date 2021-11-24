package com.spinyowl.legui.component.misc.listener.scrollbar;

import static com.spinyowl.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import com.spinyowl.legui.component.optional.Orientation;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.MouseDragEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import org.joml.Vector2f;

/**
 * Default mouse drag event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent}
 * event.
 */
public class ScrollBarMouseDragEventListener implements MouseDragEventListener {

  @Override
  public void process(MouseDragEvent event) {
    ScrollBar scrollBar = (ScrollBar) event.getTargetComponent();
    if (!scrollBar.isScrolling()) {
      return;
    }
    if (!MOUSE_BUTTON_LEFT.isPressed()) {
      return;
    }

    Vector2f pos = scrollBar.getAbsolutePosition();
    Vector2f cursorPosition = Mouse.getCursorPosition();

    float visibleAmount = scrollBar.getVisibleAmount();
    boolean vertical = Orientation.VERTICAL.equals(scrollBar.getOrientation());

    Vector2f guiSize = scrollBar.getSize();
    float arrowSize = scrollBar.isArrowsEnabled() ? scrollBar.getArrowSize() : 0;
    float scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
    float maxValue = scrollBar.getMaxValue();
    float minValue = scrollBar.getMinValue();
    float valueRange = maxValue - minValue;

    if (valueRange - visibleAmount < 0.001f) {
      return;
    }

    float barSize = scrollBarSize * visibleAmount / valueRange;
    if (barSize < ScrollBar.MIN_SCROLL_SIZE) {
      barSize = ScrollBar.MIN_SCROLL_SIZE;
    }

    float curPos;
    float dpos;
    if (vertical) {
      dpos = pos.y;
      curPos = cursorPosition.y;
    } else {
      dpos = pos.x;
      curPos = cursorPosition.x;
    }
    float newVal =
        valueRange * (curPos - (dpos + arrowSize + barSize / 2f)) / (scrollBarSize - barSize);
    if (newVal > maxValue) {
      newVal = maxValue;
    } else if (newVal < minValue) {
      newVal = minValue;
    }
    EventProcessorProvider.getInstance()
        .pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, event.getContext(), event.getFrame(),
            scrollBar.getCurValue(), newVal));
    scrollBar.setCurValue(newVal);
  }
}
