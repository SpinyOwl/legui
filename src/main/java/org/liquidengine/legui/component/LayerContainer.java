package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.theme.Theme;

/**
 * Layer container. By default it has {@link org.liquidengine.legui.listener.WindowSizeEventListener} which used to resize this container.
 */
public class LayerContainer<T extends Component> extends Container<T> {
    private WindowSizeEventListener listener;

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

    @Override
    public Vector4f getBackgroundColor() {
        return new Vector4f(0, 0, 0, 0);
    }

    @Override
    public void setBackgroundColor(Vector4f backgroundColor) {
    }

    @Override
    public void setBackgroundColor(float r, float g, float b, float a) {
    }

    /**
     * Used to initialize Layer container with default background and border.
     */
    private void initialize() {
        getListenerMap().addListener(WindowSizeEvent.class, listener = new LayerContainerWindowSizeEventListener());
        setBackgroundColor(ColorConstants.transparent());
        setBorder(null);
        Theme.getDefaultTheme().getThemeManager().getComponentTheme(LayerContainer.class).applyAll(this);
    }


    public static class LayerContainerWindowSizeEventListener implements WindowSizeEventListener {
        @Override
        public void process(WindowSizeEvent event) {
            event.getComponent().getSize().set(event.getWidth(), event.getHeight());
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
