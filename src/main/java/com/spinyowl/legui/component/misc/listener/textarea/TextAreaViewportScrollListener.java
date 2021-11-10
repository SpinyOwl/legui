package com.spinyowl.legui.component.misc.listener.textarea;

import static com.spinyowl.legui.component.misc.listener.EventUtils.hasViewportsInAboveLayersUnderCursor;
import static com.spinyowl.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.ScrollablePanel;
import com.spinyowl.legui.component.TextArea;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.ScrollEventListener;
import com.spinyowl.legui.system.handler.SehUtil;
import java.util.ArrayList;
import org.joml.Vector2f;


public class TextAreaViewportScrollListener implements ScrollEventListener {


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

    ArrayList<Component> targetList = new ArrayList<>();
    SehUtil.recursiveTargetComponentListSearch(Mouse.getCursorPosition(),
        event.getTargetComponent(), targetList);
    for (Component component : targetList) {
      if ((component instanceof TextArea) || (component instanceof ScrollablePanel)) {
        return;
      }
    }

    TextArea textArea = (TextArea) event.getTargetComponent().getParent();
    textArea.resizeTextAreaField();

    if (Math.abs(event.getYoffset()) > 0) {
      updateScrollBarValue(event.getYoffset(), event.getContext(), event.getFrame(),
          textArea.getVerticalScrollBar());
    }
    if (Math.abs(event.getXoffset()) > 0) {
      updateScrollBarValue(event.getXoffset(), event.getContext(), event.getFrame(),
          textArea.getHorizontalScrollBar());
    }
  }
}
