package org.liquidengine.legui.example;

import org.joml.Vector2i;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.context.DefaultLeguiCallbackKeeper;
import org.liquidengine.legui.context.ILeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.liquidengine.legui.processor.SystemEventProcessor;
import org.liquidengine.legui.render.LeguiRenderer;
import org.liquidengine.legui.render.nvg.NvgLeguiRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Shcherbin Alexander on 9/14/2016.
 */
public class Demo {

    private final      boolean resizable;
    private final      int     x;
    private final      int     y;
    protected          int     width;
    protected          int     height;
    protected volatile boolean running;
    protected          long    windowPointer;
    protected Vector4f clearColor = new Vector4f(1, 1, 1, 1);

    protected Thread mainThread;
    protected Thread rendererThread;
    protected Thread eventProcessorThread;
    protected Thread uiEventProcessorThread;

    protected String initialTitle;
    protected Frame  frame;

    protected LeguiContext         leguiContext;
    protected LeguiRenderer        renderer;
    protected SystemEventProcessor systemEventProcessor;
    protected LeguiEventProcessor  uiEventLeguiEventProcessor;

    protected int updates;
    protected int currentUps;

    protected int secondsFromStart = 0;
    protected boolean gcing;
    protected boolean fixedStep;
    protected boolean render = true;
    protected ILeguiCallbackKeeper callbackKeeper;


    public Demo(int width, int height, String title, Frame frame) {
        this(width, height, title, frame, true);
    }

    public Demo(int width, int height, String title, Frame frame, boolean resizable) {
        this(100, 100, width, height, title, frame, resizable);
    }

    public Demo(int x, int y, int width, int height, String title, Frame frame, boolean resizable) {
        this.width = width;
        this.height = height;
        this.initialTitle = title;
        this.frame = frame;
        this.resizable = resizable;
        this.x = x;
        this.y = y;
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
        while (!running) Thread.yield();
    }

    private void initialize() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        glfwSetErrorCallback(GLFWErrorCallback.createPrint());
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        windowPointer = glfwCreateWindow(width, height, initialTitle, NULL, NULL);
        glfwSetWindowPos(windowPointer, x, y);
        glfwShowWindow(windowPointer);

        // create UI context. It holds main gui components and UI system states
        leguiContext = new LeguiContext(windowPointer, frame);

        // enable debugging
//        leguiContext.setDebugEnabled(true);

        // create callback keeper
        DefaultLeguiCallbackKeeper leguiCallbackKeeper = new DefaultLeguiCallbackKeeper();
        callbackKeeper = leguiCallbackKeeper;
        leguiCallbackKeeper.registerCallbacks(windowPointer);

        // Create event processor for system events (obtained from callbacks) Constructor automatically creates Callbacks and binds them to window
        systemEventProcessor = new SystemEventProcessor(leguiContext, callbackKeeper);
        // Create event processor for ui events
        uiEventLeguiEventProcessor = new LeguiEventProcessor();
        // Set this processor to context, so generated ui events in system event processor will go to it.
        leguiContext.setLeguiEventProcessor(uiEventLeguiEventProcessor);

        // create renderer (NanoVG renderer implementation). You can create your own implementation.
        renderer = new NvgLeguiRenderer(leguiContext);

        // create custom callbacks
        {
            GLFWWindowCloseCallbackI closeCallback = window1 -> running = false;
            GLFWKeyCallbackI keyCloseCallback = (window, key, code, action, mods) -> {
                if (key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE) running = !running;
                if (key == GLFW_KEY_G && action != GLFW_RELEASE && mods == GLFW_MOD_CONTROL) gcing = !gcing;
                if (key == GLFW_KEY_F && action != GLFW_RELEASE && mods == GLFW_MOD_CONTROL) fixedStep = !fixedStep;
                if (key == GLFW_KEY_R && action != GLFW_RELEASE && mods == GLFW_MOD_CONTROL) render = !render;
                if (key == GLFW_KEY_H && action != GLFW_RELEASE && mods == GLFW_MOD_CONTROL) frame.getComponentLayer().getContainer().setVisible(!frame.getComponentLayer().getContainer().isVisible());
            };
            callbackKeeper.getChainWindowCloseCallback().add(closeCallback);
            callbackKeeper.getChainKeyCallback().add(keyCloseCallback);
            callbackKeeper.getChainWindowIconifyCallback().add((window, iconified) -> {
                if (iconified) {
                    GLFW.glfwSetWindowSize(window, 100, 100);
                    System.out.println("SET SIZE");
                    int w[] = {0}, h[] = {0};
                    GLFW.glfwGetWindowSize(window, w, h);
                    System.out.println("SIZE = " + w[0] + " x " + h[0]);
                }
            });
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
        glfwSwapInterval(0);

        long timer[] = {System.currentTimeMillis()};

        long   last[]         = {System.nanoTime()};
        long   delta[]        = {0};
        double nanosPerUpdate = 1_000_000_000 / 60;

        while (running) {
            if (render) {
                if (fixedStep) {
                    runWithFixedStep(timer, last, delta, nanosPerUpdate);
                } else {
                    runWithoutFixedStep(timer, last);
                }
            } else {
                int[] windowWidth = {0}, windowHeight = {0};
                GLFW.glfwGetWindowSize(windowPointer, windowWidth, windowHeight);
                glClearColor(clearColor.x, clearColor.y, clearColor.z, 1);
                glViewport(0, 0, windowWidth[0], windowHeight[0]);
                glClear(GL_COLOR_BUFFER_BIT);
                glfwSwapBuffers(windowPointer);
            }
        }

        renderer.destroy();
    }

    private void runWithoutFixedStep(long[] timer, long[] last) {
        long now = System.nanoTime();
        last[0] = now;

//        // update gui context
        leguiContext.updateGlfwWindow();
        Vector2i windowSize = leguiContext.getWindowSize();
        glClearColor(clearColor.x, clearColor.y, clearColor.z, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        glViewport(0, 0, windowSize.x, windowSize.y);
        glClear(GL_COLOR_BUFFER_BIT);

        // render gui
        renderer.render(frame);
        glfwSwapBuffers(windowPointer);

        update();
        updates++;

        if (System.currentTimeMillis() - timer[0] >= 1000) {
            secondsFromStart++;
            timer[0] += 1000;
            currentUps = updates;
            updates = 0;
        }
    }

    private void runWithFixedStep(long[] timer, long[] last, long[] delta, double nanosPerUpdate) {
        long now = System.nanoTime();
        delta[0] += now - last[0];
        last[0] = now;

        if (running) {
            if (delta[0] >= nanosPerUpdate) {
                do {
                    // update gui context
                    leguiContext.updateGlfwWindow();
                    Vector2i windowSize = leguiContext.getWindowSize();
                    glClearColor(clearColor.x, clearColor.y, clearColor.z, 1);
                    glViewport(0, 0, windowSize.x, windowSize.y);
                    glClear(GL_COLOR_BUFFER_BIT);

                    // render gui
                    renderer.render(frame);
                    glfwSwapBuffers(windowPointer);
                    update();

                    delta[0] -= nanosPerUpdate;
                    updates++;
                } while (running && delta[0] >= nanosPerUpdate);
            } else {
                sleep(1);
            }
        }
        if (System.currentTimeMillis() - timer[0] >= 1000) {
            secondsFromStart++;
            timer[0] += 1000;
            currentUps = updates;
            updates = 0;
            if (gcing) {
                System.gc();
            }
        }
    }

    private void sleep(long sleepTime) {
        try {
            TimeUnit.NANOSECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update() {
    }

    private void startSystemEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) {
                systemEventProcessor.processEvent();
                sleep(1);
            }
        }, "GUI_SYSTEM_EVENT_PROCESSOR");
        eventProcessorThread.start();
    }

    private void startLeguiEventProcessor() {
        uiEventProcessorThread = new Thread(() -> {
            while (running) {
                uiEventLeguiEventProcessor.processEvent();
                sleep(1);
            }
        }, "GUI_EVENT_PROCESSOR");
        uiEventProcessorThread.start();
    }

    // @formatter:off
    private void handleSystemEvents() {
        while (running) {
            glfwWaitEvents();
        }
    }
    // @formatter:on

    private void destroy() {
        glfwDestroyWindow(windowPointer);
        glfwTerminate();
    }

}
