package org.liquidengine.legui.example;

import org.joml.Vector2i;
import org.liquidengine.legui.Legui;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.renderer.LeguiRenderer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class Example {
    public static final     int     WIDTH            = 400;
    public static final     int     HEIGHT           = 200;
    private static volatile boolean running          = false;
    private static          long[]  monitors         = null;
    private static          boolean toggleFullscreen = false;
    private static          boolean fullscreen       = false;

    public static void main(String[] args) throws IOException {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        long window = glfwCreateWindow(WIDTH, HEIGHT, "Example", NULL, NULL);
        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(0);

        PointerBuffer pointerBuffer = glfwGetMonitors();
        int           remaining     = pointerBuffer.remaining();
        System.out.println(remaining);
        monitors = new long[remaining];
        for (int i = 0; i < remaining; i++) {
            monitors[i] = pointerBuffer.get(i);
        }

        // Firstly we need to create frame component for window.
        Frame frame = new Frame();// new Frame(WIDTH, HEIGHT);
        // we can add elements here or on the fly
        createGuiElements(frame);

        // We need to create legui instance one for window
        // which hold all necessary library components
        // or if you want some customizations you can do it by yourself.
        Legui legui = new Legui(window, frame);

        GLFWKeyCallbackI         glfwKeyCallbackI         = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

        // if we want to create some callbacks for system events you should create and put them to keeper
        //
        // Wrong:
        // glfwSetKeyCallback(window, glfwKeyCallbackI);
        // glfwSetWindowCloseCallback(window, glfwWindowCloseCallbackI);
        //
        // Right:
        legui.getCallbackKeeper().getChainKeyCallback().add(glfwKeyCallbackI);
        legui.getCallbackKeeper().getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);


        // Initialization finished, so we can start render loop.
        running = true;

        // Everything can be done in one thread as well as in separated threads.
        // Here is one-thread example.

        // before render loop we need to initialize renderer
        legui.getRenderer().initialize();

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
            legui.getContext().updateGlfwWindow();
            Vector2i windowSize = null;//context.getWindowSize();

            glClearColor(1, 1, 1, 1);
            // Set viewport size
            glViewport(0, 0, windowSize.x, windowSize.y);
            // Clear screen
            glClear(GL_COLOR_BUFFER_BIT);

            // render frame
            legui.getRenderer().render(frame);

            // poll events to callbacks
            glfwPollEvents();
            glfwSwapBuffers(window);

            // Now we need to process events. Firstly we need to process system events.
            legui.getSystemEventProcessorManager().processEvent();

            // When system events are translated to GUI events we need to process them.
            // This event processor calls listeners added to ui components
            legui.getGuiEventProcessor().processEvent();
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
        }

        // And when rendering is ended we need to destroy renderer
        legui.getRenderer().destroy();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private static void createGuiElements(Frame frame) {
        // Set background color for frame
//        frame.setBackgroundColor(ColorConstants.lightBlue());
//
//        Button                    button  = new Button("Add components", 20, 20, 160, 30);
//        Button                    button2 = new Button("Toggle fullscreen", 200, 20, 160, 30);
//        SimpleRectangleLineBorder border  = new SimpleRectangleLineBorder(ColorConstants.black(), 1);
//        button.setBorder(border);
//
//        boolean[] added = {false};
//        button.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
//            if (!added[0]) {
//                added[0] = true;
//                frame.addAllComponents(generateOnFly());
//            }
//        });
//        button2.getLeguiEventListeners().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
//            if (event.getAction().equals(MouseClickEvent.MouseClickAction.CLICK)) {
//                toggleFullscreen = true;
//            }
//        });
//
        Component component = null;//
        frame.getContainer().add(component);
//        frame.addComponent(button2);
    }

//    private static List<Component> generateOnFly() {
//        List<Component> list = new ArrayList<>();
//
//        Label label = new Label(20, 60, 200, 20);
//        label.getTextState().setText("Generated on fly label");
//        label.getTextState().setTextColor(ColorConstants.red());
//
//        RadioButtonGroup group             = new RadioButtonGroup();
//        RadioButton      radioButtonFirst  = new RadioButton("First", 20, 90, 200, 20);
//        RadioButton      radioButtonSecond = new RadioButton("Second", 20, 110, 200, 20);
//
//        radioButtonFirst.setRadioButtonGroup(group);
//        radioButtonSecond.setRadioButtonGroup(group);
//
//        list.add(label);
//        list.add(radioButtonFirst);
//        list.add(radioButtonSecond);
//
//        return list;
//    }
}
