package com.spinyowl.legui.component.misc.listener.splitpanel;

import static com.spinyowl.legui.component.optional.Orientation.HORIZONTAL;
import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.PRESS;
import static com.spinyowl.legui.event.MouseClickEvent.MouseClickAction.RELEASE;

import com.spinyowl.legui.component.SplitPanel;
import com.spinyowl.legui.component.optional.Orientation;
import com.spinyowl.legui.cursor.Cursor;
import com.spinyowl.legui.cursor.CursorServiceProvider;
import com.spinyowl.legui.event.CursorEnterEvent;
import com.spinyowl.legui.event.Event;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.EventListener;
import java.util.Objects;

public class SplitPanelSeparatorListenerDelegate implements EventListener<Event> {

  private SplitPanel splitPanel;
  private boolean dragging = false;

  public SplitPanelSeparatorListenerDelegate(SplitPanel splitPanel) {
    this.splitPanel = Objects.requireNonNull(splitPanel);
  }

  /**
   * Used to handle specific event.
   *
   * @param event event to handle.
   */
  @Override
  public void process(Event event) {
    if (!event.getTargetComponent().equals(splitPanel.getSeparator())) {
      return;
    }

    if (event instanceof MouseClickEvent) {
      MouseClickEvent e = (MouseClickEvent) event;
      if (e.getAction() == PRESS) {
        dragging = true;
      }
      if (e.getAction() == RELEASE) {
        dragging = false;
        CursorServiceProvider.getInstance().setCursor(Cursor.ARROW, e.getContext());
      }
    }

    if (event instanceof CursorEnterEvent) {
      CursorEnterEvent e = (CursorEnterEvent) event;
      Orientation orientation = splitPanel.getOrientation();
      if (e.isEntered() && orientation == HORIZONTAL) {
        CursorServiceProvider.getInstance().setCursor(Cursor.H_RESIZE, e.getContext());
      } else if (e.isEntered() && orientation != HORIZONTAL) {
        CursorServiceProvider.getInstance().setCursor(Cursor.V_RESIZE, e.getContext());
      } else if (!dragging) {
        CursorServiceProvider.getInstance().setCursor(Cursor.ARROW, e.getContext());
      }
    }
  }
}
