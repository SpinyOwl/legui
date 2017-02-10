package org.liquidengine.legui.system.context;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.listener.EventProcessor;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class Context {

    private final long  glfwWindow;
    private final Frame frame;

    private           Vector2f   windowPosition;
    private           Vector2i   windowSize;
    private           Vector2f   framebufferSize;
    private transient float      pixelRatio;
    private           Controller mouseTargetGui;
    private           Controller focusedGui;

    private boolean debugEnabled;

    private boolean iconified;

    private Map<String, Object> contextData = new ConcurrentHashMap<>();
    private EventProcessor eventProcessor;

    public Context(long glfwWindow, Frame frame, EventProcessor eventProcessor) {
        this.glfwWindow = glfwWindow;
        this.frame = frame;
        this.eventProcessor = eventProcessor;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public Map<String, Object> getContextData() {
        return contextData;
    }

    public void updateGlfwWindow() {
        int[] windowWidth = {0}, windowHeight = {0};
        glfwGetWindowSize(glfwWindow, windowWidth, windowHeight);
        int[] frameBufferWidth = {0}, frameBufferHeight = {0};
        glfwGetFramebufferSize(glfwWindow, frameBufferWidth, frameBufferHeight);
        int[] xpos = {0}, ypos = {0};
        glfwGetWindowPos(glfwWindow, xpos, ypos);
        double[] mx = {0}, my = {0};
        glfwGetCursorPos(glfwWindow, mx, my);

        update(windowWidth[0], windowHeight[0],
                frameBufferWidth[0], frameBufferHeight[0],
                xpos[0], ypos[0],
                mx[0], my[0],
                glfwGetWindowAttrib(glfwWindow, GLFW.GLFW_ICONIFIED) == GLFW_TRUE
        );
    }

    public void update(int targetWidth, int targetHeight, int framebufferWidth, int framebufferHeight,
                       int targetPosX, int targetPosY, double mousePosX, double mousePosY, boolean iconified) {
        setWindowSize(new Vector2i(targetWidth, targetHeight));
        setFramebufferSize(new Vector2f(framebufferWidth, framebufferHeight));
        setPixelRatio((float) framebufferWidth / (float) targetWidth);
        setWindowPosition(new Vector2f(targetPosX, targetPosY));
        setIconified(iconified);
    }

    public float getPixelRatio() {
        return pixelRatio;
    }

    public void setPixelRatio(float pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    public Frame getFrame() {
        return frame;
    }

    public long getGlfwWindow() {
        return glfwWindow;
    }

    public Vector2f getWindowPosition() {
        return windowPosition;
    }

    public void setWindowPosition(Vector2f windowPosition) {
        this.windowPosition = windowPosition;
    }

    public Vector2i getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Vector2i windowSize) {
        this.windowSize = windowSize;
    }

    public Vector2f getFramebufferSize() {
        return framebufferSize;
    }

    public void setFramebufferSize(Vector2f framebufferSize) {
        this.framebufferSize = framebufferSize;
    }

    public Controller getFocusedGui() {
        return focusedGui;
    }

    public void setFocusedGui(Controller focusedGui) {
        this.focusedGui = focusedGui;
    }

    public Controller getMouseTargetGui() {
        return mouseTargetGui;
    }

    public void setMouseTargetGui(Controller mouseTargetGui) {
        this.mouseTargetGui = mouseTargetGui;
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    public void setEventProcessor(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public boolean isIconified() {
        return iconified;
    }

    public void setIconified(boolean iconified) {
        this.iconified = iconified;
    }
}
