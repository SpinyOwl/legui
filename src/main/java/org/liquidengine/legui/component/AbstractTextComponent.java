package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;

import java.util.Objects;

public abstract class AbstractTextComponent extends Component implements TextComponent {

    protected TextState textState;

    /**
     * Default constructor. Used to create component instance without any parameters.
     * <p>
     * Also if you want to make it easy to use with Json marshaller/unmarshaller component should contain empty constructor.
     */
    public AbstractTextComponent() {
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param x      x position position in parent component.
     * @param y      y position position in parent component.
     * @param width  width of component.
     * @param height height of component.
     */
    public AbstractTextComponent(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Constructor with position and size parameters.
     *
     * @param position position position in parent component.
     * @param size     size of component.
     */
    public AbstractTextComponent(Vector2f position, Vector2f size) {
        super(position, size);
    }

    @Override
    public TextState getTextState() {
        return textState;
    }

    @Override
    public void setTextState(TextState textState) {
        this.textState = Objects.requireNonNull(textState);
    }
}
