package com.spinyowl.legui.component.misc.listener.textarea;

import static com.spinyowl.legui.style.util.StyleUtilities.getPadding;

import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.TextArea;
import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import com.spinyowl.legui.component.event.textarea.TextAreaFieldUpdateEventListener;
import org.joml.Vector2f;
import org.joml.Vector4f;


/**
 * Used to update text area field sizes according to changes in text area field.
 */
public class TextAreaFieldUpdateListener implements TextAreaFieldUpdateEventListener {

  private final TextArea textArea;

  public TextAreaFieldUpdateListener(TextArea textArea) {
    this.textArea = textArea;
  }

  @Override
  public void process(TextAreaFieldUpdateEvent event) {
    TextAreaField textAreaField = event.getTargetComponent();

    textArea.resizeTextAreaField();

    Vector2f absolutePosition = textAreaField.getAbsolutePosition();
    Vector4f padding = getPadding(textAreaField, textAreaField.getStyle());
    updateHorizontalOffset(textAreaField, absolutePosition, padding.x);
    updateVerticalOffset(textAreaField, absolutePosition, padding.y);
  }

  private void updateHorizontalOffset(TextAreaField textAreaField, Vector2f absolutePosition,
      float paddingLeft) {
    ScrollBar horizontalScrollBar = textArea.getHorizontalScrollBar();
    float caretX =
        (textAreaField.getCaretX() == null ? 0 : textAreaField.getCaretX()) - absolutePosition.x
            - paddingLeft;
    float maxTextWidth = textAreaField.getMaxTextWidth();

    float newVal = 0;
    if (maxTextWidth != 0) {
      float maxValue = horizontalScrollBar.getMaxValue();
      float minValue = horizontalScrollBar.getMinValue();
      float valueRange = horizontalScrollBar.getMaxValue() - horizontalScrollBar.getMinValue();

      newVal = valueRange * caretX / maxTextWidth;

      if (newVal > maxValue) {
        newVal = maxValue;
      }
      if (newVal < minValue) {
        newVal = minValue;
      }
    }
    horizontalScrollBar.setCurValue(newVal);
  }

  private void updateVerticalOffset(TextAreaField textAreaField, Vector2f absolutePosition,
      float paddingTop) {
    ScrollBar verticalScrollbar = textArea.getVerticalScrollBar();
    float caretY =
        (textAreaField.getCaretY() == null ? 0 : textAreaField.getCaretY()) - absolutePosition.y
            - paddingTop;
    float maxTextHeight = textAreaField.getMaxTextHeight();

    float newVal = 0;

    float maxValue = verticalScrollbar.getMaxValue();
    float minValue = verticalScrollbar.getMinValue();
    float valueRange = verticalScrollbar.getMaxValue() - verticalScrollbar.getMinValue();

    if (maxTextHeight != textAreaField.getStyle().getFontSize()) {
      newVal = valueRange * caretY / (maxTextHeight - textAreaField.getStyle().getFontSize());
    }

    if (newVal > maxValue) {
      newVal = maxValue;
    }
    if (newVal < minValue) {
      newVal = minValue;
    }
    verticalScrollbar.setCurValue(newVal);
  }

}
