package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;

/**
 * Layer container. By default it has {@link org.liquidengine.legui.listener.WindowSizeEventListener} which used to resize this container.
 */
public class LayerContainer<T extends Component> extends Container<T> {
    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with
     * Json marshaller/unmarshaller component should contain empty constructor.
     */
    public LayerContainer() {
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
    public LayerContainer(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public LayerContainer(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Used to initialize Layer container with default background and border.
     */
    private void initialize() {
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);
    }

    /**
     * (non-Javadoc)
     *
     * @param o
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
