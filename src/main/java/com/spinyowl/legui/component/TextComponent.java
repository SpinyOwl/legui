package com.spinyowl.legui.component;

import com.spinyowl.legui.component.optional.TextState;

public interface TextComponent {

  /**
   * Returns current text state.
   *
   * @return text state of component.
   */
  TextState getTextState();

  /**
   * Used to set text state
   *
   * @param textState new text state to set.
   * @throws NullPointerException in case if textState is null.
   */
  void setTextState(TextState textState) throws NullPointerException;
}
