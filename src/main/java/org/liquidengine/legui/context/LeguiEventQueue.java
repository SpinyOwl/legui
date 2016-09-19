package org.liquidengine.legui.context;

import org.liquidengine.legui.event.system.*;
import org.lwjgl.glfw.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiEventQueue {

    private Queue<LeguiSystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public LeguiEventQueue(LeguiCallbackKeeper guiCallbackKeeper) {
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

    public GLFWFramebufferSizeCallbackI createFramebufferSizeCallback() {
        return (window, width, height) -> eventQueue.add(new FramebufferSizeEvent(window, width, height));
    }

    public GLFWMouseButtonCallbackI createMouseButtonCallback() {
        return (window, button, action, mods) -> eventQueue.add(new MouseClickEvent(window, button, action, mods));
    }

    public GLFWCursorPosCallbackI createCursorPosCallback() {
        return (window, xpos, ypos) -> eventQueue.add(new CursorPosEvent(window, xpos, ypos));
    }

    public GLFWWindowCloseCallbackI createWindowCloseCallback() {
        return window -> eventQueue.add(new WindowCloseEvent(window));
    }

    public GLFWWindowFocusCallbackI createWindowFocusCallback() {
        return (window, focused) -> eventQueue.add(new WindowFocusEvent(window, focused));
    }

    public GLFWWindowIconifyCallbackI createWindowIconifyCallback() {
        return (window, iconified) -> eventQueue.add(new WindowIconifyEvent(window, iconified));
    }

    public GLFWWindowPosCallbackI createWindowPosCallback() {
        return (window, xpos, ypos) -> eventQueue.add(new WindowPosEvent(window, xpos, ypos));
    }

    public GLFWWindowRefreshCallbackI createWindowRefreshCallback() {
        return window -> eventQueue.add(new WindowRefreshEvent(window));
    }

    public GLFWWindowSizeCallbackI createWindowSizeCallback() {
        return (window, width, height) -> eventQueue.add(new WindowSizeEvent(window, width, height));
    }


    public GLFWCursorEnterCallbackI createCursorEnterCallback() {
        return (window, entered) -> eventQueue.add(new CursorEnterEvent(window, entered));
    }

    public GLFWCharModsCallbackI createCharModsCallback() {
        return (window, codepoint, mods) -> eventQueue.add(new CharModsEvent(window, codepoint, mods));
    }

    public GLFWScrollCallbackI createScrollCallback() {
        return (window, xoffset, yoffset) -> eventQueue.add(new ScrollEvent(window, xoffset, yoffset));
    }

    public GLFWKeyCallbackI createKeyCallback() {
        return (window, key, scancode, action, mods) -> eventQueue.add(new KeyEvent(window, key, scancode, action, mods));
    }

    public GLFWDropCallbackI createDropCallback() {
        return (window, count, names) -> eventQueue.add(new DropEvent(window, count, names));
    }

    public GLFWCharCallbackI createCharCallback() {
        return (window, codepoint) -> eventQueue.add(new CharEvent(window, codepoint));
    }

    public int size() {
        return eventQueue.size();
    }

    public LeguiSystemEvent poll() {
        return eventQueue.poll();
    }

    public LeguiSystemEvent peek() {
        return eventQueue.peek();
    }

    public LeguiSystemEvent element() {
        return eventQueue.element();
    }

    public LeguiSystemEvent remove() {
        return eventQueue.remove();
    }

    public boolean isEmpty() {
        return eventQueue.isEmpty();
    }

    public void clear() {
        eventQueue.clear();
    }

}
