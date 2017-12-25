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
 * Created by ShchAlexander on 1/25/2017.
 */
public class SystemEventProcessor {

    private Queue<SystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    /**
     * Process events.
     *
     * @param frame the frame
     * @param context the context
     */
    public void processEvents(Frame frame, Context context) {
        for (SystemEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            SystemEventHandler processor = SystemEventHandlerProvider.getInstance().getProcessor(event.getClass());
            if (processor != null) {
                processor.handle(event, frame, context);
            }
        }
    }

    /**
     * Push event.
     *
     * @param event the event
     */
    public void pushEvent(SystemEvent event) {
        eventQueue.add(event);
    }


    /**
     * Add default callbacks to callback keeper.
     *
     * @param guiCallbackKeeper the gui callback keeper
     */
    public void addDefaultCallbacks(CallbackKeeper guiCallbackKeeper) {
        //@formatter:off
        guiCallbackKeeper.getChainCharCallback().add(createDefaultGlfwCharCallbackI());
        guiCallbackKeeper.getChainDropCallback().add(createDefaultGlfwDropCallbackI());
        guiCallbackKeeper.getChainKeyCallback().add(createDefaultGlfwKeyCallbackI());
        guiCallbackKeeper.getChainScrollCallback().add(createDefaultGlfwScrollCallbackI());
        guiCallbackKeeper.getChainCharModsCallback().add(createDefaultGlfwCharModsCallbackI());
        guiCallbackKeeper.getChainCursorEnterCallback().add(createDefaultGlfwCursorEnterCallbackI());
        guiCallbackKeeper.getChainFramebufferSizeCallback().add(createDefaultGlfwFramebufferSizeCallbackI());
        guiCallbackKeeper.getChainMouseButtonCallback().add(createDefaultGlfwMouseButtonCallbackI());
        guiCallbackKeeper.getChainCursorPosCallback().add(createDefaultGlfwCursorPosCallbackI());
        guiCallbackKeeper.getChainWindowCloseCallback().add(createDefaultGlfwWindowCloseCallbackI());
        guiCallbackKeeper.getChainWindowFocusCallback().add(createDefaultGlfwWindowFocusCallbackI());
        guiCallbackKeeper.getChainWindowIconifyCallback().add(createDefaultGlfwWindowIconifyCallbackI());
        guiCallbackKeeper.getChainWindowPosCallback().add(createDefaultGlfwWindowPosCallbackI());
        guiCallbackKeeper.getChainWindowRefreshCallback().add(createDefaultGlfwWindowRefreshCallbackI());
        guiCallbackKeeper.getChainWindowSizeCallback().add(createDefaultGlfwWindowSizeCallbackI());
        //@formatter:on
    }

    /**
     * Creates default GLFWWindowSizeCallback.
     *
     * @return the GLFWWindowSizeCallback.
     */
    public GLFWWindowSizeCallbackI createDefaultGlfwWindowSizeCallbackI() {
        return (window, width, height) -> eventQueue.add(new SystemWindowSizeEvent(window, width, height));
    }

    /**
     * Creates default GLFWWindowRefreshCallback.
     *
     * @return the GLFWWindowRefreshCallback.
     */
    public GLFWWindowRefreshCallbackI createDefaultGlfwWindowRefreshCallbackI() {
        return window -> eventQueue.add(new SystemWindowRefreshEvent(window));
    }

    /**
     * Creates default GLFWWindowPosCallback.
     *
     * @return the GLFWWindowPosCallback.
     */
    public GLFWWindowPosCallbackI createDefaultGlfwWindowPosCallbackI() {
        return (window, xpos, ypos) -> eventQueue.add(new SystemWindowPosEvent(window, xpos, ypos));
    }

    /**
     * Creates default GLFWWindowIconifyCallback.
     *
     * @return the GLFWWindowIconifyCallback.
     */
    public GLFWWindowIconifyCallbackI createDefaultGlfwWindowIconifyCallbackI() {
        return (window, iconified) -> eventQueue.add(new SystemWindowIconifyEvent(window, iconified));
    }

    /**
     * Creates default GLFWWindowFocusCallback.
     *
     * @return the GLFWWindowFocusCallback.
     */
    public GLFWWindowFocusCallbackI createDefaultGlfwWindowFocusCallbackI() {
        return (window, focused) -> eventQueue.add(new SystemWindowFocusEvent(window, focused));
    }

    /**
     * Creates default GLFWWindowCloseCallback.
     *
     * @return the GLFWWindowCloseCallback.
     */
    public GLFWWindowCloseCallbackI createDefaultGlfwWindowCloseCallbackI() {
        return (window) -> eventQueue.add(new SystemWindowCloseEvent(window));
    }

    /**
     * Creates default GLFWCursorPosCallback.
     *
     * @return the GLFWCursorPosCallback.
     */
    public GLFWCursorPosCallbackI createDefaultGlfwCursorPosCallbackI() {
        return (window, xpos, ypos) -> eventQueue.add(new SystemCursorPosEvent(window, xpos, ypos));
    }

    /**
     * Creates default GLFWMouseButtonCallback.
     *
     * @return the GLFWMouseButtonCallback.
     */
    public GLFWMouseButtonCallbackI createDefaultGlfwMouseButtonCallbackI() {
        return (window, button, action, mods) -> eventQueue.add(new SystemMouseClickEvent(window, button, action, mods));
    }

    /**
     * Creates default GLFWFramebufferSizeCallback.
     *
     * @return the GLFWFramebufferSizeCallback.
     */
    public GLFWFramebufferSizeCallbackI createDefaultGlfwFramebufferSizeCallbackI() {
        return (window, width, height) -> eventQueue.add(new SystemFramebufferSizeEvent(window, width, height));
    }

    /**
     * Creates default GLFWCursorEnterCallback.
     *
     * @return the GLFWCursorEnterCallback.
     */
    public GLFWCursorEnterCallbackI createDefaultGlfwCursorEnterCallbackI() {
        return (window, entered) -> eventQueue.add(new SystemCursorEnterEvent(window, entered));
    }

    /**
     * Creates default GLFWCharModsCallback.
     *
     * @return the GLFWCharModsCallback.
     */
    public GLFWCharModsCallbackI createDefaultGlfwCharModsCallbackI() {
        return (window, codepoint, mods) -> eventQueue.add(new SystemCharModsEvent(window, codepoint, mods));
    }

    /**
     * Creates default GLFWScrollCallback.
     *
     * @return the GLFWScrollCallback.
     */
    public GLFWScrollCallbackI createDefaultGlfwScrollCallbackI() {
        return (window, xoffset, yoffset) -> eventQueue.add(new SystemScrollEvent(window, xoffset, yoffset));
    }

    /**
     * Creates default GLFWKeyCallback.
     *
     * @return the GLFWKeyCallback.
     */
    public GLFWKeyCallbackI createDefaultGlfwKeyCallbackI() {
        return (window, key, scancode, action, mods) -> eventQueue.add(new SystemKeyEvent(window, key, scancode, action, mods));
    }

    /**
     * Creates default GLFWDropCallback.
     *
     * @return the GLFWDropCallback.
     */
    public GLFWDropCallbackI createDefaultGlfwDropCallbackI() {
        return (window, count, names) -> eventQueue.add(new SystemDropEvent(window, count, names));
    }

    /**
     * Creates default GLFWCharCallback.
     *
     * @return the GLFWCharCallback.
     */
    public GLFWCharCallbackI createDefaultGlfwCharCallbackI() {
        return (window, codepoint) -> eventQueue.add(new SystemCharEvent(window, codepoint));
    }
}
