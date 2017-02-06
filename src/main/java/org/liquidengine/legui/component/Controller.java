package org.liquidengine.legui.component;

import org.joml.Vector2f;

/**
 * Created by ShchAlexander on 01.02.2017.
 */
public abstract class Controller extends Component {
    private Tooltip tooltip;

    private boolean hovered;
    private boolean focused;
    private boolean pressed;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json serializer/deserializer component should contain empty constructor.
     */
    public Controller() {
        super();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component
     * @param y      y position position in parent component
     * @param width  width of component
     * @param height height of component
     */
    public Controller(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component
     * @param size     size of component
     */
    public Controller(Vector2f position, Vector2f size) {
        super(position, size);
    }


    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = new Tooltip(tooltip);
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
