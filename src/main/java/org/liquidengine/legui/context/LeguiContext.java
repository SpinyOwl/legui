package org.liquidengine.legui.context;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.event.system.LeguiSystemEvent;
import org.liquidengine.legui.event.system.MouseClickEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiContext {

    private final Component exampleGui;
    private final Vector2f cursorPosition = new Vector2f();
    private final Vector2f cursorPositionPrev = new Vector2f();
    private final Vector2f[] mouseButtonPressPosition = new Vector2f[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private long targetPointer;
    private Vector2i targetSize;
    private Vector2f framebufferSize;
    private float pixelRatio;
    private Vector2f mousePosition;
    private Vector2f targetMousePosition;
    private Vector2f targetPosition;
    private Component focusedGui;
    private boolean[] mouseButtonStates = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private boolean transferToChild;

    private boolean debug = false;

    private Map<String, Object> contextData = new ConcurrentHashMap<>();


    public LeguiContext(long targetPointer, Component exampleGui) {
        this.targetPointer = targetPointer;
        this.exampleGui = exampleGui;
    }

    public void updateGlfwWindow() {
        int[] windowWidth = {0}, windowHeight = {0};
        GLFW.glfwGetWindowSize(targetPointer, windowWidth, windowHeight);
        int[] frameBufferWidth = {0}, frameBufferHeight = {0};
        GLFW.glfwGetFramebufferSize(targetPointer, frameBufferWidth, frameBufferHeight);
        int[] xpos = {0}, ypos = {0};
        GLFW.glfwGetWindowPos(targetPointer, xpos, ypos);
        double[] mx = {0}, my = {0};
        GLFW.glfwGetCursorPos(targetPointer, mx, my);

        update(windowWidth[0], windowHeight[0],
                frameBufferWidth[0], frameBufferHeight[0],
                xpos[0], ypos[0],
                mx[0], my[0]
        );
    }

    public void update(int targetWidth, int targetHeight, int framebufferWidth, int framebufferHeight,
                       int targetPosX, int targetPosY, double mousePosX, double mousePosY) {
        setTargetSize(new Vector2i(targetWidth, targetHeight));
        setFramebufferSize(new Vector2f(framebufferWidth, framebufferHeight));
        setPixelRatio((float) framebufferWidth / (float) targetWidth);
        setTargetPosition(new Vector2f(targetPosX, targetPosY));
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

    public long getTargetPointer() {
        return targetPointer;
    }

    public void setTargetPointer(long targetPointer) {
        this.targetPointer = targetPointer;
    }

    public Vector2f getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Vector2f mousePosition) {
        this.mousePosition = mousePosition;
    }

    public Vector2f getTargetMousePosition() {
        return targetMousePosition;
    }

    public void setTargetMousePosition(Vector2f targetMousePosition) {
        this.targetMousePosition = targetMousePosition;
    }

    public Vector2f getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Vector2f targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Vector2i getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(Vector2i targetSize) {
        this.targetSize = targetSize;
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
            List<Component> all = container.getComponents();
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

    public Map<String, Object> getContextData() {
        return contextData;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
