package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.*;
import org.liquidengine.legui.event.LeguiEvent;
import org.liquidengine.legui.event.LeguiMouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.LeguiEventListener;
import org.liquidengine.legui.listener.LeguiMouseClickEventListener;
import org.liquidengine.legui.listener.LeguiMouseDragEventListener;
import org.liquidengine.legui.listener.LeguiScrollEventListener;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.theme.Themes;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * An implementation of a scrollbar.
 */
public class ScrollBar extends Controller {
    // TODO: It would be nice to add Icon here to render arrows.
    public static final float MIN_SCROLL_SIZE = 1f;

    /**
     * Used to determine if scroll bar vertical or horizontal.
     */
    private Orientation orientation = Orientation.VERTICAL;

    /**
     * Minimum value.
     */
    private float minValue = 0f;

    /**
     * Maximum value.
     */
    private float maxValue = 100f;

    /**
     * Current value.
     */
    private float curValue = 0f;

    /**
     * Scroll step. Used to determine delta which should be added on scroll event.
     */
    private float scrollStep = 0.1f;

    /**
     * Used to determine visible part of viewport.
     */
    private float visibleAmount;

    /**
     * Defines if scrollbar has arrows or not.
     */
    private boolean arrowsEnabled = true;

    /**
     * Defines size of arrows.
     */
    private float arrowSize = 16;

    /**
     * Defines arrow color.
     */
    private Vector4f arrowColor = ColorConstants.darkGray();

    /**
     * Defines scrollbar color.
     */
    private Vector4f scrollColor = ColorConstants.darkGray();

    /**
     * Mostly used by event listeners. Shows if scrollbar is currently scrolling or not.
     */
    private boolean scrolling = false;

    /**
     * Viewport.
     */
    private Viewport viewport;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public ScrollBar() {
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public ScrollBar(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public ScrollBar(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }


    /**
     * Default constructor with current value parameter.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     *
     * @param curValue current scroll bar value to set.
     */
    public ScrollBar(float curValue) {
        this();
        this.curValue = curValue;
    }

    /**
     * Constructor with position, size and current value parameters.
     *
     * @param x        x position position in parent component.
     * @param y        y position position in parent component.
     * @param width    width of component.
     * @param height   height of component.
     * @param curValue current scroll bar value to set.
     */
    public ScrollBar(float x, float y, float width, float height, float curValue) {
        this(x, y, width, height);
        this.curValue = curValue;
    }

    /**
     * Constructor with position, size and current value parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     * @param curValue current scroll bar value to set.
     */
    public ScrollBar(Vector2f position, Vector2f size, float curValue) {
        this(position, size);
        this.curValue = curValue;
    }

    /**
     * Used to initialize listeners.
     */
    private void initialize() {
        getListenerMap().addListener(LeguiScrollEvent.class, new LeguiScrollBarScrollListener());
        getListenerMap().addListener(LeguiMouseDragEvent.class, new LeguiScrollBarMouseDragEventListener());
        getListenerMap().addListener(LeguiMouseClickEvent.class, new LeguiScrollBarMouseClickEventListener());
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(ScrollBar.class).applyAll(this);
    }

    /**
     * Determines if scroll bar currently scrolling by user. Used by event listeners.
     *
     * @return true if scrolling.
     */
    public boolean isScrolling() {
        return scrolling;
    }

    /**
     * Used to set scrolling status. By default used by event listeners which implement scrolling behaviour.
     *
     * @param scrolling new status to set.
     */
    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
    }

    /**
     * Returns viewport if scrollbar attached to viewport.
     *
     * @return scrollbar viewport.
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Used to attach scrollbar to viewport. So if scrollbar value updated - called {@link Viewport#updateViewport()} method.
     *
     * @param viewport viewport to set.
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * Returns scrollbar orientation.
     *
     * @return scrollbar orientation.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Used to set scrollbar orientation.
     *
     * @param orientation scrollbar orientation to set.
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns arrow size.
     *
     * @return arrow size.
     */
    public float getArrowSize() {
        return arrowSize;
    }

    /**
     * Used to set arrow size.
     *
     * @param arrowSize arrow size to set.
     */
    public void setArrowSize(float arrowSize) {
        this.arrowSize = arrowSize;
    }

    /**
     * Returns true if arrows enabled.
     *
     * @return true if arrows enabled.
     */
    public boolean isArrowsEnabled() {
        return arrowsEnabled;
    }

    /**
     * Used to enable/disable arrows.
     *
     * @param arrowsEnabled value to enable/disable arrows.
     */
    public void setArrowsEnabled(boolean arrowsEnabled) {
        this.arrowsEnabled = arrowsEnabled;
    }

    /**
     * Returns scrollbar color.
     *
     * @return scrollbar color.
     */
    public Vector4f getScrollColor() {
        return scrollColor;
    }

    /**
     * Used to set scrollbar color.
     *
     * @param scrollColor scrollbar color to set.
     */
    public void setScrollColor(Vector4f scrollColor) {
        this.scrollColor = scrollColor;
    }

    /**
     * Returns scrollbar arrow color.
     *
     * @return scrollbar arrow color.
     */
    public Vector4f getArrowColor() {
        return arrowColor;
    }

    /**
     * Used to set scrollbar arrow color.
     *
     * @param arrowColor scrollbar arrow color to set.
     */
    public void setArrowColor(Vector4f arrowColor) {
        this.arrowColor = arrowColor;
    }

    /**
     * Returns visible amount (if visible whole scrollbar visible amount = {@link #maxValue} - {@link #minValue})
     *
     * @return visible amount.
     */
    public float getVisibleAmount() {
        return visibleAmount;
    }

    /**
     * By default used by event listeners to set visible part of viewport.
     *
     * @param visibleAmount visible size of viewport.
     */
    public void setVisibleAmount(float visibleAmount) {
        if (visibleAmount > minValue && visibleAmount <= maxValue) {
            this.visibleAmount = visibleAmount;
        }
    }

    /**
     * Returns minimum state of scrollbar.
     *
     * @return minimum state of scrollbar.
     */
    public float getMinValue() {
        return minValue;
    }

    /**
     * Used to set minimum state of scrollbar.
     *
     * @param minValue minimum state of scrollbar to set.
     */
    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    /**
     * Returns maximum state of scrollbar.
     *
     * @return maximum state of scrollbar.
     */
    public float getMaxValue() {
        return maxValue;
    }

    /**
     * Used to set maximum state of scrollbar.
     *
     * @param maxValue maximum state of scrollbar to set.
     */
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Returns current state of scrollbar.
     *
     * @return current state of scrollbar.
     */
    public float getCurValue() {
        return curValue;
    }

    /**
     * Used to set current state of scrollbar.
     *
     * @param curValue current state of scrollbar to set.
     */
    public void setCurValue(float curValue) {
        if (curValue < minValue) {
            this.curValue = minValue;
        } else if (curValue > maxValue) {
            this.curValue = maxValue;
        } else {
            this.curValue = curValue;
        }
    }

    /**
     * Returns scrollbar scroll step (used by mouse scroll event listener).
     *
     * @return scrollbar scroll step (used by mouse scroll event listener).
     */
    public float getScrollStep() {
        return scrollStep;
    }

    /**
     * Used to set scrollbar scroll step (used by mouse scroll event listener).
     *
     * @param scrollStep scrollbar scroll step to set.
     */
    public void setScrollStep(float scrollStep) {
        if (scrollStep > 0) {
            this.scrollStep = scrollStep;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ScrollBar scrollBar = (ScrollBar) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(minValue, scrollBar.minValue)
                .append(maxValue, scrollBar.maxValue)
                .append(curValue, scrollBar.curValue)
                .append(scrollStep, scrollBar.scrollStep)
                .append(visibleAmount, scrollBar.visibleAmount)
                .append(arrowsEnabled, scrollBar.arrowsEnabled)
                .append(arrowSize, scrollBar.arrowSize)
                .append(orientation, scrollBar.orientation)
                .append(arrowColor, scrollBar.arrowColor)
                .append(scrollColor, scrollBar.scrollColor)
                .isEquals();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(orientation)
                .append(minValue)
                .append(maxValue)
                .append(curValue)
                .append(scrollStep)
                .append(visibleAmount)
                .append(arrowsEnabled)
                .append(arrowSize)
                .append(arrowColor)
                .append(scrollColor)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("orientation", orientation)
                .append("minValue", minValue)
                .append("maxValue", maxValue)
                .append("curValue", curValue)
                .append("visibleAmount", visibleAmount)
                .append("arrowsEnabled", arrowsEnabled)
                .append("arrowSize", arrowSize)
                .append("arrowColor", arrowColor)
                .append("scrollColor", scrollColor)
                .toString();
    }

    /**
     * Defines contract for listener of {@link LeguiScrollBarChangeValueEvent} event.
     */
    public interface LeguiScrollBarChangeValueEventListener extends LeguiEventListener<LeguiScrollBarChangeValueEvent> {
        void process(LeguiScrollBarChangeValueEvent event);
    }

    /**
     * Default mouse scroll event listener for scrollbar. Generates {@link LeguiScrollBarChangeValueEvent} event.
     */
    public static class LeguiScrollBarScrollListener implements LeguiScrollEventListener {
        public void process(LeguiScrollEvent event) {
            ScrollBar scrollBar     = (ScrollBar) event.getComponent();
            float     maxValue      = scrollBar.getMaxValue();
            float     minValue      = scrollBar.getMinValue();
            float     curValue      = scrollBar.getCurValue();
            float     visibleAmount = scrollBar.getVisibleAmount();
            float     valueRange    = scrollBar.getMaxValue() - scrollBar.getMinValue();
            float     newVal        = (float) (curValue - scrollBar.getScrollStep() * event.getYoffset() * visibleAmount * valueRange / (valueRange - visibleAmount));

            if (newVal > maxValue) newVal = maxValue;
            if (newVal < minValue) newVal = minValue;

            event.getContext().getEventProcessor().pushEvent(new LeguiScrollBarChangeValueEvent<>(scrollBar, event.getContext(), curValue, newVal));
            scrollBar.setCurValue(newVal);

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

    /**
     * Default mouse drag event listener for scrollbar. Generates {@link LeguiScrollBarChangeValueEvent} event.
     */
    public static class LeguiScrollBarMouseDragEventListener implements LeguiMouseDragEventListener {

        @Override
        public void process(LeguiMouseDragEvent event) {
            ScrollBar scrollBar = (ScrollBar) event.getComponent();
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

            float curPos,
                    dpos;
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
            event.getContext().getEventProcessor().pushEvent(new LeguiScrollBarChangeValueEvent<>(scrollBar, event.getContext(), scrollBar.getCurValue(), newVal));
            scrollBar.setCurValue(newVal);

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


    /**
     * Default mouse click event listener for scrollbar. Generates {@link LeguiScrollBarChangeValueEvent} event.
     */
    public static class LeguiScrollBarMouseClickEventListener implements LeguiMouseClickEventListener {

        @Override
        public void process(LeguiMouseClickEvent event) {
            ScrollBar scrollBar = (ScrollBar) event.getComponent();
            boolean   released  = event.getAction() != LeguiMouseClickEvent.MouseClickAction.PRESS;
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

            float left,
                    curPos,
                    newVal;
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

        private void updateViewport(LeguiEvent event, ScrollBar scrollBar, float maxValue, float minValue, float newVal) {
            if (newVal > maxValue) newVal = maxValue;
            else if (newVal < minValue) newVal = minValue;
            event.getContext().getEventProcessor().pushEvent(new LeguiScrollBarChangeValueEvent<>(scrollBar, event.getContext(), scrollBar.getCurValue(), newVal));
            scrollBar.setCurValue(newVal);

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

    /**
     * LeguiEvent generated by default event listeners which shows that scrollbar value was changed.
     */
    public static class LeguiScrollBarChangeValueEvent<T extends ScrollBar> extends LeguiEvent<T> {

        private final float oldValue;
        private final float newValue;

        public LeguiScrollBarChangeValueEvent(T component, LeguiContext context, float oldValue, float newValue) {
            super(component, context);
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        /**
         * Returns new value of scrollbar.
         *
         * @return new value of scrollbar.
         */
        public float getNewValue() {
            return newValue;
        }

        /**
         * Returns old value of scrollbar.
         *
         * @return old value of scrollbar.
         */
        public float getOldValue() {
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            LeguiScrollBarChangeValueEvent that = (LeguiScrollBarChangeValueEvent) o;

            return new EqualsBuilder()
                    .append(getOldValue(), that.getOldValue())
                    .append(getNewValue(), that.getNewValue())
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(getOldValue())
                    .append(getNewValue())
                    .toHashCode();
        }
    }
}