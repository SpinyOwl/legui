package org.liquidengine.legui.system;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.lwjgl.glfw.GLFW;

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

            FutureTask task = wstTasks.poll();
            if (task != null) {
                task.run();
            }
        }

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
            GLFW.glfwTerminate();
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

    // ------------------------------------------------------------------
    // --------    PROTECTED API     ------------------------------------
    // ------------------------------------------------------------------

    protected static void setVisible(long pointer) {
        instance.setVisibleP(pointer);
    }

    // ------------------------------------------------------------------
    // --------    PRIVATE API     --------------------------------------
    // ------------------------------------------------------------------

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

            if (pointer != 0) {
                Window window = new Window(pointer);
                windowMap.put(pointer, window);
                return window;
            } else {
                throw new RuntimeException("GLFW can't create a window");
            }
        });
    }

    private void destroyWindowP(Window window) {
        if (windowMap.containsValue(window)) {
            Long key = windowMap.getKey(window);
            createTaskAndGet(() -> {
                GLFW.glfwDestroyWindow(key);
                windowMap.removeValue(window);
                return null;
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
}
