package org.liquidengine.legui.example;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.context.LeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.liquidengine.legui.processor.SystemEventProcessor;
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
    private SystemEventProcessor systemEventProcessor;
    private LeguiEventProcessor uieventLeguiEventProcessor;

    private int updates;
    private int currentUps;

    private int time = 0;
    private boolean gcing;
    private LeguiCallbackKeeper callbackKeeper;


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
        startSystemEventProcessor();
        startLeguiEventProcessor();
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

        // create main gui component - for example it can be panel
        exampleGui = new ExampleGui(width, height);

        // create UI context. It holds main gui components and UI system states
        leguiContext = new LeguiContext(windowPointer, exampleGui);

        // enable debugging
        leguiContext.setDebug(true);

        // create callback keeper
        callbackKeeper = new LeguiCallbackKeeper(windowPointer);

        // Create event processor for system events (obtained from callbacks) Constructor automatically creates Callbacks and binds them to window
        systemEventProcessor = new SystemEventProcessor(exampleGui, leguiContext, callbackKeeper);
        // Create event processor for ui events
        uieventLeguiEventProcessor = new LeguiEventProcessor(exampleGui, leguiContext);
        // Set this processor to context, so generated ui events in system event processor will go to it.
        leguiContext.setLeguiEventProcessor(uieventLeguiEventProcessor);

        // create renderer (NanoVG renderer implementation). You can create your own implementation.
        renderer = new NvgLeguiRenderer(leguiContext);

//        callbackKeeper = systemEventProcessor.getCallbackKeeper(); //

        // create custom callbacks
        {
            GLFWWindowCloseCallbackI closeCallback = window1 -> running = false;
            GLFWKeyCallbackI keyCloseCallback = (window, key, code, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE) running = !running;
                if (key == GLFW_KEY_G && action != GLFW_RELEASE) gcing = !gcing;
            };
            callbackKeeper.getChainWindowCloseCallback().add(closeCallback);
            callbackKeeper.getChainKeyCallback().add(keyCloseCallback);
        }

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
            // update gui context
            leguiContext.updateGlfwWindow();
            Vector2i windowSize = leguiContext.getWindowSize();

            glClearColor(clearColor.x, clearColor.y, clearColor.z, 1);
            glViewport(0, 0, windowSize.x, windowSize.y);
            glClear(GL_COLOR_BUFFER_BIT);

            // render gui
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
                exampleGui.removeComponent(image);
                Image image1 = Image.createImage("org/liquidengine/legui/example/2.jpg");
                image1.setSize(image.getSize());
                image1.setPosition(image.getPosition());
                exampleGui.addComponent(image1);
                exampleGui.setImage(image1);
            }
        }
        exampleGui.getMouseTargetLabel().getTextState().setText("M.TARGET: " + leguiContext.getMouseTargetGui());

        Vector2f mousePosition = leguiContext.getMousePosition();
        exampleGui.getMouseLabel().getTextState().setText(String.format("X:%4s, Y:%4s", mousePosition.x, mousePosition.y));

        exampleGui.getUpsLabel().getTextState().setText("UPS: " + currentUps);

        exampleGui.getFocusedGuiLabel().getTextState().setText("FOCUSED: " + leguiContext.getFocusedGui());

        TextArea textArea = exampleGui.getTextArea();
        int caretPosition = textArea.getCaretPosition();
        String text = textArea.getTextState().getText();
        int left = caretPosition > 0 ? caretPosition - 1 : 0;
        int right = text.length() > 0 ? (caretPosition < text.length() - 1 ? caretPosition + 1 : text.length() - 1) : 0;
        String t = text.substring(left, right);
        exampleGui.getCaretp().getTextState().setText(caretPosition + " " + t);
    }

    private void startSystemEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) systemEventProcessor.processEvent();
        }, "GUI_SYSTEM_EVENT_PROCESSOR");
        eventProcessorThread.start();
    }

    private void startLeguiEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) uieventLeguiEventProcessor.processEvent();
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
