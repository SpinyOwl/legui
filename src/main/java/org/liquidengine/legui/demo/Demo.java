package org.liquidengine.legui.demo;

import org.joml.Vector2i;
import org.liquidengine.cbchain.impl.ChainErrorCallback;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.context.DefaultCallbackKeeper;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessor;
import org.liquidengine.legui.system.handler.processor.SystemEventProcessorImpl;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.Renderer;
import org.liquidengine.legui.system.renderer.nvg.NvgRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GLCapabilities;

import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL.setCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Aliaksandr_Shcherbin.
 */
public abstract class Demo {

    private int width;
    private int height;
    private String title;

    private volatile boolean running = false;

    private Thread mainThread;
    private Thread rendererThread;
    private Thread eventProcessorThread;
    private Thread leguiEventProcessorThread;

    private Renderer renderer;
    private EventProcessor leguiEventProcessors;
    private CallbackKeeper keeper;
    private SystemEventProcessor systemEventProcessor;
    private GLCapabilities glCapabilities;

    private long[] monitors;
    private Context context;

    private Frame frame;
    private long window;

    public Demo(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void run() {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());

        mainThread = new Thread(() -> {
            initialize();
            sleep(1000000000);
            startRenderer();
            startSystemEventProcessor();
            startLeguiEventProcessor();
            handleSystemEvents();
            destroy();
        }, "LEGUI_EXAMPLE");

        mainThread.start();
        // wait while not initialized
        while (!running) {
            Thread.yield();
        }
    }

    private void sleep(long sleepTime) {
        try {
            TimeUnit.NANOSECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startSystemEventProcessor() {
        eventProcessorThread = new Thread(() -> {
            while (running) {
                systemEventProcessor.processEvents(frame, context);
            }
        }, "GUI_SYSTEM_EVENT_PROCESSOR");
        eventProcessorThread.start();
    }

    private void handleSystemEvents() {
        while (running) {
            glfwWaitEvents();
        }
    }

    private void destroy() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void startLeguiEventProcessor() {
        leguiEventProcessorThread = new Thread(() -> {
            while (running) {
                EventProcessorProvider.getInstance().processEvents();
            }
        }, "GUI_EVENT_PROCESSOR");
        leguiEventProcessorThread.start();
    }

    private void render() {
        glfwMakeContextCurrent(window);
        glCapabilities = createCapabilities();
        glfwSwapInterval(0);

        renderer = new NvgRenderer();
        renderer.initialize();

        glfwMakeContextCurrent(window);
        setCapabilities(glCapabilities);
        glfwSwapInterval(0);

        while (running) {
            try {
                context.updateGlfwWindow();
                Vector2i framebufferSize = context.getFramebufferSize();

                glClearColor(1, 1, 1, 1);
                glViewport(0, 0, framebufferSize.x, framebufferSize.y);
                glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

                renderer.render(frame, context);

                glfwSwapBuffers(window);

                // update system. could be moved for example to game loop.
                update();

                // When everything done we need to relayout components.
                LayoutManager.getInstance().layout(frame);

                // also we need to run animations
                AnimatorProvider.getAnimator().runAnimations();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        renderer.destroy();
    }

    private void initialize() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        ChainErrorCallback errorCallback = new ChainErrorCallback();
        errorCallback.add(GLFWErrorCallback.createPrint(System.err));
        errorCallback.add(GLFWErrorCallback.createThrow());
        glfwSetErrorCallback(errorCallback);

        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);

        GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

        frame = new Frame(width, height);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        glfwSetWindowPos(window, 50, 50);
        glfwShowWindow(window);
        createGuiElements(frame, width, height);

        context = new Context(window);
        keeper = new DefaultCallbackKeeper();

        CallbackKeeper.registerCallbacks(window, keeper);
        keeper.getChainKeyCallback().add(glfwKeyCallbackI);
        keeper.getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

        systemEventProcessor = new SystemEventProcessorImpl();
        SystemEventProcessor.addDefaultCallbacks(keeper, systemEventProcessor);

        running = true;
    }

    private void startRenderer() {
        rendererThread = new Thread(this::render, "GUI_RENDERER");
        rendererThread.start();
    }

    public void stop() {
        running = false;
    }

    protected abstract void update();

    protected abstract void createGuiElements(Frame frame, int width, int height);

}
