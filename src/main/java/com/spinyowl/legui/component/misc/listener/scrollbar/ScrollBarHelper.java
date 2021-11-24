package com.spinyowl.legui.component.misc.listener.scrollbar;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;

public final class ScrollBarHelper {

  private ScrollBarHelper() {
  }

  public static void updateScrollBarValue(double offset, Context context, Frame frame,
      ScrollBar scrollBar) {
    float maxValue = scrollBar.getMaxValue();
    float minValue = scrollBar.getMinValue();
    float curValue = scrollBar.getCurValue();
    float visibleAmount = scrollBar.getVisibleAmount();
    float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
    if (valueRange - visibleAmount < 0.001f) {
      return;
    }
    float newVal = (float) (curValue
        - scrollBar.getScrollStep() * offset * visibleAmount * valueRange / (valueRange
        - visibleAmount));

    if (newVal > maxValue) {
      newVal = maxValue;
    }
    if (newVal < minValue) {
      newVal = minValue;
    }

    EventProcessorProvider.getInstance()
        .pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, context, frame, curValue, newVal));
    scrollBar.setCurValue(newVal);
  }

}
