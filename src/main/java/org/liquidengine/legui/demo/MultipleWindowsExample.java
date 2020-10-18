package org.liquidengine.legui.demo;

import org.joml.Vector2i;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
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
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Alexander on 17.12.2016.
 */
public class MultipleWindowsExample {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;
    private static volatile boolean running = false;

    public static void main(String[] args) {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        int N = 3;
        long[] windows = new long[N];
        Renderer[] renderers = new NvgRenderer[N];
        Context[] contexts = new Context[N];
        Frame[] frames = new Frame[N];
        CallbackKeeper[] keepers = new DefaultCallbackKeeper[N];
        SystemEventProcessor[] systemEventProcessors = new SystemEventProcessor[N];

        GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

        for (int i = 0; i < N; i++) {
            windows[i] = glfwCreateWindow(WIDTH, HEIGHT, "Multiple Windows Example " + (i+1), NULL, NULL);
            glfwShowWindow(windows[i]);

            glfwMakeContextCurrent(windows[i]);
            GL.createCapabilities();
            glfwSwapInterval(0);
            glfwSetWindowPos(windows[i], 50, 50 + (HEIGHT + 50) * i);
            // Renderer which will render our ui components.
            renderers[i] = new NvgRenderer();
            renderers[i].initialize();
            createGuiElements(frames[i] = new Frame(WIDTH, HEIGHT));

            contexts[i] = new Context(windows[i]);

            keepers[i] = new DefaultCallbackKeeper();
            CallbackKeeper.registerCallbacks(windows[i], keepers[i]);
            keepers[i].getChainKeyCallback().add(glfwKeyCallbackI);
            keepers[i].getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

            systemEventProcessors[i] = new SystemEventProcessorImpl();
            SystemEventProcessor.addDefaultCallbacks(keepers[i], systemEventProcessors[i]);
        }

        running = true;

        while (running) {

            for (int i = 0; i < N; i++) {
                glfwMakeContextCurrent(windows[i]);
                GL.getCapabilities();
                glfwSwapInterval(0);

                contexts[i].updateGlfwWindow();
                Vector2i windowSize = contexts[i].getFramebufferSize();

                glClearColor(1, 1, 1, 1);
                glViewport(0, 0, windowSize.x, windowSize.y);
                glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

                renderers[i].render(frames[i], contexts[i]);

                glfwPollEvents();
                glfwSwapBuffers(windows[i]);

                systemEventProcessors[i].processEvents(frames[i], contexts[i]);
                EventProcessorProvider.getInstance().processEvents();

                // When everything done we need to relayout components.
                LayoutManager.getInstance().layout(frames[i]);
            }
        }

        for (int i = 0; i < N; i++) {
            renderers[i].destroy();
            glfwDestroyWindow(windows[i]);
        }
        glfwTerminate();
    }

    private static void createGuiElements(Frame frame) {
        // Set background color for frame
        frame.getContainer().getStyle().getBackground().setColor(ColorConstants.lightBlue());

        Button button = new Button("Add components", 20, 20, 160, 30);
        SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
        button.getStyle().setBorder(border);

        boolean[] added = {false};
        button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (!added[0]) {
                added[0] = true;
                for (Component c : generateOnFly()) {
                    frame.getContainer().add(c);
                }
            }
        });

        button.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

        frame.getContainer().add(button);
    }

    private static List<Component> generateOnFly() {
        List<Component> list = new ArrayList<>();

        Label label = new Label(20, 60, 200, 20);
        label.getTextState().setText("Generated on fly label");
        label.getStyle().setTextColor(ColorConstants.red());

        RadioButtonGroup group = new RadioButtonGroup();
        RadioButton radioButtonFirst = new RadioButton("First", 20, 90, 200, 20);
        RadioButton radioButtonSecond = new RadioButton("Second", 20, 110, 200, 20);

        radioButtonFirst.setRadioButtonGroup(group);
        radioButtonSecond.setRadioButtonGroup(group);

        list.add(label);
        list.add(radioButtonFirst);
        list.add(radioButtonSecond);

        return list;
    }
}