package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.animation.Animation;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.component.misc.animation.scrollbar.ScrollBarAnimation;
import org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarMouseClickEventListener;
import org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarMouseDragEventListener;
import org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarScrollListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.Themes;

import java.util.List;

/**
 * An implementation of a scrollbar.
 */
public class ScrollBar extends Component {

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
    private Animation animation;

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public ScrollBar() {
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
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
     * @param size size of component.
     */
    public ScrollBar(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }


    /**
     * Default constructor with current value parameter. <p> Also if you want to make it easy to use with Json marshaller/unmarshaller component should contain
     * empty constructor.
     *
     * @param curValue current scroll bar value to set.
     */
    public ScrollBar(float curValue) {
        this();
        setCurValue(curValue);
    }

    /**
     * Constructor with position, size and current value parameters.
     *
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     * @param curValue current scroll bar value to set.
     */
    public ScrollBar(float x, float y, float width, float height, float curValue) {
        this(x, y, width, height);
        setCurValue(curValue);
    }

    /**
     * Constructor with position, size and current value parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     * @param curValue current scroll bar value to set.
     */
    public ScrollBar(Vector2f position, Vector2f size, float curValue) {
        this(position, size);
        setCurValue(curValue);
    }

    /**
     * Used to initialize listeners.
     */
    private void initialize() {
        getListenerMap().addListener(ScrollEvent.class, new ScrollBarScrollListener());
        getListenerMap().addListener(MouseDragEvent.class, new ScrollBarMouseDragEventListener());
        getListenerMap().addListener(MouseClickEvent.class, new ScrollBarMouseClickEventListener());

        animation = new ScrollBarAnimation(this);
        animation.startAnimation();

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
     * Used to attach scrollbar to viewport.
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
        if (orientation != null) {
            this.orientation = orientation;
        }
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
     * Used to add event listener for scroll bar change value event.
     *
     * @param eventListener event listener to add.
     */
    public void addScrollBarChangeValueEventListener(EventListener<ScrollBarChangeValueEvent> eventListener) {
        this.getListenerMap().addListener(ScrollBarChangeValueEvent.class, eventListener);
    }

    /**
     * Returns all event listeners for scroll bar change value event.
     *
     * @return all event listeners for scroll bar change value event.
     */
    public List<EventListener<ScrollBarChangeValueEvent>> getScrollBarChangeValueEvents() {
        return this.getListenerMap().getListeners(ScrollBarChangeValueEvent.class);
    }

    /**
     * Used to remove event listener for scroll bar change value event.
     *
     * @param eventListener event listener to remove.
     */
    public void removeScrollBarChangeValueEventListener(EventListener<ScrollBarChangeValueEvent> eventListener) {
        this.getListenerMap().removeListener(ScrollBarChangeValueEvent.class, eventListener);
    }

    /**
     * Returns ScrollBarAnimation.
     *
     * @return scroll bar animation.
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Used to set scroll bar animation. Automatically starts animation.
     *
     * @param animation scroll bar animation to set.
     */
    public void setAnimation(Animation animation) {
        if (this.animation != null) {
            this.animation.stopAnimation();
        }
        this.animation = animation;
        if (animation != null) {
            this.animation.startAnimation();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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


}