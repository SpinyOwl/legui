package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.misc.listener.slider.SliderMouseClickEventListener;
import org.liquidengine.legui.component.misc.listener.slider.SliderMouseDragEventListener;
import org.liquidengine.legui.component.misc.listener.slider.SliderScrollEventListener;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.theme.Themes;

/**
 * Implementation of slider controller.
 */
public class Slider extends Controller {

    /**
     * Maximum value of slider.
     */
    public static final float MAX_VALUE = 100f;

    /**
     * Minimum value of slider.
     */
    public static final float MIN_VALUE = 0f;

    /**
     * Slider value.
     */
    private float value;

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
     * @param size size of component.
     * @param value value to set.
     */
    public Slider(Vector2f position, Vector2f size, float value) {
        super(position, size);
        initialize(value);
    }

    /**
     * Constructor with position and size parameters and predefined value.
     *
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     * @param value value to set.
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
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     */
    public Slider(float x, float y, float width, float height) {
        this(x, y, width, height, 0f);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     */
    public Slider(Vector2f position, Vector2f size) {
        this(position, size, 0);
    }

    /**
     * Used to initialize slider with predefined value.
     *
     * @param value value to set.
     */
    private void initialize(float value) {
        this.value = value;
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);
        getListenerMap().addListener(ScrollEvent.class, new SliderScrollEventListener());
        getListenerMap().addListener(MouseClickEvent.class, new SliderMouseClickEventListener());
        getListenerMap().addListener(MouseDragEvent.class, new SliderMouseDragEventListener());
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Slider.class).applyAll(this);
    }

    /**
     * Used to set slider intersector.
     *
     * @param intersector intersector.
     */
    @Override
    public void setIntersector(Intersector intersector) {
        if (intersector == null || !(intersector instanceof RectangleIntersector)) {
            return;
        }
        super.setIntersector(intersector);
    }

    /**
     * Returns slider value.
     *
     * @return slider value.
     */
    public float getValue() {
        return value;
    }

    /**
     * Used to set slider value.
     *
     * @param value new slider value.
     */
    public void setValue(float value) {
        this.value = value < MIN_VALUE ? MIN_VALUE : value > MAX_VALUE ? MAX_VALUE : value;
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
