package com.spinyowl.legui.demo;

import static com.spinyowl.legui.style.color.ColorUtil.rgba;
import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
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

import com.spinyowl.legui.DefaultInitializer;
import com.spinyowl.legui.animation.Animator;
import com.spinyowl.legui.animation.AnimatorProvider;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.event.WindowSizeEvent;
import com.spinyowl.legui.listener.WindowSizeEventListener;
import com.spinyowl.legui.style.Style.DisplayType;
import com.spinyowl.legui.style.Style.PositionType;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.layout.LayoutManager;
import com.spinyowl.legui.system.renderer.Renderer;
import com.spinyowl.legui.theme.Themes;
import com.spinyowl.legui.theme.colored.FlatColoredTheme;
import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

public class Example {

  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;
  private static volatile boolean running = false;
  private static long[] monitors = null;
  private static boolean toggleFullscreen = false;
  private static boolean fullscreen = false;
  private static ExampleGui gui;
  private static Context context;

  //    private static String json =
  // IOUtil.loadResourceAsString("com/spinyowl/legui/demo/json.json", 1024);

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
    //    Themes.setDefaultTheme(new FlatColoredTheme(
    //        rgba(255, 255, 255, 1), // backgroundColor
    //        rgba(176, 190, 197, 1), // borderColor
    //        rgba(176, 190, 197, 1), // sliderColor
    //        rgba(100, 181, 246, 1), // strokeColor
    //        rgba(194, 219, 245, 1), // allowColor
    //        rgba(239, 154, 154, 1), // denyColor
    //        ColorConstants.transparent(), // shadowColor
    //        ColorConstants.darkGray(), // text color
    //        FontRegistry.getDefaultFont(), // font
    //        FlatColoredTheme.FONT_SIZE
    //    ));

    // Firstly we need to create frame component for window.
    Frame frame = new Frame(WIDTH, HEIGHT); // new Frame(WIDTH, HEIGHT);
    createGuiElements(frame, WIDTH, HEIGHT);
    // also we can create frame for example just by unmarshal it
    //        frame = GsonMarshalUtil.unmarshal(json);
    //        frame.setSize(WIDTH, HEIGHT);

    // We need to create legui instance one for window
    // which hold all necessary library components
    // or if you want some customizations you can do it by yourself.
    DefaultInitializer initializer = new DefaultInitializer(window, frame);

    GLFWKeyCallbackI exitOnEscCallback =
        (w1, key, code, action, mods) ->
            running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
    GLFWKeyCallbackI toggleFullscreenCallback =
        (w1, key, code, action, mods) ->
            toggleFullscreen =
                (key == GLFW_KEY_F && action == GLFW_RELEASE && (mods & GLFW_MOD_CONTROL) != 0);
    GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

    // if we want to create some callbacks for system events you should create and put them to
    // keeper
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
      // {
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
      // }

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
          glfwSetWindowMonitor(
              window,
              monitors[0],
              0,
              0,
              glfwVidMode.width(),
              glfwVidMode.height(),
              glfwVidMode.refreshRate());
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
      gui.getMouseTargetLabel()
          .getTextState()
          .setText(
              "-> " + (mouseTargetGui == null ? null : mouseTargetGui.getClass().getSimpleName()));

      Component focusedGui = context.getFocusedGui();
      gui.getFocusedGuiLabel()
          .getTextState()
          .setText("-> " + (focusedGui == null ? null : focusedGui.getClass().getSimpleName()));
    }
  }

  private static void createGuiElements(Frame frame, int w, int h) {
    gui = new ExampleGui(w, h);
    gui.setFocusable(false);
    gui.getStyle().setMinWidth(100F);
    gui.getStyle().setMinHeight(100F);
    gui.getStyle().getFlexStyle().setFlexGrow(1);
    gui.getStyle().setPosition(PositionType.RELATIVE);
    gui.getStyle().getBackground().setColor(ColorConstants.transparent());

    frame.getContainer().getStyle().setDisplay(DisplayType.FLEX);
    frame.getContainer().add(gui);
  }
}
