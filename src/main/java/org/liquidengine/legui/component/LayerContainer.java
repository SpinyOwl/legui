package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.theme.Themes;

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
     * Returns {@link Vector4f} background color vector where x,y,z,w mapped to r,g,b,a values.
     * <ul>
     * <li>vector.x - red.</li>
     * <li>vector.y - green.</li>
     * <li>vector.z - blue.</li>
     * <li>vector.a - alpha.</li>
     * </ul>
     *
     * @return background color vector.
     */
    @Override
    public Vector4f getBackgroundColor() {
        return new Vector4f(0, 0, 0, 0);
    }

    /**
     * Used to set background color vector where x,y,z,w mapped to r,g,b,a values.
     * <ul>
     * <li>vector.x - red.</li>
     * <li>vector.y - green.</li>
     * <li>vector.z - blue.</li>
     * <li>vector.a - alpha.</li>
     * </ul>
     *
     * @param backgroundColor background color vector.
     */
    @Override
    public void setBackgroundColor(Vector4f backgroundColor) {
        // forbid to change background color
    }

    /**
     * Used to set background color vector.
     *
     * @param r red value.
     * @param g green value.
     * @param b blue value.
     * @param a alpha value.
     */
    @Override
    public void setBackgroundColor(float r, float g, float b, float a) {
        // forbid to change background color
    }

    /**
     * Used to initialize Layer container with default background and border.
     */
    private void initialize() {
        getListenerMap().addListener(WindowSizeEvent.class, new LayerContainerWindowSizeEventListener());
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(LayerContainer.class).applyAll(this);
    }

    /**
     * Window size event listener for layer container. Used to update layer container size on window resize.
     */
    public static class LayerContainerWindowSizeEventListener implements WindowSizeEventListener {

        /**
         * Used to process {@link WindowSizeEvent}.
         *
         * @param event event to process.
         */
        @Override
        public void process(WindowSizeEvent event) {
            event.getComponent().getSize().set(event.getWidth(), event.getHeight());
        }

        /**
         * (non-Javadoc)
         *
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object obj) {
            return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
        }
    }
}
