package com.spinyowl.legui.component.misc.listener.widget;

import static com.spinyowl.legui.style.length.LengthType.pixel;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.component.event.component.ChangeSizeEvent;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.MouseDragEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.length.Length;
import com.spinyowl.legui.style.util.StyleUtilities;
import org.joml.Vector2f;


public class WidgetResizeButtonDragListener implements MouseDragEventListener {

  public static final float THRESHOLD = 0.0001f;
  private Button resizeButton;
  private Widget widget;

  public WidgetResizeButtonDragListener(Button resizeButton, Widget widget) {
    this.resizeButton = resizeButton;
    this.widget = widget;
  }

  /**
   * Used to handle {@link MouseDragEvent}.
   *
   * @param event event to handle.
   */
  @Override
  public void process(MouseDragEvent event) {
    Vector2f delta = event.getDelta();

    Vector2f cursorPositionPrev = Mouse.getCursorPositionPrev();
    Vector2f cursorPosition = Mouse.getCursorPosition();

    float xx = widget.getSize().x + delta.x;
    float yy = widget.getSize().y + delta.y;

    Vector2f deltaSize = new Vector2f();

    Length minWidthU = widget.getStyle().getMinWidth();
    if (minWidthU == null) {
      minWidthU = pixel(1f);
    }
    Length minHeightU = widget.getStyle().getMinHeight();
    if (minHeightU == null) {
      minHeightU = pixel(widget.getTitleContainer().getSize().y);
    }

    Length maxWidthU = widget.getStyle().getMaxWidth();
    Length maxHeightU = widget.getStyle().getMaxHeight();

    Float minWidth = StyleUtilities.getFloatLength(minWidthU, widget.getParent().getSize().x);
    Float minHeight = StyleUtilities.getFloatLength(minHeightU, widget.getParent().getSize().y);
    Float maxWidth = StyleUtilities.getFloatLength(maxWidthU, widget.getParent().getSize().x);
    Float maxHeight = StyleUtilities.getFloatLength(maxHeightU, widget.getParent().getSize().y);

    if (
        (
            delta.x < 0 && (cursorPositionPrev.x
                <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x
                || cursorPosition.x
                <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x))
            || (
            (delta.x > 0 && (cursorPositionPrev.x >= resizeButton.getAbsolutePosition().x
                || cursorPosition.x >= resizeButton.getAbsolutePosition().x)))
    ) {
      if (xx >= minWidth && (maxWidth == null || xx <= maxWidth)) {
        deltaSize.x = delta.x;
      }
    }
    if (
        (
            delta.y < 0 && (cursorPositionPrev.y
                <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y
                || cursorPosition.y
                <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y))
            || (
            (delta.y > 0 && (cursorPositionPrev.y >= resizeButton.getAbsolutePosition().y
                || cursorPosition.y >= resizeButton.getAbsolutePosition().y)))
    ) {
      if (yy >= minHeight && (maxHeight == null || yy <= maxHeight)) {
        deltaSize.y = delta.y;
      }
    }

    Vector2f oldSize = new Vector2f(widget.getSize());
    widget.getSize().add(deltaSize);
    Vector2f newSize = widget.getSize();
    if (!oldSize.equals(newSize, THRESHOLD)) {
      EventProcessorProvider.getInstance().pushEvent(
          new ChangeSizeEvent(widget, event.getContext(), event.getFrame(), oldSize, newSize));
    }
  }
}
