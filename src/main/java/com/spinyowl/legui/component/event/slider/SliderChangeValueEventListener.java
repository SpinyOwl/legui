package com.spinyowl.legui.component.event.slider;


import com.spinyowl.legui.listener.EventListener;

/**
 * Slider change value event listener. Used to change slider value. Generates slider value change
 * event.
 */
public interface SliderChangeValueEventListener extends EventListener<SliderChangeValueEvent> {

  /**
   * Used to handle {@link SliderChangeValueEvent} event.
   *
   * @param event event to handle.
   */
  void process(SliderChangeValueEvent event);
}
