package org.liquidengine.legui.system;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.joml.Vector2i;
import org.liquidengine.legui.animation.Animator;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.context.DefaultCallbackKeeper;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessor;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.nvg.NvgRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

/**
 * Class that provides easy way to create windows, abstract from creating contexts and initializing subsystems and concentrate only on developing application
 * using this library.
 */
public final class LeguiSystem {

    private static final LeguiSystem instance = new LeguiSystem();

    private Thread windowServiceThread;
    private volatile boolean running = false;
    private Queue<FutureTask> wstTasks;

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private BidiMap<Long, Window> windowMap;


    private LeguiSystem() {
    }

    private void wst() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("GLFW can't be initialized.");
        }

        running = true;
        while (running) {

            Set<Window> values = windowMap.values();

            // render cycle
            for (Window window : values) {
                glfwMakeContextCurrent(window.getPointer());
                GL.getCapabilities();
                glfwSwapInterval(0);

                window.getContext().updateGlfwWindow();
                Vector2i windowSize = window.getContext().getFramebufferSize();

                glClearColor(1, 1, 1, 1);
                glViewport(0, 0, windowSize.x, windowSize.y);
                glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

                window.getRenderer().render(window.getFrame(), window.getContext());

                glfwSwapBuffers(window.getPointer());
            }

            // poll events
            glfwPollEvents();

            // process system events per window
            for (Window window : windowMap.values()) {
                window.getSystemEventProcessor().processEvents(window.getFrame(), window.getContext());
            }

            // process legui events
            EventProcessor.getInstance().processEvents();

            // When everything done we need to relayout components.
            for (Window window : windowMap.values()) {
                LayoutManager.getInstance().layout(window.getFrame());
            }

            // Run animations. Should be also called cause some components use animations for updating state.
            Animator.getInstance().runAnimations();

            // after all we can process other tasks.
            FutureTask task = wstTasks.poll();
            if (task != null) {
                task.run();
            }
        }

        for (Window window : windowMap.values()) {
            window.getRenderer().destroy();
            glfwDestroyWindow(window.getPointer());
        }

        GLFW.glfwTerminate();
    }

    // ------------------------------------------------------------------
    // --------    PUBLIC API     ---------------------------------------
    // ------------------------------------------------------------------

    public static void initialize() {
        if (instance.initialized.compareAndSet(false, true)) {
            instance.wstTasks = new ConcurrentLinkedQueue<>();
            instance.windowMap = new DualHashBidiMap<>();
            instance.windowServiceThread = new Thread(instance::wst, "WST");
            instance.windowServiceThread.start();
        }
    }

    public static void destroy() {
        if (instance.initialized.compareAndSet(true, false)) {
            instance.running = false;
        }
    }


    public static Window createWindow(int width, int height, String title) {
        return instance.createWindowP(width, height, title, false);
    }

    public static Window createWindow(int width, int height, String title, boolean fullscreen) {
        return instance.createWindowP(width, height, title, fullscreen);
    }

    public static void destroyWindow(Window window) {
        instance.destroyWindowP(window);
    }

    public static List<Window> getWindows() {
        return new ArrayList<>(instance.windowMap.values());
    }

    public static Window getWindow(long pointer) {
        return instance.windowMap.get(pointer);
    }
    // ------------------------------------------------------------------
    // --------    PROTECTED API     ------------------------------------
    // ------------------------------------------------------------------

    protected static void setVisible(long pointer) {
        instance.setVisibleP(pointer);
    }

    protected static Vector2i getWindowSize(long pointer) {
        return instance.getWindowSizeP(pointer);
    }

    protected static Vector2i getWindowPosition(long pointer) {
        return instance.getWindowPosP(pointer);
    }

    protected static void setWindowSize(long pointer, int width, int height) {
        instance.setWindowSizeP(pointer, width, height);
    }

    protected static void setWindowPosition(long pointer, int x, int y) {
        instance.setWindowPosP(pointer, x, y);
    }

    // ------------------------------------------------------------------
    // --------    PRIVATE API     --------------------------------------
    // ------------------------------------------------------------------

    private Vector2i getWindowSizeP(long pointer) {
        return createTaskAndGet(() -> {
            int width[] = {0};
            int height[] = {0};
            GLFW.glfwGetWindowSize(pointer, width, height);
            return new Vector2i(width[0], height[0]);
        });
    }

    private void setWindowSizeP(long pointer, int width, int height) {
        createTaskNoWait(() -> GLFW.glfwSetWindowSize(pointer, width, height));
    }

    private Vector2i getWindowPosP(long pointer) {
        return createTaskAndGet(() -> {
            int x[] = {0};
            int y[] = {0};
            GLFW.glfwGetWindowPos(pointer, x, y);
            return new Vector2i(x[0], y[0]);
        });
    }

    private void setWindowPosP(long pointer, int x, int y) {
        createTaskNoWait(() -> GLFW.glfwSetWindowPos(pointer, x, y));
    }

    private Window createWindowP(int width, int height, String title, boolean fullscreen) {
        return createTaskAndGet(() -> {
            GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE);
            long pointer = 0;
            if (!fullscreen) {
                pointer = GLFW.glfwCreateWindow(width, height, title, 0, 0);
            } else {
                long monitor = GLFW.glfwGetPrimaryMonitor();
                pointer = GLFW.glfwCreateWindow(width, height, title, monitor, 0);
            }

            glfwMakeContextCurrent(pointer);
            GL.createCapabilities();
            glfwSwapInterval(0);

            if (pointer != 0) {
                Window window = new Window(pointer);

                window.setRenderer(new NvgRenderer());
                window.getRenderer().initialize();

                window.setContext(new Context(pointer));

                window.setCallbackKeeper(new DefaultCallbackKeeper());
                CallbackKeeper.registerCallbacks(pointer, window.getCallbackKeeper());

                window.setSystemEventProcessor(new SystemEventProcessor());
                window.getSystemEventProcessor().addDefaultCallbacks(window.getCallbackKeeper());

                window.setFrame(new Frame(width, height));


                windowMap.put(pointer, window);
                return window;
            } else {
                throw new RuntimeException("GLFW can't create a window");
            }
        });
    }

    private void destroyWindowP(Window window) {
        if (windowMap.containsValue(window)) {
            createTaskNoWait(() -> {
                long pointer = window.getPointer();
                windowMap.removeValue(window);
                window.getRenderer().destroy();
                window.setPointer(0);
                GLFW.glfwDestroyWindow(pointer);
            });
        }
    }

    private void setVisibleP(long pointer) {
        if (pointer == 0) {
            return;
        }
        this.<Void>createTaskAndGet(() -> {
            GLFW.glfwShowWindow(pointer);
            return null;
        });
    }


    private <T> T createTaskAndGet(Callable<T> callable) {
        FutureTask<T> task = new FutureTask<>(callable);
        wstTasks.add(task);
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTaskNoWait(Runnable runnable) {
        wstTasks.add(new FutureTask<>(() -> {
            runnable.run();
            return null;
        }));
    }
}
