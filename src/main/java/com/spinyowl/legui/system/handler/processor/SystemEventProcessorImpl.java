package com.spinyowl.legui.system.handler.processor;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemEvent;
import com.spinyowl.legui.system.handler.SystemEventHandler;
import com.spinyowl.legui.system.handler.SystemEventHandlerProvider;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SystemEventProcessorImpl implements SystemEventProcessor {

  private Queue<SystemEvent> first = new ConcurrentLinkedQueue<>();
  private Queue<SystemEvent> second = new ConcurrentLinkedQueue<>();

  /**
   * Process events.
   *
   * @param frame   the frame
   * @param context the context
   */
  public void processEvents(Frame frame, Context context) {
    swap();

    for (SystemEvent event = second.poll(); event != null; event = second.poll()) {
      SystemEventHandler processor = SystemEventHandlerProvider.getInstance()
          .getProcessor(event.getClass());
      if (processor != null) {
        processor.handle(event, frame, context);
      }
    }
  }

  private void swap() {
    Queue<SystemEvent> temp = first;
    first = second;
    second = temp;
  }

  /**
   * Push event.
   *
   * @param event the event
   */
  public void pushEvent(SystemEvent event) {
    first.add(event);
  }

  /**
   * Returns true if there are events that should be processed.
   *
   * @return true if there are events that should be processed.
   */
  @Override
  public boolean hasEvents() {
    return !(first.isEmpty() && second.isEmpty());
  }
}
