package com.spinyowl.legui.component.misc.listener.layercontainer;

import com.spinyowl.legui.event.WindowSizeEvent;
import com.spinyowl.legui.listener.WindowSizeEventListener;

/**
 * Window size event listener for layer container. Used to update layer container size on window
 * resize.
 */
public class LayerContainerWindowSizeEventListener implements WindowSizeEventListener {

  /**
   * Used to process {@link WindowSizeEvent}.
   *
   * @param event event to process.
   */
  @Override
  public void process(WindowSizeEvent event) {
    event.getTargetComponent().getSize().set(event.getWidth(), event.getHeight());
  }
}
