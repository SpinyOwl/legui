package com.spinyowl.legui.system.layout;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.event.component.ChangePositionEvent;
import com.spinyowl.legui.component.event.component.ChangeSizeEvent;
import com.spinyowl.legui.system.context.Context;

/**
 * Layout interface..
 */
public interface Layout {

  /**
   * Used to lay out child components for parent component.
   * <p>
   * Do not generate {@link ChangePositionEvent} and {@link ChangeSizeEvent} events.
   *
   * @param parent component to lay out.
   */
  void layout(Component parent);

  /**
   * Used to lay out child components for parent component.
   * <p>
   * Generates {@link ChangePositionEvent} and {@link ChangeSizeEvent} events.
   *
   * @param parent  component to lay out.
   * @param frame   component frame (for event generation).
   * @param context context (used for event generation).
   */
  void layout(Component parent, Frame frame, Context context);

}
