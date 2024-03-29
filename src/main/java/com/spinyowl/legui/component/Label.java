package com.spinyowl.legui.component;

import com.spinyowl.legui.component.event.label.LabelContentChangeEvent;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.font.TextDirection;
import com.spinyowl.legui.theme.Themes;
import java.util.Objects;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;

/**
 * Class represent single line non-editable text component.
 */
public class Label extends AbstractTextComponent {

  /**
   * Default label text.
   */
  public static final String DEFAULT_LABEL_TEXT = "Label";

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

    BiConsumer<String, String> callback = (oldValue, newValue) ->
        EventProcessorProvider.getInstance().pushEvent(
            new LabelContentChangeEvent(this, null, this.getFrame(), oldValue, newValue));

    textState = new TextState(text, callback);

    getStyle().getBackground().setColor(ColorConstants.transparent());
    getStyle().setBorder(null);
    Themes.getDefaultTheme().getThemeManager().getComponentTheme(Label.class).applyAll(this);
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
