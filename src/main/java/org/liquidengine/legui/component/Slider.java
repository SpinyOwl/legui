package org.liquidengine.legui.component;

import org.joml.Vector4f;
import org.liquidengine.legui.component.intersector.RectangleIntersector;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.listener.component.SliderChangeEventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Shcherbin Alexander on 4/25/2016.
 */
public class Slider extends Component {
    public static final float MAX_VALUE = 100f;
    public static final float MIN_VALUE = 0f;

    private float value;

    private Orientation orientation = Orientation.HORIZONTAL;

    private Vector4f sliderActiveColor = new Vector4f(0, 0, 1, 1);
    private Vector4f sliderColor = new Vector4f(0.7f, 0.7f, 0.7f, 0.4f);

    private float sliderSize = 10f;

    private List<SliderChangeEventListener> sliderChangeEventListeners = new CopyOnWriteArrayList<>();

    public Slider(float value) {
        initialize(value);
    }

    public Slider(float x, float y, float width, float height, float value) {
        super(x, y, width, height);
        initialize(value);
    }

    public Slider() {
        this(0f);
    }

    public Slider(float x, float y, float width, float height) {
        this(x, y, width, height, 0f);
    }

    private void initialize(float value) {
        this.value = value;
        this.backgroundColor.set(0f);
        RectangleIntersector rectangleIntersector = new RectangleIntersector();
        rectangleIntersector.setPaddingLeft(sliderSize / 2f);
        rectangleIntersector.setPaddingRight(sliderSize / 2f);
        this.intersector = rectangleIntersector;
        border.setEnabled(false);
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

    public boolean addSliderChangeEventListener(SliderChangeEventListener sliderUpdateListener) {
        return sliderChangeEventListeners.add(sliderUpdateListener);
    }

    public boolean removeSliderChangeEventListener(Object o) {
        return sliderChangeEventListeners.remove(o);
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
        if (Orientation.VERTICAL.equals(orientation)) {
            ((RectangleIntersector) intersector).setPaddingTop(sliderSize / 2f);
            ((RectangleIntersector) intersector).setPaddingBottom(sliderSize / 2f);
        } else {
            ((RectangleIntersector) intersector).setPaddingLeft(sliderSize / 2f);
            ((RectangleIntersector) intersector).setPaddingRight(sliderSize / 2f);
        }
    }

}
