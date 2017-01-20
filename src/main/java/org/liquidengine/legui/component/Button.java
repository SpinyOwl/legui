package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.util.ColorConstants;


/**
 * An implementation of "push" button.
 * <p>
 * <p>
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class Button extends Component {

    /**
     * Button text state.
     */
    protected TextState textState;

    /**
     * Default button background image.
     */
    protected ImageView backgroundImage;

    /**
     * Background image for focused state.
     */
    protected ImageView focusedBbackgroundImage;

    /**
     * Background image for pressed state.
     */
    protected ImageView pressedBackgroundImage;

    /**
     * Background image for hovered state.
     */
    protected ImageView hoveredBackgroundImage;

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
        this("Button", x, y, width, height);
    }

    /**
     * Creates a button with default text and specified position and size.
     */
    public Button(Vector2f position, Vector2f size) {
        this("Button", position, size);
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
    public Button(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Creates a button with specified text and specified position and size.
     */
    public Button(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Initialize button with specified text
     *
     * @param text used to initialize text state
     */
    private void initialize(String text) {
        this.textState = new TextState(text);
        backgroundColor.set(ColorConstants.white());
        border = new SimpleRectangleLineBorder(ColorConstants.darkGray(), 1);
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


    /**
     * Returns background image
     *
     * @return background image
     */
    public ImageView getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Used to change background image
     *
     * @param backgroundImage background image
     */
    public void setBackgroundImage(ImageView backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * Returns focused background image
     *
     * @return focused background image
     */
    public ImageView getFocusedBbackgroundImage() {
        return focusedBbackgroundImage;
    }

    /**
     * Used to change focused background image
     *
     * @param focusedBbackgroundImage focused background image
     */
    public void setFocusedBbackgroundImage(ImageView focusedBbackgroundImage) {
        this.focusedBbackgroundImage = focusedBbackgroundImage;
    }

    /**
     * Returns pressed background image
     *
     * @return pressed background image
     */
    public ImageView getPressedBackgroundImage() {
        return pressedBackgroundImage;
    }

    /**
     * Used to change pressed background image
     *
     * @param pressedBackgroundImage pressed background image
     */
    public void setPressedBackgroundImage(ImageView pressedBackgroundImage) {
        this.pressedBackgroundImage = pressedBackgroundImage;
    }

    /**
     * Returns hovered background image
     *
     * @return hovered background image
     */
    public ImageView getHoveredBackgroundImage() {
        return hoveredBackgroundImage;
    }

    /**
     * Used to change hovered background image
     *
     * @param hoveredBackgroundImage hovered background image
     */
    public void setHoveredBackgroundImage(ImageView hoveredBackgroundImage) {
        this.hoveredBackgroundImage = hoveredBackgroundImage;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
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

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("textState", textState)
                .toString();
    }
}
