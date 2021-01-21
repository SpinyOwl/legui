package org.liquidengine.legui.demo;

import org.joml.Vector2i;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.Animator;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.Renderer;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.theme.colored.FlatColoredTheme;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

import static org.liquidengine.legui.style.color.ColorUtil.fromInt;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public class Example {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static volatile boolean running = false;
    private static long[] monitors = null;
    private static boolean toggleFullscreen = false;
    private static boolean fullscreen = false;
    private static ExampleGui gui;
    private static Context context;

//    private static String json = IOUtil.loadResourceAsString("org/liquidengine/legui/demo/json.json", 1024);

    public static void main(String[] args) {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());
        if (!glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        // create glfw window
        long window = glfwCreateWindow(WIDTH, HEIGHT, "Example", NULL, NULL);
        // show window
        glfwShowWindow(window);

        // make window current on thread
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(0);

        // read monitors
        PointerBuffer pointerBuffer = glfwGetMonitors();
        int remaining = pointerBuffer.remaining();
        monitors = new long[remaining];
        for (int i = 0; i < remaining; i++) {
            monitors[i] = pointerBuffer.get(i);
        }

        // create LEGUI theme and set it as default
        Themes.setDefaultTheme(new FlatColoredTheme(
            fromInt(245, 245, 245, 1), // backgroundColor
            fromInt(176, 190, 197, 1), // borderColor
            fromInt(176, 190, 197, 1), // sliderColor
            fromInt(100, 181, 246, 1), // strokeColor
            fromInt(165, 214, 167, 1), // allowColor
            fromInt(239, 154, 154, 1), // denyColor
            ColorConstants.transparent(), // shadowColor
            ColorConstants.darkGray(), // text color
            FontRegistry.getDefaultFont(), // font
            16f //font size
        ));

        // Firstly we need to create frame component for window.
        Frame frame = new Frame(WIDTH, HEIGHT);// new Frame(WIDTH, HEIGHT);
        createGuiElements(frame, WIDTH, HEIGHT);
        // also we can create frame for example just by unmarshal it
//        frame = GsonMarshalUtil.unmarshal(json);
//        frame.setSize(WIDTH, HEIGHT);

        // We need to create legui instance one for window
        // which hold all necessary library components
        // or if you want some customizations you can do it by yourself.
        DefaultInitializer initializer = new DefaultInitializer(window, frame);

        GLFWKeyCallbackI exitOnEscCallback = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
        GLFWKeyCallbackI toggleFullscreenCallback = (w1, key, code, action, mods) -> toggleFullscreen = (key == GLFW_KEY_F && action == GLFW_RELEASE && (mods & GLFW_MOD_CONTROL) != 0);
        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

        // if we want to create some callbacks for system events you should create and put them to keeper
        //
        // Wrong:
        // glfwSetKeyCallback(window, exitOnEscCallback);
        // glfwSetWindowCloseCallback(window, glfwWindowCloseCallbackI);
        //
        // Right:
        initializer.getCallbackKeeper().getChainKeyCallback().add(exitOnEscCallback);
        initializer.getCallbackKeeper().getChainKeyCallback().add(toggleFullscreenCallback);
        initializer.getCallbackKeeper().getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

        // Initialization finished, so we can start render loop.
        running = true;

        // Everything can be done in one thread as well as in separated threads.
        // Here is one-thread example.

        // before render loop we need to initialize renderer
        Renderer renderer = initializer.getRenderer();
        Animator animator = AnimatorProvider.getAnimator();
        renderer.initialize();

        long time = System.currentTimeMillis();
        int updCntr = 0;

        context = initializer.getContext();
//        context.setDebugEnabled(true);
        while (running) {

            // Before rendering we need to update context with window size and window framebuffer size
            //{
            //    int[] windowWidth = {0}, windowHeight = {0};
            //    GLFW.glfwGetWindowSize(window, windowWidth, windowHeight);
            //    int[] frameBufferWidth = {0}, frameBufferHeight = {0};
            //    GLFW.glfwGetFramebufferSize(window, frameBufferWidth, frameBufferHeight);
            //    int[] xpos = {0}, ypos = {0};
            //    GLFW.glfwGetWindowPos(window, xpos, ypos);
            //    double[] mx = {0}, my = {0};
            //    GLFW.glfwGetCursorPos(window, mx, my);
            //
            //    context.update(windowWidth[0], windowHeight[0],
            //            frameBufferWidth[0], frameBufferHeight[0],
            //            xpos[0], ypos[0],
            //            mx[0], my[0]
            //    );
            //}

            // Also we can do it in one line
            context.updateGlfwWindow();
            Vector2i windowSize = context.getFramebufferSize();

            glClearColor(1, 1, 1, 1);
            // Set viewport size
            glViewport(0, 0, windowSize.x, windowSize.y);
            // Clear screen
            glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

            // We need to relayout components.
            if (gui.getGenerateEventsByLayoutManager().isChecked()) {
                LayoutManager.getInstance().layout(frame, context);
            } else {
                LayoutManager.getInstance().layout(frame);
            }

            // render frame
            renderer.render(frame, context);

            // poll events to callbacks
            glfwPollEvents();
            glfwSwapBuffers(window);

            animator.runAnimations();

            // Now we need to handle events. Firstly we need to handle system events.
            // And we need to know to which frame they should be passed.
            initializer.getSystemEventProcessor().processEvents(frame, context);

            // When system events are translated to GUI events we need to handle them.
            // This event processor calls listeners added to ui components
            initializer.getGuiEventProcessor().processEvents();

            // check toggle fullscreen flag and execute.
            if (toggleFullscreen) {
                if (fullscreen) {
                    glfwSetWindowMonitor(window, NULL, 100, 100, WIDTH, HEIGHT, GLFW_DONT_CARE);
                } else {
                    GLFWVidMode glfwVidMode = glfwGetVideoMode(monitors[0]);
                    glfwSetWindowMonitor(window, monitors[0], 0, 0, glfwVidMode.width(), glfwVidMode.height(), glfwVidMode.refreshRate());
                }
                fullscreen = !fullscreen;
                toggleFullscreen = false;
            }

            update();
            updCntr++;
            if (System.currentTimeMillis() >= time + 1000) {
                time += 1000;
                glfwSetWindowTitle(window, "LEGUI Example. Updates per second: " + updCntr);
                updCntr = 0;
            }
        }

        // And when rendering is ended we need to destroy renderer
        renderer.destroy();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private static void update() {
        if (context != null) {
            Component mouseTargetGui = context.getMouseTargetGui();
            gui.getMouseTargetLabel().getTextState().setText("-> " + (mouseTargetGui == null ? null : mouseTargetGui.getClass().getSimpleName()));

            Component focusedGui = context.getFocusedGui();
            gui.getFocusedGuiLabel().getTextState().setText("-> " + (focusedGui == null ? null : focusedGui.getClass().getSimpleName()));
        }
    }

    private static void createGuiElements(Frame frame, int w, int h) {
        gui = new ExampleGui(w, h);
        gui.setFocusable(false);
        gui.getListenerMap().addListener(WindowSizeEvent.class, (WindowSizeEventListener) event -> gui.setSize(event.getWidth(), event.getHeight()));
        frame.getContainer().add(gui);
    }
}
