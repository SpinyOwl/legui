package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.component.intersector.RectangleIntersector;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.util.ColorConstants;


/**
 * An implementation of "push" button
 * <p>
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class Button extends Component {

    protected TextState textState;

    /**
     * Creates a button with default text.
     */
    public Button() {
        this("Button");
    }

    /**
     * Creates a button with default text and specified position and size.
     */
    public Button(float x, float y, float width, float height) {
        this(x, y, width, height, "Button");
    }

    /**
     * Creates a button with default text and specified position and size.
     */
    public Button(Vector2f position, Vector2f size) {
        this(position, size, "Button");
    }

    /**
     * Creates a button with specified text.
     */
    public Button(String text) {
        initialize(text);
    }

    /**
     * Creates a button with specified text and specified position and size.
     */
    public Button(float x, float y, float width, float height, String text) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Creates a button with specified text and specified position and size.
     */
    public Button(Vector2f position, Vector2f size, String text) {
        super(position, size);
        initialize(text);
    }

    /**
     * Initialize button with specified text
     *
     * @param text
     */
    private void initialize(String text) {
        this.textState = new TextState(text);
        intersector = new RectangleIntersector();
        backgroundColor.set(0.9f, 0.9f, 0.9f, 1.0f);
        border = new SimpleLineBorder(this, ColorConstants.darkGray(), 1);
        textState.setHorizontalAlign(HorizontalAlign.CENTER);
    }

    /**
     * Returns text data of button
     *
     * @return text state of button
     */
    public TextState getTextState() {
        return textState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Button button = (Button) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(textState, button.textState)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .toString();
    }
}
