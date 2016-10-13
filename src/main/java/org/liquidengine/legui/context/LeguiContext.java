package org.liquidengine.legui.context;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.event.system.LeguiSystemEvent;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiContext {

    private long glfwWindow;

    private Vector2f windowMousePosition;
    private Vector2f windowPosition;
    private Vector2i windowSize;
    private Vector2f framebufferSize;
    private float pixelRatio;

    private Component mainGuiComponent;
    private Component mouseTargetGui;
    private Component focusedGui;

    private boolean debug = false;

    private boolean[] mouseButtonStates = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private Vector2f mousePosition;
    private Vector2f cursorPosition = new Vector2f();
    private Vector2f cursorPositionPrev = new Vector2f();
    private Vector2f[] mouseButtonPressPosition = new Vector2f[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private Map<String, Object> contextData = new ConcurrentHashMap<>();
    private LeguiEventProcessor leguiEventProcessor;


    public LeguiContext(long glfwWindow, Component mainGuiComponent) {
        this.glfwWindow = glfwWindow;
        this.mainGuiComponent = mainGuiComponent;
        initialize();
    }

    private void initialize() {

    }

    public void updateGlfwWindow() {
        int[] windowWidth = {0}, windowHeight = {0};
        GLFW.glfwGetWindowSize(glfwWindow, windowWidth, windowHeight);
        int[] frameBufferWidth = {0}, frameBufferHeight = {0};
        GLFW.glfwGetFramebufferSize(glfwWindow, frameBufferWidth, frameBufferHeight);
        int[] xpos = {0}, ypos = {0};
        GLFW.glfwGetWindowPos(glfwWindow, xpos, ypos);
        double[] mx = {0}, my = {0};
        GLFW.glfwGetCursorPos(glfwWindow, mx, my);

        update(windowWidth[0], windowHeight[0],
                frameBufferWidth[0], frameBufferHeight[0],
                xpos[0], ypos[0],
                mx[0], my[0]
        );
    }

    public void update(int targetWidth, int targetHeight, int framebufferWidth, int framebufferHeight,
                       int targetPosX, int targetPosY, double mousePosX, double mousePosY) {
        setWindowSize(new Vector2i(targetWidth, targetHeight));
        setFramebufferSize(new Vector2f(framebufferWidth, framebufferHeight));
        setPixelRatio((float) framebufferWidth / (float) targetWidth);
        setWindowPosition(new Vector2f(targetPosX, targetPosY));
        setMousePosition(new Vector2f((float) mousePosX, (float) mousePosY));
    }

    public float getPixelRatio() {
        return pixelRatio;
    }

    public void setPixelRatio(float pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    public Component getMainGuiComponent() {
        return mainGuiComponent;
    }

    public long getGlfwWindow() {
        return glfwWindow;
    }

    public void setGlfwWindow(long glfwWindow) {
        this.glfwWindow = glfwWindow;
    }

    public Vector2f getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Vector2f mousePosition) {
        this.mousePosition = mousePosition;
    }

    public Vector2f getWindowMousePosition() {
        return windowMousePosition;
    }

    public void setWindowMousePosition(Vector2f windowMousePosition) {
        this.windowMousePosition = windowMousePosition;
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

    public Component getFocusedGui() {
        return focusedGui;
    }

    public void setFocusedGui(Component focusedGui) {
        this.focusedGui = focusedGui;
    }

    public Vector2f getCursorPosition() {
        return new Vector2f(cursorPosition);
    }

    public void setCursorPosition(Vector2f cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    public Vector2f getCursorPositionPrev() {
        return new Vector2f(cursorPositionPrev);
    }

    public void setCursorPositionPrev(Vector2f cursorPositionPrev) {
        this.cursorPositionPrev = cursorPositionPrev;
    }

    public Vector2f[] getMouseButtonPressPosition() {
        return mouseButtonPressPosition;
    }

    public boolean[] getMouseButtonStates() {
        return mouseButtonStates;
    }

    public void setMouseButtonStates(boolean[] mouseButtonStates) {
        this.mouseButtonStates = mouseButtonStates;
    }

    void releaseFocus(Component mainGui, LeguiSystemEvent event) {
        boolean release = false;
        if (event instanceof SystemMouseClickEvent && ((SystemMouseClickEvent) event).action == GLFW.GLFW_RELEASE) {
            release = true;
        }

        if (release) {
            release(mainGui, focusedGui);
        }
    }

    private void release(Component gui, Component focused) {
        if (gui != focused) {
            gui.setFocused(false);
        }
        if (gui instanceof ComponentContainer) {
            ComponentContainer container = ((ComponentContainer) gui);
            List<Component> all = container.getComponents();
            for (Component element : all) {
                release(element, focused);
            }
        }
    }

    public Map<String, Object> getContextData() {
        return contextData;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Component getMouseTargetGui() {
        return mouseTargetGui;
    }

    public void setMouseTargetGui(Component mouseTargetGui) {
        this.mouseTargetGui = mouseTargetGui;
    }


    public void setLeguiEventProcessor(LeguiEventProcessor leguiEventProcessor) {
        this.leguiEventProcessor = leguiEventProcessor;
    }

    public LeguiEventProcessor getLeguiEventProcessor() {
        return leguiEventProcessor;
    }
}
