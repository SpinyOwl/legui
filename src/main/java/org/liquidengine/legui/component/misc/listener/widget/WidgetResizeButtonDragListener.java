package org.liquidengine.legui.component.misc.listener.widget;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseDragEventListener;

/**
 * Created by ShchAlexander on 11.03.2018.
 */
public class WidgetResizeButtonDragListener implements MouseDragEventListener {

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

        Float minWidth = widget.getStyle().getMinWidth();
        if (minWidth == null) {
            minWidth = 1f;
        }
        Float minHeight = widget.getStyle().getMinHeight();
        if (minHeight == null) {
            minHeight = widget.getTitleContainer().getSize().y;
        }
        if (
            (
                delta.x < 0 && (cursorPositionPrev.x <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x
                    || cursorPosition.x <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x))
                || (
                (delta.x > 0 && (cursorPositionPrev.x >= resizeButton.getAbsolutePosition().x
                    || cursorPosition.x >= resizeButton.getAbsolutePosition().x)))
            ) {
            if (xx >= minWidth) {
                deltaSize.x = delta.x;
            }
        }
        if (
            (
                delta.y < 0 && (cursorPositionPrev.y <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y
                    || cursorPosition.y <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y))
                || (
                (delta.y > 0 && (cursorPositionPrev.y >= resizeButton.getAbsolutePosition().y
                    || cursorPosition.y >= resizeButton.getAbsolutePosition().y)))
            ) {
            if (yy >= minHeight) {
                deltaSize.y = delta.y;
            }
        }

        widget.getSize().add(deltaSize);

    }
}
