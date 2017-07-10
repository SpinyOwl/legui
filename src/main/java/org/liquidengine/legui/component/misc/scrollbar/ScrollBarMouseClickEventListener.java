package org.liquidengine.legui.component.misc.scrollbar;

import org.joml.Vector2f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.Viewport;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventProcessor;
import org.liquidengine.legui.listener.MouseClickEventListener;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * Default mouse click event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent} event.
 */
public class ScrollBarMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle {@link MouseClickEvent}
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        ScrollBar scrollBar = (ScrollBar) event.getComponent();
        boolean released = event.getAction() != MouseClickEvent.MouseClickAction.PRESS;
        if (!event.getButton().equals(MOUSE_BUTTON_LEFT)) {
            return;
        }

        Vector2f pos = scrollBar.getScreenPosition();
        Vector2f cursorPosition = Mouse.getCursorPosition();

        float visibleAmount = scrollBar.getVisibleAmount();
        float curValue = scrollBar.getCurValue();
        boolean vertical = Orientation.VERTICAL.equals(scrollBar.getOrientation());

        Vector2f guiSize = scrollBar.getSize();
        float arrowSize = scrollBar.isArrowsEnabled() ? scrollBar.getArrowSize() : 0;
        float scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float valueRange = maxValue - minValue;
        float barSize = scrollBarSize * visibleAmount / valueRange;
        if (barSize < ScrollBar.MIN_SCROLL_SIZE) {
            barSize = ScrollBar.MIN_SCROLL_SIZE;
        }
        float scrollPosAccordingToScrollBounds = (scrollBarSize - barSize) * curValue / valueRange;

        float left;
        float curPos;
        float newVal;
        if (vertical) {
            left = pos.y + scrollPosAccordingToScrollBounds + arrowSize;
            curPos = cursorPosition.y;
        } else {
            left = pos.x + scrollPosAccordingToScrollBounds + arrowSize;
            curPos = cursorPosition.x;
        }
        if (curPos < left) {
            newVal = curValue - 0.5f * visibleAmount * valueRange / (valueRange - visibleAmount);
            if (!released) {
                updateViewport(event, scrollBar, maxValue, minValue, newVal);
            }
            scrollBar.setScrolling(false);
        } else if (curPos > left + barSize) {
            newVal = curValue + 0.5f * visibleAmount * valueRange / (valueRange - visibleAmount);
            if (!released) {
                updateViewport(event, scrollBar, maxValue, minValue, newVal);
            }
            scrollBar.setScrolling(false);
        } else {
            if (released) {
                scrollBar.setScrolling(false);
            } else {
                scrollBar.setScrolling(true);
            }
        }
    }

    /**
     * Used to update viewport.
     *
     * @param event     event.
     * @param scrollBar scrollbar.
     * @param maxValue  maximum scrollbar value.
     * @param minValue  minimum scrollbar value.
     * @param newValue  new scrollbar value.
     */
    private void updateViewport(Event event, ScrollBar scrollBar, float maxValue, float minValue, float newValue) {
        float valueToUse = newValue;
        if (newValue > maxValue) {
            valueToUse = maxValue;
        } else if (newValue < minValue) {
            valueToUse = minValue;
        }
        EventProcessor processor = event.getContext().getEventProcessor();
        processor.pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, event.getContext(), scrollBar.getCurValue(), valueToUse));
        scrollBar.setCurValue(valueToUse);

        Viewport viewport = scrollBar.getViewport();
        if (viewport != null) {
            viewport.updateViewport();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
