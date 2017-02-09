package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class SystemEventProcessor {
    private final Frame   frame;
    private final Context context;
    private Queue<SystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public SystemEventProcessor(Frame frame, Context context, CallbackKeeper keeper) {
        this.frame = frame;
        this.context = context;
        addCallbacks(keeper);
    }

    public SystemEventProcessor(Frame frame, Context context) {
        this.frame = frame;
        this.context = context;
    }

    public void processEvent() {
        for (SystemEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            SystemEventHandler processor = SystemEventHandlerProvider.getInstance().getProcessor(event.getClass());
            if (processor != null) processor.process(event, frame, context);
        }
    }

    public void pushEvent(SystemEvent event) {
        eventQueue.add(event);
    }


    private void addCallbacks(CallbackKeeper guiCallbackKeeper) {
        //@formatter:off
        guiCallbackKeeper.getChainCharCallback().add                 ((window, codepoint)
                -> eventQueue.add(new SystemCharEvent                 (window, codepoint)));

        guiCallbackKeeper.getChainDropCallback().add                 ((window, count, names)
                -> eventQueue.add(new SystemDropEvent                 (window, count, names)));

        guiCallbackKeeper.getChainKeyCallback().add                  ((window, key, scancode, action, mods)
                -> eventQueue.add(new SystemKeyEvent                  (window, key, scancode, action, mods)));

        guiCallbackKeeper.getChainScrollCallback().add               ((window, xoffset, yoffset)
                -> eventQueue.add(new SystemScrollEvent               (window, xoffset, yoffset)));

        guiCallbackKeeper.getChainCharModsCallback().add             ((window, codepoint, mods)
                -> eventQueue.add(new SystemCharModsEvent             (window, codepoint, mods)));

        guiCallbackKeeper.getChainCursorEnterCallback().add          ((window, entered)
                -> eventQueue.add(new SystemCursorEnterEvent          (window, entered)));

        guiCallbackKeeper.getChainFramebufferSizeCallback().add      ((window, width, height)
                -> eventQueue.add(new SystemFramebufferSizeEvent      (window, width, height)));

        guiCallbackKeeper.getChainMouseButtonCallback().add          ((window, button, action, mods)
                -> eventQueue.add(new SystemMouseClickEvent           (window, button, action, mods)));

        guiCallbackKeeper.getChainCursorPosCallback().add            ((window, xpos, ypos)
                -> eventQueue.add(new SystemCursorPosEvent            (window, xpos, ypos)));

        guiCallbackKeeper.getChainWindowCloseCallback().add          ((window)
                -> eventQueue.add(new SystemWindowCloseEvent          (window)));

        guiCallbackKeeper.getChainWindowFocusCallback().add          ((window, focused)
                -> eventQueue.add(new SystemWindowFocusEvent          (window, focused)));

        guiCallbackKeeper.getChainWindowIconifyCallback().add        ((window, iconified)
                -> eventQueue.add(new SystemWindowIconifyEvent        (window, iconified)));

        guiCallbackKeeper.getChainWindowPosCallback().add            ((window, xpos, ypos)
                -> eventQueue.add(new SystemWindowPosEvent            (window, xpos, ypos)));

        guiCallbackKeeper.getChainWindowRefreshCallback().add        (window
                -> eventQueue.add(new SystemWindowRefreshEvent       (window)));

        guiCallbackKeeper.getChainWindowSizeCallback().add           ((window, width, height)
                -> eventQueue.add(new SystemWindowSizeEvent           (window, width, height)));
        //@formatter:on
    }
}
