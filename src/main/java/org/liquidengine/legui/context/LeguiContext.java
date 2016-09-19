package org.liquidengine.legui.context;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.event.system.LeguiSystemEvent;
import org.liquidengine.legui.event.system.MouseClickEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiContext {

    private final Component exampleGui;
    private final Vector2f cursorPosition = new Vector2f();
    private final Vector2f cursorPositionPrev = new Vector2f();
    private final Vector2f[] mouseButtonPressPosition = new Vector2f[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private long windowPointer;
    private Vector2f windowSize;
    private Vector2f framebufferSize;
    private float pixelRatio;
    private Vector2f mousePosition;
    private Vector2f windowMousePosition;
    private Vector2f windowPosition;
    private Component focusedGui;
    private boolean[] mouseButtonStates = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private boolean transferToChild;

    public LeguiContext(long windowPointer, Component exampleGui) {
        this.windowPointer = windowPointer;
        this.exampleGui = exampleGui;
    }

    public void update() {
        int[] windowWidth = {0}, windowHeight = {0};
        GLFW.glfwGetWindowSize(windowPointer, windowWidth, windowHeight);
        int[] frameBufferWidth = {0}, frameBufferHeight = {0};
        GLFW.glfwGetFramebufferSize(windowPointer, frameBufferWidth, frameBufferHeight);
        int[] xpos = {0}, ypos = {0};
        GLFW.glfwGetWindowPos(windowPointer, xpos, ypos);
        double[] mx = {0}, my = {0};
        GLFW.glfwGetCursorPos(windowPointer, mx, my);

        update(windowWidth[0], windowHeight[0],
                frameBufferWidth[0], frameBufferHeight[0],
                xpos[0], ypos[0],
                mx[0], my[0]
        );
    }

    public void update(int windowWidth, int windowHeight, int framebufferWidth, int framebufferHeight,
                       int winPosX, int winPosY, double mousePosX, double mousePosY) {
        setWindowSize(new Vector2f(windowWidth, windowHeight));
        setFramebufferSize(new Vector2f(framebufferWidth, framebufferHeight));
        setPixelRatio((float) framebufferWidth / (float) windowWidth);
        setWindowPosition(new Vector2f(winPosX, winPosY));
        setMousePosition(new Vector2f((float) mousePosX, (float) mousePosY));
    }

    public float getPixelRatio() {
        return pixelRatio;
    }

    public void setPixelRatio(float pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    public Component getExampleGui() {
        return exampleGui;
    }

    public long getWindowPointer() {
        return windowPointer;
    }

    public void setWindowPointer(long windowPointer) {
        this.windowPointer = windowPointer;
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

    public Vector2f getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Vector2f windowSize) {
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
        return cursorPosition;
    }

    public Vector2f getCursorPositionPrev() {
        return cursorPositionPrev;
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
        if (event instanceof MouseClickEvent && ((MouseClickEvent) event).action == GLFW.GLFW_RELEASE) {
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
            ComponentContainer container = (ComponentContainer) gui;
            List<Component> all = container.getAll();
            for (Component element : all) {
                release(element, focused);
            }
        }
    }


    public boolean isTransferToChild() {
        return transferToChild;
    }

    public void setTransferToChild(boolean transferToChild) {
        this.transferToChild = transferToChild;
    }


}
