package org.liquidengine.legui.example;

import org.joml.Vector2i;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
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
public class Demo {

    private final boolean resizable;
    protected int width;
    protected int height;
    protected volatile boolean running;
    protected long windowPointer;

    protected Vector4f clearColor = new Vector4f(1, 1, 1, 1);

    protected Thread mainThread;
    protected Thread rendererThread;
    protected Thread eventProcessorThread;

    protected String initialTitle;
    protected Component component;

    protected LeguiContext leguiContext;
    protected LeguiRenderer renderer;
    protected SystemEventProcessor systemEventProcessor;
    protected LeguiEventProcessor uiEventLeguiEventProcessor;

    protected int updates;
    protected int currentUps;

    protected int secondsFromStart = 0;
    protected boolean gcing;
    protected LeguiCallbackKeeper callbackKeeper;


    public Demo(int width, int height, String title, Component component) {
        this(width, height, title, component, true);
    }

    public Demo(int width, int height, String title, Component component, boolean resizable) {
        this.width = width;
        this.height = height;
        this.initialTitle = title;
        this.component = component;
        this.resizable = resizable;
    }

    public void start() {
        mainThread = new Thread(() -> {
            initialize();
            startRenderer();
            startSystemEventProcessor();
            startLeguiEventProcessor();
            handleSystemEvents();
            destroy();
        }, "LEGUI_EXAMPLE");

        mainThread.start();
        // wait while not initialized
        while (!running) ;
    }

    private void initialize() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }

        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        windowPointer = glfwCreateWindow(width, height, initialTitle, NULL, NULL);
//        glfwSwapInterval(1);
        glfwShowWindow(windowPointer);

        // create UI context. It holds main gui components and UI system states
        leguiContext = new LeguiContext(windowPointer, component);

        // enable debugging
        leguiContext.setDebug(true);

        // create callback keeper
        callbackKeeper = new LeguiCallbackKeeper(windowPointer);

        // Create event processor for system events (obtained from callbacks) Constructor automatically creates Callbacks and binds them to window
        systemEventProcessor = new SystemEventProcessor(component, leguiContext, callbackKeeper);
        // Create event processor for ui events
        uiEventLeguiEventProcessor = new LeguiEventProcessor();
        // Set this processor to context, so generated ui events in system event processor will go to it.
        leguiContext.setLeguiEventProcessor(uiEventLeguiEventProcessor);

        // create renderer (NanoVG renderer implementation). You can create your own implementation.
        renderer = new NvgLeguiRenderer(leguiContext);

//        callbackKeeper = systemEventProcessor.getCallbackKeeper(); //

        // create custom callbacks
        {
            GLFWWindowCloseCallbackI closeCallback = window1 -> running = false;
            GLFWKeyCallbackI keyCloseCallback = (window, key, code, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE) running = !running;
                if (key == GLFW_KEY_G && action != GLFW_RELEASE && mods == GLFW_MOD_CONTROL) gcing = !gcing;
            };
            callbackKeeper.getChainWindowCloseCallback().add(closeCallback);
            callbackKeeper.getChainKeyCallback().add(keyCloseCallback);
        }

        running = true;
    }

    private void startRenderer() {
        rendererThread = new Thread(this::render, "GUI_RENDERER");
        rendererThread.start();
    }

    private void render() {
        glfwMakeContextCurrent(windowPointer);
        GL.createCapabilities();
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
            renderer.render(component);

            glfwSwapBuffers(windowPointer);

            update();

            updates++;
            if (System.currentTimeMillis() - timer >= 1000) {
                secondsFromStart++;
                timer += 1000;
                currentUps = updates;
                updates = 0;
            }
        }

        renderer.destroy();
    }

    public void update() {
    }

    private void startSystemEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) systemEventProcessor.processEvent();
        }, "GUI_SYSTEM_EVENT_PROCESSOR");
        eventProcessorThread.start();
    }

    private void startLeguiEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) uiEventLeguiEventProcessor.processEvent();
        }, "GUI_EVENT_PROCESSOR");
        eventProcessorThread.start();
    }

    // @formatter:off
    private void handleSystemEvents() { while (running) { glfwPollEvents(); if(gcing) { System.gc(); } } }
    // @formatter:on

    private void destroy() {
        glfwDestroyWindow(windowPointer);
        glfwTerminate();
    }

}
