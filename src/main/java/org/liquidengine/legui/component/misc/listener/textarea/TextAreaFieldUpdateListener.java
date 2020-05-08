package org.liquidengine.legui.component.misc.listener.textarea;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextArea.TextAreaViewport;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEventListener;

import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;


/**
 * Used to update text area field sizes according to changes in text area field.
 * Created by ShchAlexander on 17.07.2018.
 */
public class TextAreaFieldUpdateListener implements TextAreaFieldUpdateEventListener {

    private final TextArea textArea;

    public TextAreaFieldUpdateListener(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void process(TextAreaFieldUpdateEvent event) {
        TextAreaField textAreaField = event.getTargetComponent();
        Component parent = textAreaField.getParent();

        Vector4f padding = getPadding(textAreaField, textAreaField.getStyle());
        if (parent instanceof TextAreaViewport) {
            TextAreaViewport textAreaViewport = (TextAreaViewport) parent;

            float maxTextWidth = Math.max(
                textAreaField.getMaxTextWidth() + padding.x + padding.z,
                textAreaViewport.getSize().x
            );
            float maxTextHeight = Math.max(
                textAreaField.getMaxTextHeight() + padding.y + padding.w,
                textAreaViewport.getSize().y
            );
            textAreaField.setSize(maxTextWidth, maxTextHeight);
        }

        Vector2f absolutePosition = textAreaField.getAbsolutePosition();

        updateHorizontalOffset(textAreaField, absolutePosition, padding.x);
        updateVerticalOffset(textAreaField, absolutePosition, padding.y);
    }

    private void updateHorizontalOffset(TextAreaField textAreaField, Vector2f absolutePosition, float paddingLeft) {
        ScrollBar horizontalScrollBar = textArea.getHorizontalScrollBar();
        float caretX = (textAreaField.getCaretX() == null ? 0 : textAreaField.getCaretX()) - absolutePosition.x - paddingLeft;
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

    private void updateVerticalOffset(TextAreaField textAreaField, Vector2f absolutePosition, float paddingTop) {
        ScrollBar verticalScrollbar = textArea.getVerticalScrollBar();
        float caretY = (textAreaField.getCaretY() == null ? 0 : textAreaField.getCaretY()) - absolutePosition.y - paddingTop;
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
