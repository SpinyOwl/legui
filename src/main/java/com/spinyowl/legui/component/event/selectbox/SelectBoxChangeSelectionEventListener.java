package com.spinyowl.legui.component.event.selectbox;

import com.spinyowl.legui.listener.EventListener;


public interface SelectBoxChangeSelectionEventListener<T> extends
    EventListener<SelectBoxChangeSelectionEvent<T>> {

  /**
   * Used to handle {@link SelectBoxChangeSelectionEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(SelectBoxChangeSelectionEvent<T> event);

}
