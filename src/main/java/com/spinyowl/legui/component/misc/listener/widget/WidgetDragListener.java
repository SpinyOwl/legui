package com.spinyowl.legui.component.misc.listener.widget;

import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.component.event.component.ChangePositionEvent;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.listener.MouseDragEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import org.joml.Vector2f;


public class WidgetDragListener implements MouseDragEventListener {

  public static final float THRESHOLD = 0.0001f;
  private Widget widget;

  public WidgetDragListener(Widget widget) {
    this.widget = widget;
  }

  @Override
  public void process(MouseDragEvent event) {
    Vector2f oldPos = new Vector2f(widget.getPosition());
    widget.getPosition().add(event.getDelta());
    Vector2f newPos = widget.getPosition();
    if (!oldPos.equals(newPos, THRESHOLD)) {
      EventProcessorProvider.getInstance().pushEvent(
          new ChangePositionEvent(widget, event.getContext(), event.getFrame(), oldPos, newPos));
    }
  }

}
