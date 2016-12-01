package org.liquidengine.legui.context;

import org.liquidengine.cbchain.*;
import org.liquidengine.cbchain.impl.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiCallbackKeeper {

    private IChainCharCallback chainCharCallback = new ChainCharCallback();
    private IChainDropCallback chainDropCallback = new ChainDropCallback();
    private IChainKeyCallback chainKeyCallback = new ChainKeyCallback();
    private IChainScrollCallback chainScrollCallback = new ChainScrollCallback();
    private IChainCharModsCallback chainCharModsCallback = new ChainCharModsCallback();
    private IChainCursorEnterCallback chainCursorEnterCallback = new ChainCursorEnterCallback();
    private IChainFramebufferSizeCallback chainFramebufferSizeCallback = new ChainFramebufferSizeCallback();
    private IChainMouseButtonCallback chainMouseButtonCallback = new ChainMouseButtonCallback();
    private IChainCursorPosCallback chainCursorPosCallback = new ChainCursorPosCallback();
    private IChainWindowCloseCallback chainWindowCloseCallback = new ChainWindowCloseCallback();
    private IChainWindowFocusCallback chainWindowFocusCallback = new ChainWindowFocusCallback();
    private IChainWindowIconifyCallback chainWindowIconifyCallback = new ChainWindowIconifyCallback();
    private IChainWindowPosCallback chainWindowPosCallback = new ChainWindowPosCallback();
    private IChainWindowRefreshCallback chainWindowRefreshCallback = new ChainWindowRefreshCallback();
    private IChainWindowSizeCallback chainWindowSizeCallback = new ChainWindowSizeCallback();

    public void registerCallbacks(long window) {
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

    public IChainCharCallback getChainCharCallback() {
        return chainCharCallback;
    }

    public void setChainCharCallback(IChainCharCallback chainCharCallback) {
        this.chainCharCallback = chainCharCallback;
    }

    public IChainDropCallback getChainDropCallback() {
        return chainDropCallback;
    }

    public void setChainDropCallback(IChainDropCallback chainDropCallback) {
        this.chainDropCallback = chainDropCallback;
    }

    public IChainKeyCallback getChainKeyCallback() {
        return chainKeyCallback;
    }

    public void setChainKeyCallback(IChainKeyCallback chainKeyCallback) {
        this.chainKeyCallback = chainKeyCallback;
    }

    public IChainScrollCallback getChainScrollCallback() {
        return chainScrollCallback;
    }

    public void setChainScrollCallback(IChainScrollCallback chainScrollCallback) {
        this.chainScrollCallback = chainScrollCallback;
    }

    public IChainCharModsCallback getChainCharModsCallback() {
        return chainCharModsCallback;
    }

    public void setChainCharModsCallback(IChainCharModsCallback chainCharModsCallback) {
        this.chainCharModsCallback = chainCharModsCallback;
    }

    public IChainCursorEnterCallback getChainCursorEnterCallback() {
        return chainCursorEnterCallback;
    }

    public void setChainCursorEnterCallback(IChainCursorEnterCallback chainCursorEnterCallback) {
        this.chainCursorEnterCallback = chainCursorEnterCallback;
    }

    public IChainFramebufferSizeCallback getChainFramebufferSizeCallback() {
        return chainFramebufferSizeCallback;
    }

    public void setChainFramebufferSizeCallback(IChainFramebufferSizeCallback chainFramebufferSizeCallback) {
        this.chainFramebufferSizeCallback = chainFramebufferSizeCallback;
    }

    public IChainMouseButtonCallback getChainMouseButtonCallback() {
        return chainMouseButtonCallback;
    }

    public void setChainMouseButtonCallback(IChainMouseButtonCallback chainMouseButtonCallback) {
        this.chainMouseButtonCallback = chainMouseButtonCallback;
    }

    public IChainCursorPosCallback getChainCursorPosCallback() {
        return chainCursorPosCallback;
    }

    public void setChainCursorPosCallback(IChainCursorPosCallback chainCursorPosCallback) {
        this.chainCursorPosCallback = chainCursorPosCallback;
    }

    public IChainWindowCloseCallback getChainWindowCloseCallback() {
        return chainWindowCloseCallback;
    }

    public void setChainWindowCloseCallback(IChainWindowCloseCallback chainWindowCloseCallback) {
        this.chainWindowCloseCallback = chainWindowCloseCallback;
    }

    public IChainWindowFocusCallback getChainWindowFocusCallback() {
        return chainWindowFocusCallback;
    }

    public void setChainWindowFocusCallback(IChainWindowFocusCallback chainWindowFocusCallback) {
        this.chainWindowFocusCallback = chainWindowFocusCallback;
    }

    public IChainWindowIconifyCallback getChainWindowIconifyCallback() {
        return chainWindowIconifyCallback;
    }

    public void setChainWindowIconifyCallback(IChainWindowIconifyCallback chainWindowIconifyCallback) {
        this.chainWindowIconifyCallback = chainWindowIconifyCallback;
    }

    public IChainWindowPosCallback getChainWindowPosCallback() {
        return chainWindowPosCallback;
    }

    public void setChainWindowPosCallback(IChainWindowPosCallback chainWindowPosCallback) {
        this.chainWindowPosCallback = chainWindowPosCallback;
    }

    public IChainWindowRefreshCallback getChainWindowRefreshCallback() {
        return chainWindowRefreshCallback;
    }

    public void setChainWindowRefreshCallback(IChainWindowRefreshCallback chainWindowRefreshCallback) {
        this.chainWindowRefreshCallback = chainWindowRefreshCallback;
    }

    public IChainWindowSizeCallback getChainWindowSizeCallback() {
        return chainWindowSizeCallback;
    }

    public void setChainWindowSizeCallback(IChainWindowSizeCallback chainWindowSizeCallback) {
        this.chainWindowSizeCallback = chainWindowSizeCallback;
    }
}
