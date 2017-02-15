package org.liquidengine.legui.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.AbstractEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.listener.ScrollEventListener;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.theme.Theme;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class ScrollBar extends Controller {
    public static final float MIN_SCROLL_SIZE = 1f;

    private Orientation orientation = Orientation.VERTICAL;

    private float minValue = 0f;
    private float maxValue = 100f;
    private float curValue = 0f;

    private float visibleAmount;

    private boolean  arrowsEnabled = Theme.DEFAULT_THEME.scrollBarArrowsEnabled();
    private float    arrowSize     = Theme.DEFAULT_THEME.scrollBarArrowSize();
    private Vector4f arrowColor    = Theme.DEFAULT_THEME.scrollBarArrowColor();
    private Vector4f scrollColor   = Theme.DEFAULT_THEME.scrollBarColor();

    private boolean scrolling = false;

    private Viewport viewport;

    public ScrollBar(float x, float y, float width, float height, float curValue) {
        super(x, y, width, height);
        this.curValue = curValue;
        initialize();
    }

    public ScrollBar(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    public ScrollBar() {
        initialize();
    }

    public ScrollBar(int curValue) {
        this.curValue = curValue;
    }

    private void initialize() {
        getListenerMap().addListener(ScrollEvent.class, new ScrollBarScrollListener(this));
        getListenerMap().addListener(MouseDragEvent.class, new ScrollBarMouseDragEventListener(this));
        getListenerMap().addListener(MouseClickEvent.class, new ScrollBarMouseClickEventListener(this));
    }

    public boolean isScrolling() {
        return scrolling;
    }

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public float getArrowSize() {
        return arrowSize;
    }

    public void setArrowSize(float arrowSize) {
        this.arrowSize = arrowSize;
    }

    public boolean isArrowsEnabled() {
        return arrowsEnabled;
    }

    public void setArrowsEnabled(boolean arrowsEnabled) {
        this.arrowsEnabled = arrowsEnabled;
    }

    public Vector4f getScrollColor() {
        return scrollColor;
    }

    public void setScrollColor(Vector4f scrollColor) {
        this.scrollColor = scrollColor;
    }

    public float getVisibleAmount() {
        return visibleAmount;
    }

    public void setVisibleAmount(float visibleAmount) {
        if (visibleAmount > minValue && visibleAmount <= maxValue) {
            this.visibleAmount = visibleAmount;
        }
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getCurValue() {
        return curValue;
    }

    public void setCurValue(float curValue) {
        if (curValue < minValue) {
            this.curValue = minValue;
        } else if (curValue > maxValue) {
            this.curValue = maxValue;
        } else {
            this.curValue = curValue;
        }
    }

    public Vector4f getArrowColor() {
        return arrowColor;
    }

    public void setArrowColor(Vector4f arrowColor) {
        this.arrowColor = arrowColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScrollBar)) return false;
        if (!super.equals(o)) return false;
        ScrollBar scrollBar = (ScrollBar) o;
        return Float.compare(scrollBar.minValue, minValue) == 0 &&
                Float.compare(scrollBar.maxValue, maxValue) == 0 &&
                Float.compare(scrollBar.curValue, curValue) == 0 &&
                Float.compare(scrollBar.visibleAmount, visibleAmount) == 0 &&
                arrowsEnabled == scrollBar.arrowsEnabled &&
                Float.compare(scrollBar.arrowSize, arrowSize) == 0 &&
                scrolling == scrollBar.scrolling &&
                orientation == scrollBar.orientation &&
                Objects.equal(arrowColor, scrollBar.arrowColor) &&
                Objects.equal(scrollColor, scrollBar.scrollColor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(),
                orientation,
                minValue,
                maxValue,
                curValue,
                visibleAmount,
                arrowsEnabled,
                arrowSize,
                arrowColor,
                scrolling,
                scrollColor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orientation", orientation)
                .append("minValue", minValue)
                .append("maxValue", maxValue)
                .append("curValue", curValue)
                .append("visibleAmount", visibleAmount)
                .append("arrowsEnabled", arrowsEnabled)
                .append("arrowSize", arrowSize)
                .append("arrowColor", arrowColor)
                .append("scrolling", scrolling)
                .append("scrollColor", scrollColor)
                .toString();
    }

    public interface ScrollBarChangeValueEventListener extends EventListener<ScrollBarChangeValueEvent> {
        void process(ScrollBarChangeValueEvent event);
    }

    public static class ScrollBarScrollListener implements ScrollEventListener {
        private final ScrollBar scrollBar;

        public ScrollBarScrollListener(ScrollBar scrollBar) {
            this.scrollBar = scrollBar;
        }

        public void process(ScrollEvent event) {
            float maxValue      = scrollBar.getMaxValue();
            float minValue      = scrollBar.getMinValue();
            float curValue      = scrollBar.getCurValue();
            float visibleAmount = scrollBar.getVisibleAmount();
            float valueRange    = scrollBar.getMaxValue() - scrollBar.getMinValue();
            float newVal        = (float) (curValue - 0.1f * event.getYoffset() * visibleAmount * valueRange / (valueRange - visibleAmount));

            if (newVal > maxValue) newVal = maxValue;
            if (newVal < minValue) newVal = minValue;

            event.getContext().getEventProcessor().pushEvent(new ScrollBarChangeValueEvent(scrollBar, event.getContext(), curValue, newVal));
            scrollBar.setCurValue(newVal);

            Viewport viewport = scrollBar.getViewport();
            if (viewport != null) {
                viewport.updateViewport();
            }
        }
    }

    public static class ScrollBarMouseDragEventListener implements MouseDragEventListener {
        private final ScrollBar scrollBar;

        public ScrollBarMouseDragEventListener(ScrollBar scrollBar) {
            this.scrollBar = scrollBar;
        }

        @Override
        public void process(MouseDragEvent event) {
            if (!scrollBar.isScrolling()) return;
            if (!MOUSE_BUTTON_LEFT.isPressed()) return;

            Vector2f pos            = scrollBar.getScreenPosition();
            Vector2f cursorPosition = Mouse.getCursorPosition();

            float   visibleAmount = scrollBar.getVisibleAmount();
            boolean vertical      = Orientation.VERTICAL.equals(scrollBar.getOrientation());

            Vector2f guiSize       = scrollBar.getSize();
            float    arrowSize     = scrollBar.isArrowsEnabled() ? scrollBar.getArrowSize() : 0;
            float    scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
            float    maxValue      = scrollBar.getMaxValue();
            float    minValue      = scrollBar.getMinValue();
            float    valueRange    = maxValue - minValue;
            float    barSize       = scrollBarSize * visibleAmount / valueRange;
            if (barSize < MIN_SCROLL_SIZE) barSize = MIN_SCROLL_SIZE;

            float curPos, dpos;
            if (vertical) {
                dpos = pos.y;
                curPos = cursorPosition.y;
            } else {
                dpos = pos.x;
                curPos = cursorPosition.x;
            }
            float newVal = valueRange * (curPos - (dpos + arrowSize + barSize / 2f)) / (scrollBarSize - barSize);
            if (newVal > maxValue) newVal = maxValue;
            else if (newVal < minValue) newVal = minValue;
            event.getContext().getEventProcessor().pushEvent(new ScrollBarChangeValueEvent(scrollBar, event.getContext(), scrollBar.getCurValue(), newVal));
            scrollBar.setCurValue(newVal);

            Viewport viewport = scrollBar.getViewport();
            if (viewport != null) {
                viewport.updateViewport();
            }
        }
    }

    public static class ScrollBarMouseClickEventListener implements MouseClickEventListener {
        private final ScrollBar scrollBar;

        public ScrollBarMouseClickEventListener(ScrollBar scrollBar) {
            this.scrollBar = scrollBar;
        }

        @Override
        public void process(MouseClickEvent event) {
            boolean released = event.getAction() != MouseClickEvent.MouseClickAction.PRESS;
            if (!event.getButton().equals(MOUSE_BUTTON_LEFT)) return;

            Vector2f pos            = scrollBar.getScreenPosition();
            Vector2f cursorPosition = Mouse.getCursorPosition();

            float   visibleAmount = scrollBar.getVisibleAmount();
            float   curValue      = scrollBar.getCurValue();
            boolean vertical      = Orientation.VERTICAL.equals(scrollBar.getOrientation());

            Vector2f guiSize       = scrollBar.getSize();
            float    arrowSize     = scrollBar.isArrowsEnabled() ? scrollBar.getArrowSize() : 0;
            float    scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
            float    maxValue      = scrollBar.getMaxValue();
            float    minValue      = scrollBar.getMinValue();
            float    valueRange    = maxValue - minValue;
            float    barSize       = scrollBarSize * visibleAmount / valueRange;
            if (barSize < ScrollBar.MIN_SCROLL_SIZE) barSize = ScrollBar.MIN_SCROLL_SIZE;
            float scrollPosAccordingToScrollBounds = (scrollBarSize - barSize) * curValue / valueRange;

            float left, curPos, newVal;
            if (vertical) {
                left = pos.y + scrollPosAccordingToScrollBounds + arrowSize;
                curPos = cursorPosition.y;
            } else {
                left = pos.x + scrollPosAccordingToScrollBounds + arrowSize;
                curPos = cursorPosition.x;
            }
            if (curPos < left) {
                newVal = curValue - 0.5f * visibleAmount * valueRange / (valueRange - visibleAmount);
                if (!released) updateViewport(event, scrollBar, maxValue, minValue, newVal);
                scrollBar.setScrolling(false);
            } else if (curPos > left + barSize) {
                newVal = curValue + 0.5f * visibleAmount * valueRange / (valueRange - visibleAmount);
                if (!released) updateViewport(event, scrollBar, maxValue, minValue, newVal);
                scrollBar.setScrolling(false);
            } else {
                if (released) scrollBar.setScrolling(false);
                else {
                    scrollBar.setScrolling(true);
                }
            }
        }

        private void updateViewport(AbstractEvent event, ScrollBar gui, float maxValue, float minValue, float newVal) {
            if (newVal > maxValue) newVal = maxValue;
            else if (newVal < minValue) newVal = minValue;
            event.getContext().getEventProcessor().pushEvent(new ScrollBarChangeValueEvent(scrollBar, event.getContext(), gui.getCurValue(), newVal));
            gui.setCurValue(newVal);

            Viewport viewport = gui.getViewport();
            if (viewport != null) {
                viewport.updateViewport();
            }
        }
    }

    public static class ScrollBarChangeValueEvent extends AbstractEvent {

        private final float oldValue;
        private final float newValue;

        public ScrollBarChangeValueEvent(Component component, Context context, float oldValue, float newValue) {
            super(component, context);
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public float getNewValue() {
            return newValue;
        }

        public float getOldValue() {
            return oldValue;
        }
    }
}