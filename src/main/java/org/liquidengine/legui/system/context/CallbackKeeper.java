package org.liquidengine.legui.system.context;

import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCharModsCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetDropCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;

import org.liquidengine.cbchain.IChainCharCallback;
import org.liquidengine.cbchain.IChainCharModsCallback;
import org.liquidengine.cbchain.IChainCursorEnterCallback;
import org.liquidengine.cbchain.IChainCursorPosCallback;
import org.liquidengine.cbchain.IChainDropCallback;
import org.liquidengine.cbchain.IChainFramebufferSizeCallback;
import org.liquidengine.cbchain.IChainKeyCallback;
import org.liquidengine.cbchain.IChainMouseButtonCallback;
import org.liquidengine.cbchain.IChainScrollCallback;
import org.liquidengine.cbchain.IChainWindowCloseCallback;
import org.liquidengine.cbchain.IChainWindowFocusCallback;
import org.liquidengine.cbchain.IChainWindowIconifyCallback;
import org.liquidengine.cbchain.IChainWindowPosCallback;
import org.liquidengine.cbchain.IChainWindowRefreshCallback;
import org.liquidengine.cbchain.IChainWindowSizeCallback;

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
