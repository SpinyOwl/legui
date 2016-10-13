package org.liquidengine.legui.processor.system;

import org.liquidengine.cbchain.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Class used to keep chain callbacks for gui library.
 * Created by Shcherbin Alexander on 6/23/2016.
 */
public class CallbackKeeper {

    private final ChainCharCallback chainCharCallback = new ChainCharCallback();
    private final ChainDropCallback chainDropCallback = new ChainDropCallback();
    private final ChainKeyCallback chainKeyCallback = new ChainKeyCallback();
    private final ChainScrollCallback chainScrollCallback = new ChainScrollCallback();
    private final ChainCharModsCallback chainCharModsCallback = new ChainCharModsCallback();
    private final ChainCursorEnterCallback chainCursorEnterCallback = new ChainCursorEnterCallback();
    private final ChainFramebufferSizeCallback chainFramebufferSizeCallback = new ChainFramebufferSizeCallback();
    private final ChainMouseButtonCallback chainMouseButtonCallback = new ChainMouseButtonCallback();
    private final ChainCursorPosCallback chainCursorPosCallback = new ChainCursorPosCallback();
    private final ChainWindowCloseCallback chainWindowCloseCallback = new ChainWindowCloseCallback();
    private final ChainWindowFocusCallback chainWindowFocusCallback = new ChainWindowFocusCallback();
    private final ChainWindowIconifyCallback chainWindowIconifyCallback = new ChainWindowIconifyCallback();
    private final ChainWindowPosCallback chainWindowPosCallback = new ChainWindowPosCallback();
    private final ChainWindowRefreshCallback chainWindowRefreshCallback = new ChainWindowRefreshCallback();
    private final ChainWindowSizeCallback chainWindowSizeCallback = new ChainWindowSizeCallback();

    public ChainCharCallback getChainCharCallback() {
        return chainCharCallback;
    }

    public ChainDropCallback getChainDropCallback() {
        return chainDropCallback;
    }

    public ChainKeyCallback getChainKeyCallback() {
        return chainKeyCallback;
    }

    public ChainScrollCallback getChainScrollCallback() {
        return chainScrollCallback;
    }

    public ChainCharModsCallback getChainCharModsCallback() {
        return chainCharModsCallback;
    }

    public ChainCursorEnterCallback getChainCursorEnterCallback() {
        return chainCursorEnterCallback;
    }

    public ChainFramebufferSizeCallback getChainFramebufferSizeCallback() {
        return chainFramebufferSizeCallback;
    }

    public ChainMouseButtonCallback getChainMouseButtonCallback() {
        return chainMouseButtonCallback;
    }

    public ChainCursorPosCallback getChainCursorPosCallback() {
        return chainCursorPosCallback;
    }

    public ChainWindowCloseCallback getChainWindowCloseCallback() {
        return chainWindowCloseCallback;
    }

    public ChainWindowFocusCallback getChainWindowFocusCallback() {
        return chainWindowFocusCallback;
    }

    public ChainWindowIconifyCallback getChainWindowIconifyCallback() {
        return chainWindowIconifyCallback;
    }

    public ChainWindowPosCallback getChainWindowPosCallback() {
        return chainWindowPosCallback;
    }

    public ChainWindowRefreshCallback getChainWindowRefreshCallback() {
        return chainWindowRefreshCallback;
    }

    public ChainWindowSizeCallback getChainWindowSizeCallback() {
        return chainWindowSizeCallback;
    }

    public CallbackKeeper(long window) {
        glfwSetCharCallback(window, chainCharCallback);
        glfwSetDropCallback(window, chainDropCallback);
        glfwSetKeyCallback(window, chainKeyCallback);
        glfwSetScrollCallback(window, chainScrollCallback);
        glfwSetCharModsCallback(window, chainCharModsCallback);
        glfwSetCursorEnterCallback(window, chainCursorEnterCallback);
        glfwSetFramebufferSizeCallback(window, chainFramebufferSizeCallback);
        glfwSetMouseButtonCallback(window, chainMouseButtonCallback);
        glfwSetCursorPosCallback(window, chainCursorPosCallback);
        glfwSetWindowCloseCallback(window, chainWindowCloseCallback);
        glfwSetWindowFocusCallback(window, chainWindowFocusCallback);
        glfwSetWindowIconifyCallback(window, chainWindowIconifyCallback);
        glfwSetWindowPosCallback(window, chainWindowPosCallback);
        glfwSetWindowRefreshCallback(window, chainWindowRefreshCallback);
        glfwSetWindowSizeCallback(window, chainWindowSizeCallback);
    }
}
