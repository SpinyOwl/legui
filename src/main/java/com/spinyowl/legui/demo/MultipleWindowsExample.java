package com.spinyowl.legui.demo;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.RadioButton;
import com.spinyowl.legui.component.RadioButtonGroup;
import com.spinyowl.legui.event.CursorEnterEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.CursorEnterEventListener;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.system.context.CallbackKeeper;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.context.DefaultCallbackKeeper;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessor;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessorImpl;
import com.spinyowl.legui.system.layout.LayoutManager;
import com.spinyowl.legui.system.renderer.Renderer;
import com.spinyowl.legui.system.renderer.nvg.NvgRenderer;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

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

    GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action, mods) -> running = !(
        key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
    GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

    for (int i = 0; i < N; i++) {
      windows[i] = glfwCreateWindow(WIDTH, HEIGHT, "Multiple Windows Example " + (i + 1), NULL,
          NULL);
      glfwShowWindow(windows[i]);

      glfwMakeContextCurrent(windows[i]);
      GL.createCapabilities();
      glfwSwapInterval(0);
      glfwSetWindowPos(windows[i], 50, 50 + (HEIGHT + 50) * i);
      // Renderer which will render our ui components.
      renderers[i] = new NvgRenderer();
      renderers[i].initialize();
      createGuiElements(frames[i] = new Frame(WIDTH, HEIGHT));
      keepers[i] = new DefaultCallbackKeeper();
      CallbackKeeper.registerCallbacks(windows[i], keepers[i]);
      keepers[i].getChainKeyCallback().add(glfwKeyCallbackI);
      keepers[i].getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

      systemEventProcessors[i] = new SystemEventProcessorImpl();
      SystemEventProcessor.addDefaultCallbacks(keepers[i], systemEventProcessors[i]);

      contexts[i] = new Context(windows[i], systemEventProcessors[i]);

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

    button.getListenerMap()
        .addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

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