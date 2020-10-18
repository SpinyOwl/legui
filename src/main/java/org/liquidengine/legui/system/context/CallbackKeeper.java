package org.liquidengine.legui.system.context;

import org.liquidengine.cbchain.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Interface determines that class which implement it should provide chain callbacks.
 */
public interface CallbackKeeper {

    /**
     * Used to bind callbacks to OpenGL window. This method could be called only from main thread (Main OpenGL thread).
     *
     * @param window window to bind.
     * @param keeper callback keeper with callbacks.
     */
    static void registerCallbacks(long window, CallbackKeeper keeper) {
        glfwSetCharCallback(window, keeper.getChainCharCallback());
        glfwSetDropCallback(window, keeper.getChainDropCallback());
        glfwSetKeyCallback(window, keeper.getChainKeyCallback());
        glfwSetScrollCallback(window, keeper.getChainScrollCallback());
        glfwSetCharModsCallback(window, keeper.getChainCharModsCallback());
        glfwSetCursorEnterCallback(window, keeper.getChainCursorEnterCallback());
        glfwSetFramebufferSizeCallback(window, keeper.getChainFramebufferSizeCallback());
        glfwSetMouseButtonCallback(window, keeper.getChainMouseButtonCallback());
        glfwSetCursorPosCallback(window, keeper.getChainCursorPosCallback());
        glfwSetWindowCloseCallback(window, keeper.getChainWindowCloseCallback());
        glfwSetWindowFocusCallback(window, keeper.getChainWindowFocusCallback());
        glfwSetWindowIconifyCallback(window, keeper.getChainWindowIconifyCallback());
        glfwSetWindowPosCallback(window, keeper.getChainWindowPosCallback());
        glfwSetWindowRefreshCallback(window, keeper.getChainWindowRefreshCallback());
        glfwSetWindowSizeCallback(window, keeper.getChainWindowSizeCallback());
    }

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWCharCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainCharCallback getChainCharCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWDropCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainDropCallback getChainDropCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWKeyCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainKeyCallback getChainKeyCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWScrollCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainScrollCallback getChainScrollCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWCharModsCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainCharModsCallback getChainCharModsCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWCursorEnterCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainCursorEnterCallback getChainCursorEnterCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWFramebufferSizeCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainFramebufferSizeCallback getChainFramebufferSizeCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWMouseButtonCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainMouseButtonCallback getChainMouseButtonCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWCursorPosCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainCursorPosCallback getChainCursorPosCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWWindowCloseCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainWindowCloseCallback getChainWindowCloseCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWWindowFocusCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainWindowFocusCallback getChainWindowFocusCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWWindowIconifyCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainWindowIconifyCallback getChainWindowIconifyCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWWindowPosCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainWindowPosCallback getChainWindowPosCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWWindowRefreshCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainWindowRefreshCallback getChainWindowRefreshCallback();

    /**
     * Returns chain callback used to keep {@link org.lwjgl.glfw.GLFWWindowSizeCallbackI} instances.
     *
     * @return chain char callback.
     */
    IChainWindowSizeCallback getChainWindowSizeCallback();
}
