package com.spinyowl.legui.component;

import com.spinyowl.legui.component.event.textinput.TextInputContentChangeEvent;
import com.spinyowl.legui.component.misc.listener.text.CopyTextEventListener;
import com.spinyowl.legui.component.misc.listener.text.SelectAllTextEventListener;
import com.spinyowl.legui.component.misc.listener.textinput.CutTextInputKeyboardEventListener;
import com.spinyowl.legui.component.misc.listener.textinput.PasteTextInputKeyboardEventListener;
import com.spinyowl.legui.component.misc.listener.textinput.TextInputCharEventListener;
import com.spinyowl.legui.component.misc.listener.textinput.TextInputDragEventListener;
import com.spinyowl.legui.component.misc.listener.textinput.TextInputKeyEventListener;
import com.spinyowl.legui.component.misc.listener.textinput.TextInputMouseClickEventListener;
import com.spinyowl.legui.component.optional.TextState;
import com.spinyowl.legui.event.CharEvent;
import com.spinyowl.legui.event.KeyboardEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.listener.EventListener;
import com.spinyowl.legui.theme.Themes;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;

/**
 * Text input is a single line text component which can be used to enter text.
 */
public class TextInput extends AbstractTextComponent {

  /**
   * Default constructor. Used to create component instance without any parameters. <p> Also if you
   * want to make it easy to use with Json marshaller/unmarshaller component should contain empty
   * constructor.
   */
  public TextInput() {
    initialize("");
  }

  /**
   * Constructor with position and size parameters.
   *
   * @param x      x position position in parent component.
   * @param y      y position position in parent component.
   * @param width  width of component.
   * @param height height of component.
   */
  public TextInput(float x, float y, float width, float height) {
    super(x, y, width, height);
    initialize("");
  }

  /**
   * Constructor with position and size parameters.
   *
   * @param position position position in parent component.
   * @param size     size of component.
   */
  public TextInput(Vector2f position, Vector2f size) {
    super(position, size);
    initialize("");
  }

  /**
   * Default constructor with text to set. <p> Also if you want to make it easy to use with Json
   * marshaller/unmarshaller component should contain empty constructor.
   *
   * @param text text to set.
   */
  public TextInput(String text) {
    initialize(text);
  }

  /**
   * Constructor with text, position and size parameters.
   *
   * @param text   text to set.
   * @param x      x position position in parent component.
   * @param y      y position position in parent component.
   * @param width  width of component.
   * @param height height of component.
   */
  public TextInput(String text, float x, float y, float width, float height) {
    super(x, y, width, height);
    initialize(text);
  }

  /**
   * Constructor with text, position and size parameters.
   *
   * @param text     text to set.
   * @param position position position in parent component.
   * @param size     size of component.
   */
  public TextInput(String text, Vector2f position, Vector2f size) {
    super(position, size);
    initialize(text);
  }

  /**
   * Used to initialize text input.
   *
   * @param text text to set.
   */
  private void initialize(String text) {
    textState = new TextState(text);
    getStyle().setPadding(1f, 5f);

    getListenerMap().addListener(KeyboardEvent.class, new TextInputKeyEventListener());
    getListenerMap().addListener(KeyboardEvent.class, new SelectAllTextEventListener());
    getListenerMap().addListener(KeyboardEvent.class, new CopyTextEventListener());
    getListenerMap().addListener(KeyboardEvent.class, new PasteTextInputKeyboardEventListener());
    getListenerMap().addListener(KeyboardEvent.class, new CutTextInputKeyboardEventListener());
    getListenerMap().addListener(MouseClickEvent.class, new TextInputMouseClickEventListener());
    getListenerMap().addListener(MouseDragEvent.class, new TextInputDragEventListener());
    getListenerMap().addListener(CharEvent.class, new TextInputCharEventListener());

    Themes.getDefaultTheme().getThemeManager().getComponentTheme(TextInput.class).applyAll(this);
  }

  /**
   * Returns mouse caret position.
   *
   * @return mouse caret position.
   */
  public int getMouseCaretPosition() {
    return textState.getMouseCaretPosition();
  }

  /**
   * Used to set mouse caret position.
   *
   * @param mouseCaretPosition mouse caret position to set.
   */
  public void setMouseCaretPosition(int mouseCaretPosition) {
    textState.setMouseCaretPosition(mouseCaretPosition);
  }

  /**
   * Returns true if text is editable.
   *
   * @return true if text is editable.
   */
  public boolean isEditable() {
    return textState.isEditable();
  }

  /**
   * Used to set editable text or not.
   *
   * @param editable editable text or not.
   */
  public void setEditable(boolean editable) {
    textState.setEditable(editable);
  }

  /**
   * Returns caret position.
   *
   * @return caret position.
   */
  public int getCaretPosition() {
    return textState.getCaretPosition();
  }

  /**
   * Used to set caret position.
   *
   * @param caretPosition caret position to set.
   */
  public void setCaretPosition(int caretPosition) {
    textState.setCaretPosition(caretPosition);
  }

  /**
   * Returns start selection index.
   *
   * @return start selection index.
   */
  public int getStartSelectionIndex() {
    return textState.getStartSelectionIndex();
  }

  /**
   * Used to set start selection index.
   *
   * @param startSelectionIndex start selection index to set.
   */
  public void setStartSelectionIndex(int startSelectionIndex) {
    textState.setStartSelectionIndex(startSelectionIndex);
  }

  /**
   * Returns end selection index.
   *
   * @return end selection index.
   */
  public int getEndSelectionIndex() {
    return textState.getEndSelectionIndex();
  }

  /**
   * Used to set end selection index.
   *
   * @param endSelectionIndex end selection index to set.
   */
  public void setEndSelectionIndex(int endSelectionIndex) {
    textState.setEndSelectionIndex(endSelectionIndex);
  }

  /**
   * Returns selected text.
   *
   * @return selected text.
   */
  public String getSelection() {
    return textState.getSelection();
  }

  /**
   * Used to add event listener for text input content change event.
   *
   * @param eventListener event listener to add.
   */
  public void addTextInputContentChangeEventListener(
      EventListener<TextInputContentChangeEvent> eventListener) {
    this.getListenerMap().addListener(TextInputContentChangeEvent.class, eventListener);
  }

  /**
   * Returns all event listeners for text input content change event.
   *
   * @return all event listeners for text input content change event.
   */
  public List<EventListener<TextInputContentChangeEvent>> getTextInputContentChangeEvents() {
    return this.getListenerMap().getListeners(TextInputContentChangeEvent.class);
  }

  /**
   * Used to remove event listener for text input content change event.
   *
   * @param eventListener event listener to remove.
   */
  public void removeTextInputContentChangeEventListener(
      EventListener<TextInputContentChangeEvent> eventListener) {
    this.getListenerMap().removeListener(TextInputContentChangeEvent.class, eventListener);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("textState", textState)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TextInput input = (TextInput) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(textState, input.textState)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(textState)
        .toHashCode();
  }

}
