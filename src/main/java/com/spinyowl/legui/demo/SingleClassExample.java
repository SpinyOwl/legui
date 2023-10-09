package com.spinyowl.legui.demo;

import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.BOTTOM;
import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.LEFT;
import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.RIGHT;
import static com.spinyowl.legui.component.TabbedPanel.TabStripPosition.TOP;
import static com.spinyowl.legui.style.Style.DisplayType.FLEX;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
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

import com.spinyowl.legui.animation.AnimatorProvider;
import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.TabbedPanel;
import com.spinyowl.legui.component.TabbedPanel.Tab;
import com.spinyowl.legui.component.TabbedPanel.TabStripPosition;
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.event.CursorEnterEvent;
import com.spinyowl.legui.event.KeyEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.event.MouseClickEvent.MouseClickAction;
import com.spinyowl.legui.listener.CursorEnterEventListener;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.Style.PositionType;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.style.flex.FlexStyle.FlexDirection;
import com.spinyowl.legui.system.context.CallbackKeeper;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.context.DefaultCallbackKeeper;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessor;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessorImpl;
import com.spinyowl.legui.system.layout.LayoutManager;
import com.spinyowl.legui.system.renderer.Renderer;
import com.spinyowl.legui.system.renderer.nvg.NvgRenderer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

public class SingleClassExample {

  public static final int WIDTH = 400;
  public static final int HEIGHT = 400;
  private static volatile boolean running = false;
  private static Label mouseTargetLabel;

  public static void main(String[] args) throws IOException {
    System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
    System.setProperty("java.awt.headless", Boolean.TRUE.toString());
    if (!GLFW.glfwInit()) {
      throw new RuntimeException("Can't initialize GLFW");
    }
    long window = glfwCreateWindow(WIDTH, HEIGHT, "Single Class Example", NULL, NULL);
    glfwShowWindow(window);

    glfwMakeContextCurrent(window);
    GL.createCapabilities();
    glfwSwapInterval(0);

    // Firstly we need to create frame component for window.
    Frame frame = new Frame(WIDTH, HEIGHT);
    // we can add elements here or on the fly
    createGuiElements(frame);

    // We need to create callback keeper which will hold all of callbacks.
    // These callbacks will be used in initialization of system event processor
    // (will be added callbacks which will push system events to event queue and after that
    // processed by SystemEventProcessor)
    CallbackKeeper keeper = new DefaultCallbackKeeper();

    // register callbacks for window. Note: all previously binded callbacks will be unbinded.
    CallbackKeeper.registerCallbacks(window, keeper);

    GLFWKeyCallbackI glfwKeyCallbackI =
        (w1, key, code, action, mods) ->
            running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
    GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

    // if we want to create some callbacks for system events you should create and put them to
    // keeper
    //
    // Wrong:
    // glfwSetKeyCallback(window, glfwKeyCallbackI);
    // glfwSetWindowCloseCallback(window, glfwWindowCloseCallbackI);
    //
    // Right:
    keeper.getChainKeyCallback().add(glfwKeyCallbackI);
    keeper.getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

    // Event processor for system events. System events should be processed and translated to gui
    // events.
    SystemEventProcessor systemEventProcessor = new SystemEventProcessorImpl();
    SystemEventProcessor.addDefaultCallbacks(keeper, systemEventProcessor);

    // We need to create legui context which shared by renderer and event processor.
    // Also we need to pass event processor for ui events such as click on component, key typing and
    // etc.
    Context context = new Context(window, systemEventProcessor);

    // Also we need to create renderer provider
    // and create renderer which will render our ui components.
    Renderer renderer = new NvgRenderer();

    // Initialization finished, so we can start render loop.
    running = true;

    // Everything can be done in one thread as well as in separated threads.
    // Here is one-thread example.

    // before render loop we need to initialize renderer
    renderer.initialize();

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

      // render frame
      renderer.render(frame, context);

      // poll events to callbacks
      glfwPollEvents();
      glfwSwapBuffers(window);

      // Now we need to process events. Firstly we need to process system events.
      systemEventProcessor.processEvents(frame, context);

      // When system events are translated to GUI events we need to process them.
      // This event processor calls listeners added to ui components
      EventProcessorProvider.getInstance().processEvents();

      // When everything done we need to relayout components.
      LayoutManager.getInstance().layout(frame);

      // Run animations. Should be also called cause some components use animations for updating
      // state.
      AnimatorProvider.getAnimator().runAnimations();

      update(context);
    }

    // And when rendering is ended we need to destroy renderer
    renderer.destroy();

    glfwDestroyWindow(window);
    glfwTerminate();
  }

  private static void update(Context context) {
    Component target = context.getMouseTargetGui();
    if (mouseTargetLabel != null) {
      mouseTargetLabel
          .getTextState()
          .setText("-> " + (target == null ? "null" : target.getClass().getSimpleName()));
    }
  }

  private static void createGuiElements(Frame frame) {
    // Set background color for frame
    frame.getContainer().getStyle().getBackground().setColor(ColorConstants.lightBlue());
    frame.getContainer().setFocusable(false);
    //    frame.getContainer().getStyle().setDisplay(FLEX);
    //    frame.getContainer().getStyle().setPosition(PositionType.ABSOLUTE);
    //    frame.getContainer().getStyle().getFlexStyle().setFlexDirection(FlexDirection.COLUMN);
    //
    //    Panel menuBar = createPanel(ColorConstants.blue());
    //    Panel mainPanel = createPanel(ColorConstants.red());
    //    Panel animationPanel = createPanel(ColorConstants.green());
    //
    //    frame.getContainer().addAll(List.of(menuBar, mainPanel, animationPanel));
    Button button = new Button("Add components", 10, 10, 160, 30);
    SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
    button.getStyle().setBorder(border);

    AtomicBoolean added = new AtomicBoolean(false);
    button
        .getListenerMap()
        .addListener(
            MouseClickEvent.class,
            (MouseClickEventListener)
                event -> {
                  if (!added.getAndSet(true)) {
                    for (Component c : generateOnFly()) {
                      frame.getContainer().add(c);
                    }
                  }
                });

    button
        .getListenerMap()
        .addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

    mouseTargetLabel = new Label("Hello Label 1", 10, HEIGHT - 30, WIDTH - 20, 20);
    frame.getContainer().add(mouseTargetLabel);

    frame.getContainer().add(button);
  }

  @NotNull
  private static Panel createPanel(Vector4f color) {
    var panel = new Panel();
    panel.getStyle().setDisplay(FLEX);
    panel.getStyle().getFlexStyle().setFlexDirection(FlexDirection.ROW);
    panel.getStyle().setBorderRadius(0f);
    panel.getStyle().setPosition(PositionType.RELATIVE);
    panel.getStyle().getFlexStyle().setFlexGrow(1);
    panel.getStyle().setMarginBottom(1f);
    panel.getStyle().getBackground().setColor(color);
    //    panel.getStyle().setMinWidth(50F);
    panel.getStyle().setMinHeight(50F);
    panel.getStyle().setMaxHeight(50F);
    panel.getStyle().setMargin(5, 5, 1, 5);
    return panel;
  }

  private static List<Component> generateOnFly() {
    List<Component> list = new ArrayList<>();

    Widget widget = new Widget(10, 50, 380, 240);
    widget.getContainer().getStyle().setDisplay(FLEX);
    widget.setDraggable(false);

    TabbedPanel tabbedPanel = new TabbedPanel();
    tabbedPanel.getStyle().setPosition(PositionType.RELATIVE);
    tabbedPanel.getStyle().getFlexStyle().setFlexGrow(1);
    tabbedPanel.getStyle().getFlexStyle().setFlexShrink(1);
    tabbedPanel.getStyle().setMargin(10F);
    tabbedPanel.getStyle().setBorder(new SimpleLineBorder(ColorConstants.black(), 1F));
    widget.getContainer().add(tabbedPanel);

    Tab tab1 = new Tab("Planes", new Label("Show all planes available"));
    Tab tab2 = new Tab("Cars", new Label("Show all cars available"));
    Tab tab3 = new Tab("Boats", new Label("Show all boats available"));
    tabbedPanel.addTab(tab1);
    tabbedPanel.addTab(tab2);
    tabbedPanel.addTab(tab3);

    AtomicInteger tabIdx = new AtomicInteger();
    Button addTabButton = new Button("Add tab", 180, 10, 70, 30);
    addTabButton
        .getListenerMap()
        .addListener(
            MouseClickEvent.class,
            event -> {
              if (MouseClickAction.CLICK.equals(event.getAction())) {
                String tabName = "Tab #" + tabIdx.incrementAndGet();
                Component tabComponent = new Panel();
                tabComponent.getStyle().getBackground().setColor(ColorUtil.randomColor());
                tabComponent.getStyle().setPadding(10F, 20F);
                tabbedPanel.addTab(new Tab(tabName, tabComponent));
              }
            });

    Button removeTabButton = new Button("Remove tab", 260, 10, 70, 30);
    removeTabButton
        .getListenerMap()
        .addListener(
            MouseClickEvent.class,
            event -> {
              if (MouseClickAction.CLICK.equals(event.getAction()) && tabbedPanel.tabCount() > 0) {
                tabbedPanel.removeTab(tabbedPanel.getCurrentTab());
              }
            });

    Button changeStripPosition = new Button("Switch", 340, 10, 50, 30);
    changeStripPosition
        .getListenerMap()
        .addListener(
            MouseClickEvent.class,
            event -> {
              if (MouseClickAction.CLICK.equals(event.getAction())) {
                TabStripPosition current = tabbedPanel.getTabStripPosition();
                if (TOP == current) {
                  tabbedPanel.setTabStripPosition(LEFT);
                } else if (LEFT == current) {
                  tabbedPanel.setTabStripPosition(BOTTOM);
                } else if (BOTTOM == current) {
                  tabbedPanel.setTabStripPosition(RIGHT);
                } else {
                  tabbedPanel.setTabStripPosition(TOP);
                }
              }
            });

    widget
        .getTitle()
        .getListenerMap()
        .addListener(
            KeyEvent.class,
            event -> {
              if (event.getAction() == GLFW_RELEASE) {
                if (event.getKey() == GLFW_KEY_1) {
                  tabbedPanel.setTabWidth(30);
                } else if (event.getKey() == GLFW_KEY_2) {
                  tabbedPanel.setTabHeight(120);
                } else if (event.getKey() == GLFW_KEY_3) {
                  tabbedPanel.setTabStripPosition(LEFT);
                } else if (event.getKey() == GLFW_KEY_4) {
                  tabbedPanel.setTabWidth(120);
                } else if (event.getKey() == GLFW_KEY_5) {
                  tabbedPanel.setTabHeight(30);
                } else if (event.getKey() == GLFW_KEY_6) {
                  tabbedPanel.setTabStripPosition(TOP);
                }
              }
            });
    list.add(addTabButton);
    list.add(removeTabButton);
    list.add(changeStripPosition);
    list.add(widget);

    //    Panel panel = new Panel(20, 100, 100, 100);
    //    panel.getStyle().setDisplay(FLEX);
    //    panel.getStyle().setMinWidth(100F);
    //    panel.getStyle().setMinHeight(100F);
    ////    panel.getStyle().setWidth(Auto.AUTO);
    ////    panel.getStyle().setHeight(Auto.AUTO);
    //    panel.getStyle().setLeft(20);
    //    panel.getStyle().setTop(100);
    //    panel.getStyle().getFlexStyle().setAlignItems(AlignItems.FLEX_START);
    ////    panel.getStyle().getFlexStyle().setFlexWrap(FlexWrap.WRAP);
    //
    //    Button add = new Button("Add", 180, 10, 50, 30);
    //    int index = 0;
    //    add.getListenerMap().addListener(MouseClickEvent.class, event -> {
    //      if (MouseClickAction.CLICK.equals(event.getAction())) {
    //        Panel component = new Panel();
    //
    //        component.getStyle().setWidth(20F);
    //        component.getStyle().setHeight(20F);
    //
    //        component.getStyle().setMinWidth(20F);
    //        component.getStyle().setMinHeight(20F);
    //
    //        component.getStyle().getFlexStyle().setFlexGrow(1);
    //        component.getStyle().getFlexStyle().setFlexShrink(1);
    //
    //        component.getStyle().setPosition(PositionType.RELATIVE);
    //
    //        panel.add(component);
    //      }
    //    });
    //
    //    Button remove = new Button("Remove tab", 260, 10, 70, 30);
    //    remove.getListenerMap().addListener(MouseClickEvent.class, event -> {
    //      if (MouseClickAction.CLICK.equals(event.getAction()) && panel.count() > 0) {
    //        panel.remove(panel.count() - 1);
    //      }
    //    });
    //
    //    list.add(panel);
    //    list.add(add);

    return list;
  }
}
