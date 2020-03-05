package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.theme.Themes;

/**
 * Default Container implementation. Used to hold components.
 */
public class Panel extends Component {

    /**
     * Default constructor. Used to create component instance without any parameters. <p> Also if you want to make it easy to use with Json
     * marshaller/unmarshaller component should contain empty constructor.
     */
    public Panel() {
        Themes.getDefaultTheme().applyAll(this);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x x position position in parent component.
     * @param y y position position in parent component.
     * @param width width of component.
     * @param height height of component.
     */
    public Panel(float x, float y, float width, float height) {
        super(x, y, width, height);
        Themes.getDefaultTheme().applyAll(this);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size size of component.
     */
    public Panel(Vector2f position, Vector2f size) {
        super(position, size);
        Themes.getDefaultTheme().applyAll(this);
    }

}
