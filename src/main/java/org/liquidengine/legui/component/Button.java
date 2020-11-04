package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.event.button.ButtonContentChangeEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.font.TextDirection;
import org.liquidengine.legui.theme.Themes;

import java.util.function.BiConsumer;

/**
 * An implementation of "push" button.
 */
public class Button extends AbstractTextComponent {

    /**
     * Default text for button component.
     */
    public static final String DEFAULT_BUTTON_TEXT = "Button";

    /**
     * Used to set text direction (vertical, horizontal).
     */
    private TextDirection textDirection = TextDirection.HORIZONTAL;

    /**
     * Creates a button with default text.
     */
    public Button() {
        this(DEFAULT_BUTTON_TEXT);
    }

    /**
     * Creates a button with default text and specified position and size.
     *
     * @param x      x position in parent.
     * @param y      y position in parent.
     * @param width  width of component.
     * @param height height of component.
     */
    public Button(float x, float y, float width, float height) {
        this(DEFAULT_BUTTON_TEXT, x, y, width, height);
    }

    /**
     * Creates a button with default text and specified position and size.
     *
     * @param position position in parent.
     * @param size     size of component.
     */
    public Button(Vector2f position, Vector2f size) {
        this(DEFAULT_BUTTON_TEXT, position, size);
    }

    /**
     * Creates a button with specified text.
     *
     * @param text button text.
     */
    public Button(String text) {
        initialize(text);
    }

    /**
     * Creates a button with specified text and specified position and size.
     *
     * @param text   button text.
     * @param x      x position in parent.
     * @param y      y position in parent.
     * @param width  width of component.
     * @param height height of component.
     */
    public Button(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Creates a button with specified text and specified position and size.
     *
     * @param text     button text.
     * @param position position in parent.
     * @param size     size of component.
     */
    public Button(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Initialize button with specified text.
     *
     * @param text used to initialize text state.
     */
    private void initialize(String text) {
        BiConsumer<String, String> callback = (oldValue, newValue) ->
                EventProcessorProvider.getInstance().pushEvent(new ButtonContentChangeEvent(this, null, this.getFrame(), oldValue, newValue));
        this.textState = new TextState(text, callback);
        getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Button.class).applyAll(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Button button = (Button) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getTextState(), button.getTextState())
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

    public TextDirection getTextDirection() {
        return textDirection;
    }

    public void setTextDirection(TextDirection textDirection) {
        this.textDirection = textDirection;
    }
}