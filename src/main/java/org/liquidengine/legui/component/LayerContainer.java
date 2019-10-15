package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.misc.listener.layercontainer.LayerContainerWindowSizeEventListener;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.Themes;

/**
 * Layer container. By default it has {@link org.liquidengine.legui.listener.WindowSizeEventListener} which used to resize this container.
 */
public class LayerContainer extends Component {

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public LayerContainer() {
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
    public LayerContainer(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     */
    public LayerContainer(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    /**
     * Used to initialize Layer container with default background and border.
     */
    private void initialize() {
        getListenerMap().addListener(WindowSizeEvent.class, new LayerContainerWindowSizeEventListener());
        getStyle().getBackground().setColor(ColorConstants.transparent());
        getStyle().setBorder(null);
        setFocusable(false);
        setTabFocusable(false);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(LayerContainer.class).applyAll(this);
    }

}
