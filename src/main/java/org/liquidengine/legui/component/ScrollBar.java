package org.liquidengine.legui.component;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.Orientation;

/**
 * Created by Shcherbin Alexander on 4/25/2016.
 */
public class ScrollBar extends Component {
    public static final float MIN_SCROLL_SIZE = 1f;
    private Orientation orientation = Orientation.VERTICAL;

    private float minValue = 0f;
    private float maxValue = 100f;
    private float curValue = 0f;

    private float visibleAmount;

    private boolean arrowsEnabled;
    private float arrowSize = 20f;
    private Vector4f arrowColor;

    private boolean scrolling = false;

    private Vector4f scrollColor;
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
        initialize();
    }

    private void initialize() {
        backgroundColor.set(new Vector4f(0, 0, 0, 0.1f));
        scrollColor = new Vector4f(0, 0, 0, 0.4f);
        arrowColor = new Vector4f(1, 1, 1, 0.4f);
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
                Objects.equal(scrollColor, scrollBar.scrollColor) &&
                Objects.equal(viewport, scrollBar.viewport);
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
                scrollColor,
                viewport);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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
                .append("viewport", viewport)
                .toString();
    }
}
