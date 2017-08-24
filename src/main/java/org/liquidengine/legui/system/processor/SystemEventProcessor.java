package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.*;
import org.lwjgl.glfw.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class SystemEventProcessor {

    private Queue<SystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public void processEvents(Frame frame, Context context) {
        for (SystemEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            SystemEventHandler processor = SystemEventHandlerProvider.getInstance().getProcessor(event.getClass());
            if (processor != null) {
                processor.handle(event, frame, context);
            }
        }
    }

    public void pushEvent(SystemEvent event) {
        eventQueue.add(event);
    }


    public void addDefaultCallbacks(CallbackKeeper guiCallbackKeeper) {
        //@formatter:off
        guiCallbackKeeper.getChainCharCallback()            .add(createDefaultGlfwCharCallbackI());
        guiCallbackKeeper.getChainDropCallback()            .add(createDefaultGlfwDropCallbackI());
        guiCallbackKeeper.getChainKeyCallback()             .add(createDefaultGlfwKeyCallbackI());
        guiCallbackKeeper.getChainScrollCallback()          .add(createDefaultGlfwScrollCallbackI());
        guiCallbackKeeper.getChainCharModsCallback()        .add(createDefaultGlfwCharModsCallbackI());
        guiCallbackKeeper.getChainCursorEnterCallback()     .add(createDefaultGlfwCursorEnterCallbackI());
        guiCallbackKeeper.getChainFramebufferSizeCallback() .add(createDefaultGlfwFramebufferSizeCallbackI());
        guiCallbackKeeper.getChainMouseButtonCallback()     .add(createDefaultGlfwMouseButtonCallbackI());
        guiCallbackKeeper.getChainCursorPosCallback()       .add(createDefaultGlfwCursorPosCallbackI());
        guiCallbackKeeper.getChainWindowCloseCallback()     .add(createDefaultGlfwWindowCloseCallbackI());
        guiCallbackKeeper.getChainWindowFocusCallback()     .add(createDefaultGlfwWindowFocusCallbackI());
        guiCallbackKeeper.getChainWindowIconifyCallback()   .add(createDefaultGlfwWindowIconifyCallbackI());
        guiCallbackKeeper.getChainWindowPosCallback()       .add(createDefaultGlfwWindowPosCallbackI());
        guiCallbackKeeper.getChainWindowRefreshCallback()   .add(createDefaultGlfwWindowRefreshCallbackI());
        guiCallbackKeeper.getChainWindowSizeCallback()      .add(createDefaultGlfwWindowSizeCallbackI());
        //@formatter:on
    }

    public GLFWWindowSizeCallbackI createDefaultGlfwWindowSizeCallbackI() {
        return (window, width, height) -> eventQueue.add(new SystemWindowSizeEvent(window, width, height));
    }

    public GLFWWindowRefreshCallbackI createDefaultGlfwWindowRefreshCallbackI() {
        return window -> eventQueue.add(new SystemWindowRefreshEvent(window));
    }

    public GLFWWindowPosCallbackI createDefaultGlfwWindowPosCallbackI() {
        return (window, xpos, ypos) -> eventQueue.add(new SystemWindowPosEvent(window, xpos, ypos));
    }

    public GLFWWindowIconifyCallbackI createDefaultGlfwWindowIconifyCallbackI() {
        return (window, iconified) -> eventQueue.add(new SystemWindowIconifyEvent(window, iconified));
    }

    public GLFWWindowFocusCallbackI createDefaultGlfwWindowFocusCallbackI() {
        return (window, focused) -> eventQueue.add(new SystemWindowFocusEvent(window, focused));
    }

    public GLFWWindowCloseCallbackI createDefaultGlfwWindowCloseCallbackI() {
        return (window) -> eventQueue.add(new SystemWindowCloseEvent(window));
    }

    public GLFWCursorPosCallbackI createDefaultGlfwCursorPosCallbackI() {
        return (window, xpos, ypos) -> eventQueue.add(new SystemCursorPosEvent(window, xpos, ypos));
    }

    public GLFWMouseButtonCallbackI createDefaultGlfwMouseButtonCallbackI() {
        return (window, button, action, mods) -> eventQueue.add(new SystemMouseClickEvent(window, button, action, mods));
    }

    public GLFWFramebufferSizeCallbackI createDefaultGlfwFramebufferSizeCallbackI() {
        return (window, width, height) -> eventQueue.add(new SystemFramebufferSizeEvent(window, width, height));
    }

    public GLFWCursorEnterCallbackI createDefaultGlfwCursorEnterCallbackI() {
        return (window, entered) -> eventQueue.add(new SystemCursorEnterEvent(window, entered));
    }

    public GLFWCharModsCallbackI createDefaultGlfwCharModsCallbackI() {
        return (window, codepoint, mods) -> eventQueue.add(new SystemCharModsEvent(window, codepoint, mods));
    }

    public GLFWScrollCallbackI createDefaultGlfwScrollCallbackI() {
        return (window, xoffset, yoffset) -> eventQueue.add(new SystemScrollEvent(window, xoffset, yoffset));
    }

    public GLFWKeyCallbackI createDefaultGlfwKeyCallbackI() {
        return (window, key, scancode, action, mods) -> eventQueue.add(new SystemKeyEvent(window, key, scancode, action, mods));
    }

    public GLFWDropCallbackI createDefaultGlfwDropCallbackI() {
        return (window, count, names) -> eventQueue.add(new SystemDropEvent(window, count, names));
    }

    public GLFWCharCallbackI createDefaultGlfwCharCallbackI() {
        return (window, codepoint) -> eventQueue.add(new SystemCharEvent(window, codepoint));
    }
}
