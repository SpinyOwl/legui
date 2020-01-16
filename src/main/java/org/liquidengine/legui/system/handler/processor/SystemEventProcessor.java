package org.liquidengine.legui.system.handler.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.*;
import org.lwjgl.glfw.*;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public interface SystemEventProcessor {
    /**
     * Used to process a bunch of events that already pushed to this event processor.
     *
     * @param frame   target frame for events.
     * @param context context.
     */
    void processEvents(Frame frame, Context context);

    /**
     * Push event.
     *
     * @param event the event
     */
    void pushEvent(SystemEvent event);

    /**
     * Returns true if there are events that should be processed.
     *
     * @return true if there are events that should be processed.
     */
    boolean hasEvents();

    /**
     * Add default callbacks to callback keeper.
     *
     * @param guiCallbackKeeper the gui callback keeper
     */
    static void addDefaultCallbacks(CallbackKeeper guiCallbackKeeper, SystemEventProcessor processor) {
        //@formatter:off
        guiCallbackKeeper.getChainCharCallback()            .add(createDefaultGlfwCharCallbackI            (processor));
        guiCallbackKeeper.getChainDropCallback()            .add(createDefaultGlfwDropCallbackI            (processor));
        guiCallbackKeeper.getChainKeyCallback()             .add(createDefaultGlfwKeyCallbackI             (processor));
        guiCallbackKeeper.getChainScrollCallback()          .add(createDefaultGlfwScrollCallbackI          (processor));
        guiCallbackKeeper.getChainCharModsCallback()        .add(createDefaultGlfwCharModsCallbackI        (processor));
        guiCallbackKeeper.getChainCursorEnterCallback()     .add(createDefaultGlfwCursorEnterCallbackI     (processor));
        guiCallbackKeeper.getChainFramebufferSizeCallback() .add(createDefaultGlfwFramebufferSizeCallbackI (processor));
        guiCallbackKeeper.getChainMouseButtonCallback()     .add(createDefaultGlfwMouseButtonCallbackI     (processor));
        guiCallbackKeeper.getChainCursorPosCallback()       .add(createDefaultGlfwCursorPosCallbackI       (processor));
        guiCallbackKeeper.getChainWindowCloseCallback()     .add(createDefaultGlfwWindowCloseCallbackI     (processor));
        guiCallbackKeeper.getChainWindowFocusCallback()     .add(createDefaultGlfwWindowFocusCallbackI     (processor));
        guiCallbackKeeper.getChainWindowIconifyCallback()   .add(createDefaultGlfwWindowIconifyCallbackI   (processor));
        guiCallbackKeeper.getChainWindowPosCallback()       .add(createDefaultGlfwWindowPosCallbackI       (processor));
        guiCallbackKeeper.getChainWindowRefreshCallback()   .add(createDefaultGlfwWindowRefreshCallbackI   (processor));
        guiCallbackKeeper.getChainWindowSizeCallback()      .add(createDefaultGlfwWindowSizeCallbackI      (processor));
        //@formatter:on
    }

    /**
     * Creates default GLFWWindowSizeCallback.
     *
     * @return the GLFWWindowSizeCallback.
     */
    static GLFWWindowSizeCallbackI createDefaultGlfwWindowSizeCallbackI(SystemEventProcessor processor) {
        return (window, width, height) -> processor.pushEvent(new SystemWindowSizeEvent(window, width, height));
    }

    /**
     * Creates default GLFWWindowRefreshCallback.
     *
     * @return the GLFWWindowRefreshCallback.
     */
    static GLFWWindowRefreshCallbackI createDefaultGlfwWindowRefreshCallbackI(SystemEventProcessor processor) {
        return window -> processor.pushEvent(new SystemWindowRefreshEvent(window));
    }

    /**
     * Creates default GLFWWindowPosCallback.
     *
     * @return the GLFWWindowPosCallback.
     */
    static GLFWWindowPosCallbackI createDefaultGlfwWindowPosCallbackI(SystemEventProcessor processor) {
        return (window, xpos, ypos) -> processor.pushEvent(new SystemWindowPosEvent(window, xpos, ypos));
    }

    /**
     * Creates default GLFWWindowIconifyCallback.
     *
     * @return the GLFWWindowIconifyCallback.
     */
    static GLFWWindowIconifyCallbackI createDefaultGlfwWindowIconifyCallbackI(SystemEventProcessor processor) {
        return (window, iconified) -> processor.pushEvent(new SystemWindowIconifyEvent(window, iconified));
    }

    /**
     * Creates default GLFWWindowFocusCallback.
     *
     * @return the GLFWWindowFocusCallback.
     */
    static GLFWWindowFocusCallbackI createDefaultGlfwWindowFocusCallbackI(SystemEventProcessor processor) {
        return (window, focused) -> processor.pushEvent(new SystemWindowFocusEvent(window, focused));
    }

    /**
     * Creates default GLFWWindowCloseCallback.
     *
     * @return the GLFWWindowCloseCallback.
     */
    static GLFWWindowCloseCallbackI createDefaultGlfwWindowCloseCallbackI(SystemEventProcessor processor) {
        return (window) -> processor.pushEvent(new SystemWindowCloseEvent(window));
    }

    /**
     * Creates default GLFWCursorPosCallback.
     *
     * @return the GLFWCursorPosCallback.
     */
    static GLFWCursorPosCallbackI createDefaultGlfwCursorPosCallbackI(SystemEventProcessor processor) {
        return (window, xpos, ypos) -> processor.pushEvent(new SystemCursorPosEvent(window, xpos, ypos));
    }

    /**
     * Creates default GLFWMouseButtonCallback.
     *
     * @return the GLFWMouseButtonCallback.
     */
    static GLFWMouseButtonCallbackI createDefaultGlfwMouseButtonCallbackI(SystemEventProcessor processor) {
        return (window, button, action, mods) -> processor.pushEvent(new SystemMouseClickEvent(window, button, action, mods));
    }

    /**
     * Creates default GLFWFramebufferSizeCallback.
     *
     * @return the GLFWFramebufferSizeCallback.
     */
    static GLFWFramebufferSizeCallbackI createDefaultGlfwFramebufferSizeCallbackI(SystemEventProcessor processor) {
        return (window, width, height) -> processor.pushEvent(new SystemFramebufferSizeEvent(window, width, height));
    }

    /**
     * Creates default GLFWCursorEnterCallback.
     *
     * @return the GLFWCursorEnterCallback.
     */
    static GLFWCursorEnterCallbackI createDefaultGlfwCursorEnterCallbackI(SystemEventProcessor processor) {
        return (window, entered) -> processor.pushEvent(new SystemCursorEnterEvent(window, entered));
    }

    /**
     * Creates default GLFWCharModsCallback.
     *
     * @return the GLFWCharModsCallback.
     */
    static GLFWCharModsCallbackI createDefaultGlfwCharModsCallbackI(SystemEventProcessor processor) {
        return (window, codepoint, mods) -> processor.pushEvent(new SystemCharModsEvent(window, codepoint, mods));
    }

    /**
     * Creates default GLFWScrollCallback.
     *
     * @return the GLFWScrollCallback.
     */
    static GLFWScrollCallbackI createDefaultGlfwScrollCallbackI(SystemEventProcessor processor) {
        return (window, xoffset, yoffset) -> processor.pushEvent(new SystemScrollEvent(window, xoffset, yoffset));
    }

    /**
     * Creates default GLFWKeyCallback.
     *
     * @return the GLFWKeyCallback.
     */
    static GLFWKeyCallbackI createDefaultGlfwKeyCallbackI(SystemEventProcessor processor) {
        return (window, key, scancode, action, mods) -> processor.pushEvent(new SystemKeyEvent(window, key, scancode, action, mods));
    }

    /**
     * Creates default GLFWDropCallback.
     *
     * @return the GLFWDropCallback.
     */
    static GLFWDropCallbackI createDefaultGlfwDropCallbackI(SystemEventProcessor processor) {
        return (window, count, names) -> processor.pushEvent(new SystemDropEvent(window, count, names));
    }

    /**
     * Creates default GLFWCharCallback.
     *
     * @return the GLFWCharCallback.
     */
    static GLFWCharCallbackI createDefaultGlfwCharCallbackI(SystemEventProcessor processor) {
        return (window, codepoint) -> processor.pushEvent(new SystemCharEvent(window, codepoint));
    }
}
