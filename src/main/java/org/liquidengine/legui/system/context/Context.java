package org.liquidengine.legui.system.context;

import static org.lwjgl.glfw.GLFW.GLFW_ICONIFIED;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public class Context {

    private final long glfwWindow;

    private Vector2f windowPosition;
    private Vector2i windowSize;
    private Vector2i framebufferSize;
    private transient float pixelRatio;
    private Component mouseTargetGui;
    private Component focusedGui;

    private boolean debugEnabled;

    private boolean iconified;

    private Map<String, Object> contextData = new ConcurrentHashMap<>();

    /**
     * Instantiates a new Context.
     *
     * @param glfwWindow the glfw window
     */
    public Context(long glfwWindow) {
        this.glfwWindow = glfwWindow;
    }

    /**
     * Is debug enabled boolean.
     *
     * @return the boolean
     */
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    /**
     * Sets debug enabled.
     *
     * @param debugEnabled the debug enabled
     */
    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    /**
     * Gets context data.
     *
     * @return the context data
     */
    public Map<String, Object> getContextData() {
        return contextData;
    }

    /**
     * Update glfw window.
     */
    public void updateGlfwWindow() {
        int[] windowWidth = {0},
            windowHeight = {0};
        int[] frameBufferWidth = {0},
            frameBufferHeight = {0};
        int[] xpos = {0},
            ypos = {0};
        glfwGetWindowSize(glfwWindow, windowWidth, windowHeight);
        glfwGetFramebufferSize(glfwWindow, frameBufferWidth, frameBufferHeight);
        glfwGetWindowPos(glfwWindow, xpos, ypos);

        update(windowWidth[0], windowHeight[0],
               frameBufferWidth[0], frameBufferHeight[0],
               xpos[0], ypos[0],
               glfwGetWindowAttrib(glfwWindow, GLFW_ICONIFIED) == GLFW_TRUE
              );
    }

    /**
     * Update.
     *
     * @param targetWidth the target width
     * @param targetHeight the target height
     * @param framebufferWidth the framebuffer width
     * @param framebufferHeight the framebuffer height
     * @param targetPosX the target pos x
     * @param targetPosY the target pos y
     * @param iconified the iconified
     */
    public void update(int targetWidth, int targetHeight, int framebufferWidth, int framebufferHeight,
                       int targetPosX, int targetPosY, boolean iconified) {
        setWindowSize(new Vector2i(targetWidth, targetHeight));
        setFramebufferSize(new Vector2i(framebufferWidth, framebufferHeight));
        setPixelRatio((float) framebufferWidth / (float) targetWidth);
        setWindowPosition(new Vector2f(targetPosX, targetPosY));
        setIconified(iconified);
    }

    /**
     * Gets pixel ratio.
     *
     * @return the pixel ratio
     */
    public float getPixelRatio() {
        return pixelRatio;
    }

    /**
     * Sets pixel ratio.
     *
     * @param pixelRatio the pixel ratio
     */
    public void setPixelRatio(float pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    /**
     * Gets glfw window.
     *
     * @return the glfw window
     */
    public long getGlfwWindow() {
        return glfwWindow;
    }

    /**
     * Gets window position.
     *
     * @return the window position
     */
    public Vector2f getWindowPosition() {
        return windowPosition;
    }

    /**
     * Sets window position.
     *
     * @param windowPosition the window position
     */
    public void setWindowPosition(Vector2f windowPosition) {
        this.windowPosition = windowPosition;
    }

    /**
     * Gets window size.
     *
     * @return the window size
     */
    public Vector2i getWindowSize() {
        return windowSize;
    }

    /**
     * Sets window size.
     *
     * @param windowSize the window size
     */
    public void setWindowSize(Vector2i windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * Gets framebuffer size.
     *
     * @return the framebuffer size
     */
    public Vector2i getFramebufferSize() {
        return framebufferSize;
    }

    /**
     * Sets framebuffer size.
     *
     * @param framebufferSize the framebuffer size
     */
    public void setFramebufferSize(Vector2i framebufferSize) {
        this.framebufferSize = framebufferSize;
    }

    /**
     * Gets focused gui.
     *
     * @return the focused gui
     */
    public Component getFocusedGui() {
        return focusedGui;
    }

    /**
     * Sets focused gui.
     *
     * @param focusedGui the focused gui
     */
    public void setFocusedGui(Component focusedGui) {
        this.focusedGui = focusedGui;
    }

    /**
     * Returns current mouse target component.
     *
     * @return current mouse target component.
     */
    public Component getMouseTargetGui() {
        return mouseTargetGui;
    }

    /**
     * Used to update current mouse target component.
     *
     * @param mouseTargetGui new mouse target component.
     */
    public void setMouseTargetGui(Component mouseTargetGui) {
        this.mouseTargetGui = mouseTargetGui;
    }

    /**
     * Returns window iconified state.
     *
     * @return window iconified state.
     */
    public boolean isIconified() {
        return iconified;
    }

    /**
     * Used to update state of window (in case of window iconified).
     *
     * @param iconified window state.
     */
    public void setIconified(boolean iconified) {
        this.iconified = iconified;
    }
}
