package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.event.slider.SliderChangeValueEvent;
import org.liquidengine.legui.component.misc.listener.slider.SliderMouseClickEventListener;
import org.liquidengine.legui.component.misc.listener.slider.SliderMouseDragEventListener;
import org.liquidengine.legui.component.misc.listener.slider.SliderScrollEventListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.Themes;

import java.util.List;

/**
 * Implementation of slider controller.
 */
public class Slider extends Component {

    /**
     * Default maximum value for a slider.
     */
    public static final float DEFAULT_MAX_VALUE = 100f;
    /**
     * Default minimum value for a slider.
     */
    public static final float DEFAULT_MIN_VALUE = 0f;

    /**
     * Slider value.
     */
    private float value = 0f;

    /**
     * Slider orientation.
     */
    private Orientation orientation = Orientation.HORIZONTAL;

    /**
     * Slider active color.
     */
    private Vector4f sliderActiveColor = new Vector4f(0, 0, 1, 1);

    /**
     * Slider color.
     */
    private Vector4f sliderColor = new Vector4f(0.7f, 0.7f, 0.7f, 0.4f);

    /**
     * Slider size. Size of slider knob. (Knob size).
     */
    private float sliderSize = 10f;

    /**
     * Maximum value of the slider.
     */
    private float minValue = DEFAULT_MIN_VALUE;

    /**
     * Maximum value of the slider.
     */
    private float maxValue = DEFAULT_MAX_VALUE;

    /**
     * The step size of the slider.
     */
    private float stepSize;

    /**
     * Used to create slider with predefined value.
     *
     * @param value value to set.
     */
    public Slider(float value) {
        initialize(value);
    }

    /**
     * Constructor with position and size parameters and predefined value.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     * @param value    value to set.
     */
    public Slider(Vector2f position, Vector2f size, float value) {
        super(position, size);
        initialize(value);
    }

    /**
     * Constructor with position and size parameters and predefined value.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     * @param value  value to set.
     */
    public Slider(float x, float y, float width, float height, float value) {
        super(x, y, width, height);
        initialize(value);
    }

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public Slider() {
        this(0f);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public Slider(float x, float y, float width, float height) {
        this(x, y, width, height, 0f);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public Slider(Vector2f position, Vector2f size) {
        this(position, size, 0f);
    }

    /**
     * Used to initialize slider with predefined value.
     *
     * @param value value to set.
     */
    private void initialize(float value) {
        this.value = value;
        getStyle().getBackground().setColor(ColorConstants.transparent());
        getStyle().setBorder(null);
        getListenerMap().addListener(ScrollEvent.class, new SliderScrollEventListener());
        getListenerMap().addListener(MouseClickEvent.class, new SliderMouseClickEventListener());
        getListenerMap().addListener(MouseDragEvent.class, new SliderMouseDragEventListener());
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Slider.class).applyAll(this);
    }

    /**
     * Returns the minimum value.
     *
     * @return the minimum value.
     */
    public float getMinValue() {
        return minValue;
    }


    /**
     * Sets the minimum value.
     *
     * @param minValue the minimum value.
     */
    public void setMinValue(final float minValue) {
        if (minValue > this.maxValue) {
            this.maxValue = minValue;
            return;
        }
        this.minValue = minValue;
        setStepSize(getStepSize());
        setValue(getValue());
    }


    /**
     * Returns the maximum value.
     *
     * @return the maximum value.
     */
    public float getMaxValue() {
        return maxValue;
    }


    /**
     * Sets the maximum value.
     *
     * @param maxValue the maximum value.
     */
    public void setMaxValue(final float maxValue) {
        if (maxValue < this.minValue) {
            this.minValue = maxValue;
            return;
        }
        this.maxValue = maxValue;
        setStepSize(getStepSize());
        setValue(getValue());
    }


    /**
     * Returns the step size. Step size equal to 0 means that there is no step size.
     *
     * @return the step size.
     */
    public float getStepSize() {
        return stepSize < 0 ? 0f : stepSize;
    }


    /**
     * Sets the step size. Step size equal to 0 means that there is no step size.
     *
     * @param stepSize the step size.
     */
    public void setStepSize(final float stepSize) {
        this.stepSize = stepSize;
        if (stepSize > 0) {
            float difference = this.maxValue - this.minValue;
            this.stepSize = difference < stepSize ? difference : stepSize;
        }
        setValue(getValue());
    }


    /**
     * Used to set slider intersector.
     *
     * @param intersector intersector.
     */
    @Override
    public void setIntersector(Intersector intersector) {
        if (!(intersector instanceof RectangleIntersector)) {
            return;
        }
        super.setIntersector(intersector);
    }

    /**
     * Returns slider value as a float.
     *
     * @return slider value as a float.
     */
    public float getValue() {
        return value;
    }

    /**
     * Used to set slider value from a float value.
     *
     * @param value new slider value.
     */
    public void setValue(float value) {
        this.value = value;

        // respect step size
        if (this.stepSize > 0) {
            // add half-step size to get the center of the step
            float halfStepSize = this.stepSize / 2f;
            if (this.value < 0f) {
                halfStepSize *= -1;
            }

            final int count = (int) ((this.value + halfStepSize) / this.stepSize);
            this.value = this.stepSize * count;
        }

        // check for min/max values
        if (this.value > this.maxValue) {
            this.value = this.maxValue;
        } else if (this.value < this.minValue) {
            this.value = this.minValue;
        }
    }

    /**
     * Returns slider color. (Inactive slider color).
     *
     * @return slider color.
     */
    public Vector4f getSliderColor() {
        return sliderColor;
    }

    /**
     * Used to set slider color. (Inactive slider color).
     *
     * @param sliderColor new slider color.
     */
    public void setSliderColor(Vector4f sliderColor) {
        this.sliderColor = sliderColor;
    }

    /**
     * Returns slider size. (Knob size).
     *
     * @return slider size.
     */
    public float getSliderSize() {
        return sliderSize;
    }

    /**
     * Used to set slider size. (Knob size).
     *
     * @param sliderSize new slider size.
     */
    public void setSliderSize(float sliderSize) {
        this.sliderSize = sliderSize;
    }

    /**
     * Returns slider active color.
     *
     * @return slider active color.
     */
    public Vector4f getSliderActiveColor() {
        return sliderActiveColor;
    }

    /**
     * Used to set slider active color.
     *
     * @param sliderActiveColor new slider active color.
     */
    public void setSliderActiveColor(Vector4f sliderActiveColor) {
        this.sliderActiveColor = sliderActiveColor;
    }

    /**
     * Returns slider orientation.
     *
     * @return slider orientation.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Used to set slider orientation.
     *
     * @param orientation new slider orientation.
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Used to add event listener for slider change value event.
     *
     * @param eventListener event listener to add.
     */
    public void addSliderChangeValueEventListener(EventListener<SliderChangeValueEvent> eventListener) {
        this.getListenerMap().addListener(SliderChangeValueEvent.class, eventListener);
    }

    /**
     * Returns all event listeners for slider change value event.
     *
     * @return all event listeners for slider change value event.
     */
    public List<EventListener<SliderChangeValueEvent>> getSliderChangeValueEvents() {
        return this.getListenerMap().getListeners(SliderChangeValueEvent.class);
    }

    /**
     * Used to remove event listener for slider change value event.
     *
     * @param eventListener event listener to remove.
     */
    public void removeSliderChangeValueEventListener(EventListener<SliderChangeValueEvent> eventListener) {
        this.getListenerMap().removeListener(SliderChangeValueEvent.class, eventListener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Slider slider = (Slider) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(value, slider.value)
                .append(sliderSize, slider.sliderSize)
                .append(orientation, slider.orientation)
                .append(sliderActiveColor, slider.sliderActiveColor)
                .append(sliderColor, slider.sliderColor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .append(orientation)
                .append(sliderActiveColor)
                .append(sliderColor)
                .append(sliderSize)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", value)
                .append("orientation", orientation)
                .append("sliderActiveColor", sliderActiveColor)
                .append("sliderColor", sliderColor)
                .append("sliderSize", sliderSize)
                .toString();
    }

}
