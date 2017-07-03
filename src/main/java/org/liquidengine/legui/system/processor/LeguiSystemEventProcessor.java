package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class LeguiSystemEventProcessor {
    private final Frame        frame;
    private final LeguiContext context;
    private Queue<LeguiSystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public LeguiSystemEventProcessor(Frame frame, LeguiContext context, CallbackKeeper keeper) {
        this.frame = frame;
        this.context = context;
        addCallbacks(keeper);
    }

    public LeguiSystemEventProcessor(Frame frame, LeguiContext context) {
        this.frame = frame;
        this.context = context;
    }

    public void processEvent() {
        for (LeguiSystemEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            LeguiSystemEventHandler processor = SystemEventHandlerProvider.getInstance().getProcessor(event.getClass());
            if (processor != null) processor.handle(event, frame, context);
        }
    }

    public void pushEvent(LeguiSystemEvent event) {
        eventQueue.add(event);
    }


    private void addCallbacks(CallbackKeeper guiCallbackKeeper) {
        //@formatter:off
        guiCallbackKeeper.getChainCharCallback().add                 ((window, codepoint)
                -> eventQueue.add(new LeguiSystemCharEvent                 (window, codepoint)));

        guiCallbackKeeper.getChainDropCallback().add                 ((window, count, names)
                -> eventQueue.add(new LeguiSystemDropEvent                 (window, count, names)));

        guiCallbackKeeper.getChainKeyCallback().add                  ((window, key, scancode, action, mods)
                -> eventQueue.add(new LeguiSystemKeyEvent                  (window, key, scancode, action, mods)));

        guiCallbackKeeper.getChainScrollCallback().add               ((window, xoffset, yoffset)
                -> eventQueue.add(new LeguiSystemScrollEvent               (window, xoffset, yoffset)));

        guiCallbackKeeper.getChainCharModsCallback().add             ((window, codepoint, mods)
                -> eventQueue.add(new LeguiSystemCharModsEvent             (window, codepoint, mods)));

        guiCallbackKeeper.getChainCursorEnterCallback().add          ((window, entered)
                -> eventQueue.add(new LeguiSystemCursorEnterEvent          (window, entered)));

        guiCallbackKeeper.getChainFramebufferSizeCallback().add      ((window, width, height)
                -> eventQueue.add(new LeguiSystemFramebufferSizeEvent      (window, width, height)));

        guiCallbackKeeper.getChainMouseButtonCallback().add          ((window, button, action, mods)
                -> eventQueue.add(new LeguiSystemMouseClickEvent           (window, button, action, mods)));

        guiCallbackKeeper.getChainCursorPosCallback().add            ((window, xpos, ypos)
                -> eventQueue.add(new LeguiSystemCursorPosEvent            (window, xpos, ypos)));

        guiCallbackKeeper.getChainWindowCloseCallback().add          ((window)
                -> eventQueue.add(new LeguiSystemWindowCloseEvent          (window)));

        guiCallbackKeeper.getChainWindowFocusCallback().add          ((window, focused)
                -> eventQueue.add(new LeguiSystemWindowFocusEvent          (window, focused)));

        guiCallbackKeeper.getChainWindowIconifyCallback().add        ((window, iconified)
                -> eventQueue.add(new LeguiSystemWindowIconifyEvent        (window, iconified)));

        guiCallbackKeeper.getChainWindowPosCallback().add            ((window, xpos, ypos)
                -> eventQueue.add(new LeguiSystemWindowPosEvent            (window, xpos, ypos)));

        guiCallbackKeeper.getChainWindowRefreshCallback().add        (window
                -> eventQueue.add(new LeguiSystemWindowRefreshEvent       (window)));

        guiCallbackKeeper.getChainWindowSizeCallback().add           ((window, width, height)
                -> eventQueue.add(new LeguiSystemWindowSizeEvent           (window, width, height)));
        //@formatter:on
    }
}
