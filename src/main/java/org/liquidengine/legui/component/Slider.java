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
import org.liquidengine.legui.event.LeguiMouseClickEvent;
import org.liquidengine.legui.event.LeguiMouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.listener.*;
import org.liquidengine.legui.listener.LeguiMouseClickEventListener;
import org.liquidengine.legui.listener.LeguiScrollEventListener;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.theme.Themes;

/**
 * Implementation of slider controller.
 */
public class Slider extends Controller {

    public static final float MAX_VALUE = 100f;
    public static final float MIN_VALUE = 0f;

    private float value;

    private Orientation orientation = Orientation.HORIZONTAL;

    private Vector4f sliderActiveColor = new Vector4f(0, 0, 1, 1);
    private Vector4f sliderColor       = new Vector4f(0.7f, 0.7f, 0.7f, 0.4f);

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
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
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
        getListenerMap().addListener(LeguiScrollEvent.class, new LeguiSliderScrollEventListener());
        getListenerMap().addListener(LeguiMouseClickEvent.class, new LeguiSliderMouseClickEventListener());
        getListenerMap().addListener(LeguiMouseDragEvent.class, new LeguiSliderMouseDragEventListener());
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Slider.class).applyAll(this);
    }

    @Override
    public void setIntersector(Intersector intersector) {
        if (intersector == null || !(intersector instanceof RectangleIntersector)) return;
        super.setIntersector(intersector);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value < MIN_VALUE ? MIN_VALUE : value > MAX_VALUE ? MAX_VALUE : value;
    }

    public Vector4f getSliderColor() {
        return sliderColor;
    }

    public void setSliderColor(Vector4f sliderColor) {
        this.sliderColor = sliderColor;
    }

    public float getSliderSize() {
        return sliderSize;
    }

    public void setSliderSize(float sliderSize) {
        this.sliderSize = sliderSize;
    }


    public Vector4f getSliderActiveColor() {
        return sliderActiveColor;
    }

    public void setSliderActiveColor(Vector4f sliderActiveColor) {
        this.sliderActiveColor = sliderActiveColor;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

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

    public interface LeguiSliderChangeValueEventListener extends LeguiEventListener<LeguiSliderChangeValueEvent> {
        void process(LeguiSliderChangeValueEvent event);
    }

    public static class LeguiSliderScrollEventListener implements LeguiScrollEventListener {

        @Override
        public void process(LeguiScrollEvent event) {
            Slider slider   = (Slider) event.getComponent();
            float  curValue = slider.getValue();
            float  value    = (float) (curValue + event.getYoffset());

            if (value > MAX_VALUE) value = MAX_VALUE;
            if (value < MIN_VALUE) value = MIN_VALUE;

            event.getContext().getEventProcessor().pushEvent(new LeguiSliderChangeValueEvent(slider, event.getContext(), slider.getValue(), value));
            slider.setValue(value);
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    public static class LeguiSliderMouseClickEventListener implements LeguiMouseClickEventListener {

        @Override
        public void process(LeguiMouseClickEvent event) {
            if (event.getButton().equals(Mouse.MouseButton.MOUSE_BUTTON_LEFT) && event.getAction() == LeguiMouseClickEvent.MouseClickAction.PRESS) {
                Slider   slider = (Slider) event.getComponent();
                Vector2f pos    = slider.getScreenPosition();

                Vector2f cursorPosition = Mouse.getCursorPosition();
                float    value;
                if (Orientation.VERTICAL.equals(slider.getOrientation())) {
                    value = 100f * (pos.y + slider.getSize().y - cursorPosition.y - slider.sliderSize / 2f) / (slider.getSize().y - slider.sliderSize);
                } else {
                    value = 100f * (cursorPosition.x - pos.x - slider.sliderSize / 2f) / (slider.getSize().x - slider.sliderSize);
                }
                if (value > MAX_VALUE) value = MAX_VALUE;
                if (value < MIN_VALUE) value = MIN_VALUE;
                event.getContext().getEventProcessor().pushEvent(new LeguiSliderChangeValueEvent(slider, event.getContext(), slider.getValue(), value));
                slider.setValue(value);
            }
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    public static class LeguiSliderMouseDragEventListener implements LeguiMouseDragEventListener {

        @Override
        public void process(LeguiMouseDragEvent event) {
            Slider slider = (Slider) event.getComponent();
            if (!Mouse.MouseButton.MOUSE_BUTTON_LEFT.isPressed()) return;

            Vector2f pos = slider.getScreenPosition();

            Vector2f cursorPosition = Mouse.getCursorPosition();
            float    value;
            if (Orientation.VERTICAL.equals(slider.getOrientation())) {
                value = 100f * ((pos.y + slider.getSize().y) - cursorPosition.y - slider.sliderSize / 2f) / (slider.getSize().y - slider.sliderSize);
            } else {
                value = 100f * (cursorPosition.x - pos.x - slider.sliderSize / 2f) / (slider.getSize().x - slider.sliderSize);
            }

            if (value > MAX_VALUE) value = MAX_VALUE;
            if (value < MIN_VALUE) value = MIN_VALUE;

            event.getContext().getEventProcessor().pushEvent(new LeguiSliderChangeValueEvent(slider, event.getContext(), slider.getValue(), value));
            slider.setValue(value);
        }

        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }

    public static class LeguiSliderChangeValueEvent<T extends Slider> extends LeguiEvent<T> {

        private final float oldValue;
        private final float newValue;

        public LeguiSliderChangeValueEvent(T component, LeguiContext context, float oldValue, float newValue) {
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
