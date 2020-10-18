package org.liquidengine.legui.component.misc.listener.widget;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.event.component.ChangeSizeEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.length.Length;
import org.liquidengine.legui.style.util.StyleUtilities;

import static org.liquidengine.legui.style.length.LengthType.pixel;

/**
 * Created by ShchAlexander on 11.03.2018.
 */
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
                delta.x < 0 && (cursorPositionPrev.x <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x
                    || cursorPosition.x <= resizeButton.getAbsolutePosition().x + resizeButton.getSize().x))
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
                delta.y < 0 && (cursorPositionPrev.y <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y
                    || cursorPosition.y <= resizeButton.getAbsolutePosition().y + resizeButton.getSize().y))
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
            EventProcessorProvider.getInstance().pushEvent(new ChangeSizeEvent(widget, event.getContext(), event.getFrame(), oldSize, newSize));
        }
    }
}
