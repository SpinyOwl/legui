package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.WindowPosEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemWindowPosEvent;
import java.util.List;

/**
 * This class instance used to handle {@link WindowPosEvent}.
 */
public class WindowPosEventHandler extends AbstractSystemEventHandler<SystemWindowPosEvent> {

  /**
   * This method used to handle {@link WindowPosEvent}.
   *
   * @param event   event to be processed.
   * @param layer   target event layer.
   * @param context context.
   * @return true if this event was handled and should not be handled more.
   */
  @Override
  protected boolean handle(SystemWindowPosEvent event, Layer layer, Context context, Frame frame) {
    pushEvent(layer, event, context, frame);
    return false;
  }

  /**
   * Used to push {@link WindowPosEvent} instance of {@link com.spinyowl.legui.event.Event} to
   * {@link com.spinyowl.legui.listener.processor.EventProcessor}.
   *
   * @param component component for which should be created {@link WindowPosEvent}
   * @param event     event to push.
   * @param context   context.
   */
  private void pushEvent(Component component, SystemWindowPosEvent event, Context context,
      Frame frame) {
    if (!(component.isVisible())) {
      return;
    }
    EventProcessorProvider.getInstance()
        .pushEvent(new WindowPosEvent(component, context, frame, event.xpos, event.ypos));
    List<Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      pushEvent(child, event, context, frame);
    }
  }
}
