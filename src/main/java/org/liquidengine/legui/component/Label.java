package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.TextDirection;
import org.liquidengine.legui.theme.Themes;

import java.util.Objects;

/**
 * Class represent single line non-editable text component.
 */
public class Label extends Component implements TextComponent {

    /**
     * Default label text.
     */
    public static final String DEFAULT_LABEL_TEXT = "Label";

    /**
     * Used to hold text state of component.
     */
    private TextState textState = new TextState();

    /**
     * Used to set text direction (vertical, horizontal).
     */
    private TextDirection textDirection = TextDirection.HORIZONTAL;

    /**
     * Default constructor. Creates label with 'Label' text.
     */
    public Label() {
        this(DEFAULT_LABEL_TEXT);
    }

    /**
     * Creates label with specified size and on specified position.
     *
     * @param x      x position.
     * @param y      y position.
     * @param width  label width.
     * @param height label height.
     */
    public Label(float x, float y, float width, float height) {
        this(DEFAULT_LABEL_TEXT, x, y, width, height);
    }

    /**
     * Creates label with specified size and on specified position.
     *
     * @param position label position.
     * @param size     label size.
     */
    public Label(Vector2f position, Vector2f size) {
        this(DEFAULT_LABEL_TEXT, position, size);
    }

    /**
     * Creates label with specified text.
     *
     * @param text text to set.
     */
    public Label(String text) {
        initialize(text);
    }

    /**
     * Creates label with specified text, size and on specified position.
     *
     * @param text   text to set.
     * @param x      x position.
     * @param y      y position.
     * @param width  label width.
     * @param height label height.
     */
    public Label(String text, float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize(text);
    }

    /**
     * Creates label with specified text, size and on specified position.
     *
     * @param text     text to set.
     * @param position label position.
     * @param size     label size.
     */
    public Label(String text, Vector2f position, Vector2f size) {
        super(position, size);
        initialize(text);
    }

    /**
     * Used to initialize label.
     *
     * @param text text to set.
     */
    private void initialize(String text) {
        textState.setText(text);
        getStyle().getBackground().setColor(ColorConstants.transparent());
        getStyle().setBorder(null);
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(Label.class).applyAll(this);
    }

    /**
     * Returns current text state.
     *
     * @return text state of component.
     */
    public TextState getTextState() {
        return textState;
    }


    public TextDirection getTextDirection() {
        return textDirection;
    }

    public void setTextDirection(TextDirection textDirection) {
        this.textDirection = Objects.requireNonNull(textDirection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Label label = (Label) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(textState, label.textState)
                .append(textDirection, label.textDirection)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(textState)
                .append(textDirection)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("textState", textState)
                .append("textDirection", textDirection)
                .toString();
    }

}
