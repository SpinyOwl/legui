package com.spinyowl.legui.event;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Focus event. Occurs when component receive or lose focus. */
@Getter
@EqualsAndHashCode
@ToString
public class FocusEvent<T extends Component> extends Event<T> {

  /** If component receive focus. */
  private final boolean focused;

  /** Component which receives focus. */
  private final Component nextFocus;

  /**
   * Used to create focus event.
   *
   * @param component event receiver.
   * @param context context.
   * @param nextFocus focus receiver.
   * @param focused state of component.
   * @param frame frame.
   */
  public FocusEvent(
      T component, Context context, Frame frame, Component nextFocus, boolean focused) {
    super(component, context, frame);
    this.focused = focused;
    this.nextFocus = nextFocus;
  }
}
