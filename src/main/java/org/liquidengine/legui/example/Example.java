package org.liquidengine.legui.example;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.context.LeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.liquidengine.legui.render.LeguiRenderer;
import org.liquidengine.legui.render.nvg.NvgLeguiRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public class Example {

    private boolean running;
    private int width;
    private int height;
    private long windowPointer;

    private Vector4f clearColor = new Vector4f(1, 1, 1, 1);

    private Thread rendererThread;
    private Thread eventProcessorThread;

    private String initialTitle;
    private ExampleGui exampleGui;

    private LeguiContext leguiContext;
    private LeguiRenderer renderer;
    private LeguiEventProcessor eventProcessor;
    private LeguiCallbackKeeper callbackKeeper;

    private int updates;
    private int currentUps;
    private int time = 0;

    private boolean gcing;


    public Example(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.initialTitle = title;
    }

    public static void main(String[] args) {
        Example example = new Example(800, 600, "Example");
        example.run();
    }

    private void run() {
        initialize();
        startRenderer();
        startEventProcessor();
        handleSystemEvents();
        destroy();
    }

    private void initialize() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }

        windowPointer = glfwCreateWindow(width, height, initialTitle, NULL, NULL);
//        glfwSwapInterval(1);
        glfwShowWindow(windowPointer);

        exampleGui = new ExampleGui(width, height);
        leguiContext = new LeguiContext(windowPointer, exampleGui);
        leguiContext.setDebug(true);

        eventProcessor = new LeguiEventProcessor(exampleGui, leguiContext);
        renderer = new NvgLeguiRenderer(leguiContext);

        callbackKeeper = eventProcessor.getCallbacks();
        GLFWWindowCloseCallbackI closeCallback = window1 -> running = false;
        GLFWKeyCallbackI keyCloseCallback = (window, key, code, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE) running = !running;
            if (key == GLFW_KEY_G && action != GLFW_RELEASE) gcing = !gcing;
        };
        callbackKeeper.getChainWindowCloseCallback().add(closeCallback);
        callbackKeeper.getChainKeyCallback().add(keyCloseCallback);
        running = true;
    }

    private void startRenderer() {
        rendererThread = new Thread(() -> render(), "GUI_RENDERER");
        rendererThread.start();
    }

    private void render() {
        glfwMakeContextCurrent(windowPointer);
        GLCapabilities capabilities = GL.createCapabilities();
        renderer.initialize();

        long timer = System.currentTimeMillis();

        while (running) {
            leguiContext.updateGlfwWindow();

            glClearColor(clearColor.x, clearColor.y, clearColor.z, 1);
            Vector2i windowSize = leguiContext.getTargetSize();
            glViewport(0, 0, windowSize.x, windowSize.y);
            glClear(GL_COLOR_BUFFER_BIT);

            renderer.render(exampleGui);

            glfwSwapBuffers(windowPointer);

            update();

            updates++;
            if (System.currentTimeMillis() - timer >= 1000) {
                time++;
                timer += 1000;
                currentUps = updates;
                updates = 0;
            }
        }

        renderer.destroy();
    }

    private void update() {
        if (time > 5) {
            Image image = exampleGui.getImage();
            if (image != null && image.getPath().equals("org/liquidengine/legui/example/1.jpg")) {
                exampleGui.getContainer().removeComponent(image);
                Image image1 = Image.createImage("org/liquidengine/legui/example/2.jpg");
                exampleGui.getContainer().addComponent(image1);
                exampleGui.setImage(image1);
            }
        }
        Label label = exampleGui.getMouseTargetLabel();
        label.getTextState().setText(leguiContext.getMouseTargetGui() + "");

        Label mouseLabel = exampleGui.getMouseLabel();
        Vector2f mousePosition = leguiContext.getMousePosition();
        mouseLabel.getTextState().setText(String.format("X:%4s, Y:%4s", mousePosition.x, mousePosition.y));

        Label upsLabel = exampleGui.getUpsLabel();
        upsLabel.getTextState().setText("UPS: " + currentUps);
    }

    private void startEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) eventProcessor.processEvent();
        }, "GUI_EVENT_PROCESSOR");
        eventProcessorThread.start();
    }

    // @formatter:off
    private void handleSystemEvents() { while (running) { glfwPollEvents(); if(gcing) { System.out.println("GC"); System.gc(); } } }
    // @formatter:on

    private void destroy() {
        glfwDestroyWindow(windowPointer);
        glfwTerminate();
    }

}
