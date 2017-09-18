package org.liquidengine.legui.system.handler.processor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCharEvent;
import org.liquidengine.legui.system.event.SystemCharModsEvent;
import org.liquidengine.legui.system.event.SystemCursorEnterEvent;
import org.liquidengine.legui.system.event.SystemCursorPosEvent;
import org.liquidengine.legui.system.event.SystemDropEvent;
import org.liquidengine.legui.system.event.SystemEvent;
import org.liquidengine.legui.system.event.SystemFramebufferSizeEvent;
import org.liquidengine.legui.system.event.SystemKeyEvent;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.liquidengine.legui.system.event.SystemScrollEvent;
import org.liquidengine.legui.system.event.SystemWindowCloseEvent;
import org.liquidengine.legui.system.event.SystemWindowFocusEvent;
import org.liquidengine.legui.system.event.SystemWindowIconifyEvent;
import org.liquidengine.legui.system.event.SystemWindowPosEvent;
import org.liquidengine.legui.system.event.SystemWindowRefreshEvent;
import org.liquidengine.legui.system.event.SystemWindowSizeEvent;
import org.liquidengine.legui.system.handler.SystemEventHandler;
import org.liquidengine.legui.system.handler.SystemEventHandlerProvider;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

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
