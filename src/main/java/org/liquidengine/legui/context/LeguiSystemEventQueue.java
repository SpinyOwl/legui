package org.liquidengine.legui.context;

import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.event.system.*;
import org.lwjgl.glfw.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiSystemEventQueue {

    private Queue<SystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public LeguiSystemEventQueue(ILeguiCallbackKeeper guiCallbackKeeper) {
        guiCallbackKeeper.getChainCharCallback().add(createCharCallback());
        guiCallbackKeeper.getChainDropCallback().add(createDropCallback());
        guiCallbackKeeper.getChainKeyCallback().add(createKeyCallback());
        guiCallbackKeeper.getChainScrollCallback().add(createScrollCallback());
        guiCallbackKeeper.getChainCharModsCallback().add(createCharModsCallback());
        guiCallbackKeeper.getChainCursorEnterCallback().add(createCursorEnterCallback());
        guiCallbackKeeper.getChainFramebufferSizeCallback().add(createFramebufferSizeCallback());
        guiCallbackKeeper.getChainMouseButtonCallback().add(createMouseButtonCallback());
        guiCallbackKeeper.getChainCursorPosCallback().add(createCursorPosCallback());
        guiCallbackKeeper.getChainWindowCloseCallback().add(createWindowCloseCallback());
        guiCallbackKeeper.getChainWindowFocusCallback().add(createWindowFocusCallback());
        guiCallbackKeeper.getChainWindowIconifyCallback().add(createWindowIconifyCallback());
        guiCallbackKeeper.getChainWindowPosCallback().add(createWindowPosCallback());
        guiCallbackKeeper.getChainWindowRefreshCallback().add(createWindowRefreshCallback());
        guiCallbackKeeper.getChainWindowSizeCallback().add(createWindowSizeCallback());
    }

    private GLFWFramebufferSizeCallbackI createFramebufferSizeCallback() {
        return (window, width, height) -> eventQueue.add(new SystemFramebufferSizeEvent(window, width, height));
    }

    private GLFWMouseButtonCallbackI createMouseButtonCallback() {
        return (window, button, action, mods) -> eventQueue.add(new SystemMouseClickEvent(window, button, action, mods));
    }

    private GLFWCursorPosCallbackI createCursorPosCallback() {
        return (window, xpos, ypos) -> eventQueue.add(new SystemCursorPosEvent(window, xpos, ypos));
    }

    private GLFWWindowCloseCallbackI createWindowCloseCallback() {
        return window -> eventQueue.add(new SystemWindowCloseEvent(window));
    }

    private GLFWWindowFocusCallbackI createWindowFocusCallback() {
        return (window, focused) -> eventQueue.add(new SystemWindowFocusEvent(window, focused));
    }

    private GLFWWindowIconifyCallbackI createWindowIconifyCallback() {
        return (window, iconified) -> eventQueue.add(new SystemWindowIconifyEvent(window, iconified));
    }

    private GLFWWindowPosCallbackI createWindowPosCallback() {
        return (window, xpos, ypos) -> eventQueue.add(new SystemWindowPosEvent(window, xpos, ypos));
    }

    private GLFWWindowRefreshCallbackI createWindowRefreshCallback() {
        return window -> eventQueue.add(new SystemWindowRefreshEvent(window));
    }

    private GLFWWindowSizeCallbackI createWindowSizeCallback() {
        return (window, width, height) -> eventQueue.add(new SystemWindowSizeEvent(window, width, height));
    }


    private GLFWCursorEnterCallbackI createCursorEnterCallback() {
        return (window, entered) -> eventQueue.add(new SystemCursorEnterEvent(window, entered));
    }

    private GLFWCharModsCallbackI createCharModsCallback() {
        return (window, codepoint, mods) -> eventQueue.add(new SystemCharModsEvent(window, codepoint, mods));
    }

    private GLFWScrollCallbackI createScrollCallback() {
        return (window, xoffset, yoffset) -> eventQueue.add(new SystemScrollEvent(window, xoffset, yoffset));
    }

    private GLFWKeyCallbackI createKeyCallback() {
        return (window, key, scancode, action, mods) -> eventQueue.add(new SystemKeyEvent(window, key, scancode, action, mods));
    }

    private GLFWDropCallbackI createDropCallback() {
        return (window, count, names) -> eventQueue.add(new SystemDropEvent(window, count, names));
    }

    private GLFWCharCallbackI createCharCallback() {
        return (window, codepoint) -> eventQueue.add(new SystemCharEvent(window, codepoint));
    }

    public int size() {
        return eventQueue.size();
    }

    public SystemEvent poll() {
        return eventQueue.poll();
    }

    public boolean isEmpty() {
        return eventQueue.isEmpty();
    }

    public void clear() {
        eventQueue.clear();
    }

}
