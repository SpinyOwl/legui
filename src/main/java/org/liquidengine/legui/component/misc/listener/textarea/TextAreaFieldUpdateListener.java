package org.liquidengine.legui.component.misc.listener.textarea;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextArea.TextAreaViewport;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEventListener;

/**
 * Created by ShchAlexander on 17.07.2018.
 */
public class TextAreaFieldUpdateListener implements TextAreaFieldUpdateEventListener {

    private TextArea textArea;

    public TextAreaFieldUpdateListener(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void process(TextAreaFieldUpdateEvent event) {
        TextAreaField textAreaField = event.getTargetComponent();
        Component parent = textAreaField.getParent();

        if (parent instanceof TextAreaViewport) {
            TextAreaViewport textAreaViewport = (TextAreaViewport) parent;
            float maxTextWidth = Math.max(textAreaField.getMaxTextWidth() +
                                              textAreaField.getStyle().getPaddingLeftF() +
                                              textAreaField.getStyle().getPaddingRightF(),
                                          textAreaViewport.getSize().x);
            float maxTextHeight = Math.max(textAreaField.getMaxTextHeight() +
                                               textAreaField.getStyle().getPaddingTopF() +
                                               textAreaField.getStyle().getPaddingBottomF(),
                                           textAreaViewport.getSize().y);
            textAreaField.setSize(maxTextWidth, maxTextHeight);
        }

        Vector2f absolutePosition = textAreaField.getAbsolutePosition();
        Vector2f size = textAreaField.getSize();

        updateHorizontalOffset(textAreaField, absolutePosition, size);
        updateVerticalOffset(textAreaField, absolutePosition, size);
    }

    private void updateHorizontalOffset(TextAreaField textAreaField, Vector2f absolutePosition, Vector2f size) {
        ScrollBar horizontalScrollBar = textArea.getHorizontalScrollBar();
        float caretX = textAreaField.getCaretX() - absolutePosition.x - textAreaField.getStyle().getPaddingLeftF();
        float maxTextWidth = textAreaField.getMaxTextWidth();

        float newVal = 0;
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
        horizontalScrollBar.setCurValue(newVal);
    }

    private void updateVerticalOffset(TextAreaField textAreaField, Vector2f absolutePosition, Vector2f size) {
        ScrollBar verticalScrollbar = textArea.getVerticalScrollBar();
        float caretY = textAreaField.getCaretY() - absolutePosition.y - textAreaField.getStyle().getPaddingTopF();
        float maxTextHeight = textAreaField.getMaxTextHeight();

        float newVal = 0;
        float maxValue = verticalScrollbar.getMaxValue();
        float minValue = verticalScrollbar.getMinValue();
        float valueRange = verticalScrollbar.getMaxValue() - verticalScrollbar.getMinValue();

        newVal = valueRange * caretY / (maxTextHeight - textAreaField.getTextState().getFontSize());

        if (newVal > maxValue) {
            newVal = maxValue;
        }
        if (newVal < minValue) {
            newVal = minValue;
        }
        verticalScrollbar.setCurValue(newVal);
    }

}
