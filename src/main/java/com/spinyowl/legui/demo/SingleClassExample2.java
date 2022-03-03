package com.spinyowl.legui.demo;

import static com.spinyowl.legui.style.Style.DisplayType.FLEX;
import static com.spinyowl.legui.style.Style.PositionType.ABSOLUTE;
import static com.spinyowl.legui.style.Style.PositionType.RELATIVE;
import static com.spinyowl.legui.style.flex.FlexStyle.FlexDirection.COLUMN;
import static com.spinyowl.legui.style.flex.FlexStyle.FlexDirection.ROW;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
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
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.intersection.Intersector;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.style.flex.FlexStyle.AlignContent;
import com.spinyowl.legui.style.flex.FlexStyle.AlignItems;
import com.spinyowl.legui.style.flex.FlexStyle.JustifyContent;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.system.context.CallbackKeeper;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.context.DefaultCallbackKeeper;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessor;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessorImpl;
import com.spinyowl.legui.system.layout.LayoutManager;
import com.spinyowl.legui.system.renderer.Renderer;
import com.spinyowl.legui.system.renderer.nvg.NvgRenderer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

public class SingleClassExample2 {

  public static final int WIDTH = 640;
  public static final int HEIGHT = 320;

  // fires only if in bottom right half of button.
  private static final Intersector RESIZE_BUTTON_INTERSECTOR =
      new Intersector() {
        @Override
        public boolean intersects(Component component, Vector2f point) {
          Vector2f size = component.getSize();
          Vector2f pos = component.getAbsolutePosition();

          boolean inRect =
              point.x >= pos.x
                  && point.y >= pos.y
                  && point.x <= pos.x + size.x
                  && point.y <= pos.y + size.y;
          if (inRect) {
            return point.y >= ((size.x + pos.x - point.x) * size.y) / size.x + pos.y;
          }
          return false;
        }
      };

  private static volatile boolean running = false;

  public static void main(String[] args) throws IOException {
    System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
    System.setProperty("java.awt.headless", Boolean.TRUE.toString());
    if (!GLFW.glfwInit()) {
      throw new RuntimeException("Can't initialize GLFW");
    }
    long window = glfwCreateWindow(WIDTH, HEIGHT, "Legui", NULL, NULL);
    glfwShowWindow(window);

    glfwMakeContextCurrent(window);
    GL.createCapabilities();
    glfwSwapInterval(0);

    // Firstly we need to create frame component for window.
    Frame frame = new Frame(WIDTH, HEIGHT);
    // we can add elements here or on the fly

    // We need to create legui context which shared by renderer and event processor.
    // Also we need to pass event processor for ui events such as click on component, key typing and
    // etc.
    Context context = new Context(window);

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

    // Also we need to create renderer provider
    // and create renderer which will render our ui components.
    Renderer renderer = new NvgRenderer();

    // Initialization finished, so we can start render loop.
    running = true;

    // Everything can be done in one thread as well as in separated threads.
    // Here is one-thread example.

    // before render loop we need to initialize renderer
    renderer.initialize();

    AtomicBoolean needToUpdate = new AtomicBoolean(true);
    keeper
        .getChainKeyCallback()
        .add(
            (w, key, scancode, action, mods) -> {
              if (key == GLFW_KEY_F1 && action == GLFW_RELEASE) needToUpdate.set(true);
            });
    //    createUI(frame);
    while (running) {
      if (needToUpdate.getAndSet(false)) createUI(frame);

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
    }

    // And when rendering is ended we need to destroy renderer
    renderer.destroy();

    glfwDestroyWindow(window);
    glfwTerminate();
  }

  private static void createUI(Frame frame) {
    frame.getContainer().clearChildComponents();
    frame.getContainer().getStyle().setDisplay(FLEX);
    frame.getContainer().getStyle().getFlexStyle().setFlexDirection(ROW);
    frame.getContainer().getStyle().getBackground().setColor(ColorUtil.rgba(0xe1, 0xf5, 0xfe, 1));

    Panel leftPanel = createLeftPanel();
    frame.getContainer().add(leftPanel);

    Panel rightPanel = createRightPanel();
    frame.getContainer().add(rightPanel);
  }

  private static Panel createRightPanel() {
    int minWidth = WIDTH - 260;
    int minHeight = HEIGHT - 40;
    Panel contentPanel = new Panel();
    contentPanel.getStyle().setMinWidth(minWidth);
    contentPanel.getStyle().setMinWidth(minWidth);
    contentPanel.getStyle().setMinHeight(minHeight);
    contentPanel.getStyle().getFlexStyle().setFlexGrow(1);
    contentPanel.getStyle().getFlexStyle().setFlexShrink(1);
    contentPanel.getStyle().setMargin(20F, 20F, 20F, 0F);
    contentPanel.getStyle().setPosition(RELATIVE);

    // content panel has manual display type - so we have to manually set size and position for
    // components via constructor or #setSize & #setPosition
    int gap = 30;
    int wid = (minWidth - gap * 3) / 2;
    int hei = (minHeight - gap * 3) / 2;

    Widget widget1 = new Widget("", gap, gap, wid, hei);
    setWidgetStyles(widget1);
    contentPanel.add(widget1);

    Widget widget2 = new Widget("", wid + gap * 2, gap, wid, hei);
    setWidgetStyles(widget2);
    contentPanel.add(widget2);

    Widget widget3 = new Widget("", gap, hei + gap * 2, wid, hei);
    setWidgetStyles(widget3);
    contentPanel.add(widget3);

    Widget widget4 = new Widget("", wid + gap * 2, hei + gap * 2, wid, hei);
    setWidgetStyles(widget4);
    contentPanel.add(widget4);

    return contentPanel;
  }

  private static Panel createLeftPanel() {
    Panel leftPanel = new Panel();
    leftPanel.getStyle().setMargin(20F);
    leftPanel.getStyle().setMinWidth(200F);
    leftPanel.getStyle().setWidth(200F);
    leftPanel.getStyle().setMinHeight(HEIGHT - 40F);
    leftPanel.getStyle().setPosition(RELATIVE);
    leftPanel.getStyle().getFlexStyle().setFlexDirection(COLUMN);
    leftPanel.getStyle().getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
    leftPanel.getStyle().getFlexStyle().setAlignContent(AlignContent.STRETCH);
    leftPanel.getStyle().getFlexStyle().setAlignItems(AlignItems.STRETCH);

    leftPanel.getStyle().setDisplay(FLEX);

    Panel personPanel = createPersonPanel();
    leftPanel.add(personPanel);

    SimpleLineBorder border = new SimpleLineBorder(ColorConstants.lightGray(), 0.8f);

    leftPanel.add(createMenuButton("Protect", border));
    leftPanel.add(createMenuButton("Some", border));
    leftPanel.add(createMenuButton("Cool", border));
    leftPanel.add(createMenuButton("Aliens", border));

    Button apply = new Button("\uF12C");
    setBottomButtonStyles(apply, border);
    apply.getStyle().getBackground().setColor(ColorUtil.rgba(0xa5, 0xd6, 0xa7, 1));
    apply.getStyle().setLeft(0);
    apply.getStyle().setBottom(0);
    leftPanel.add(apply);

    Button decline = new Button("\uF156");
    setBottomButtonStyles(decline, border);
    decline.getStyle().getBackground().setColor(ColorUtil.rgba(0xef, 0x9a, 0x9a, 1));
    decline.getStyle().setRight(0);
    decline.getStyle().setBottom(0);
    leftPanel.add(decline);

    return leftPanel;
  }

  private static void setBottomButtonStyles(Button apply, SimpleLineBorder border) {
    apply.getStyle().setBorder(border);
    apply.getStyle().setShadow(null);
    apply.getStyle().setHeight(40F);
    apply.getStyle().setWidth(100F);
    apply.getStyle().setPosition(ABSOLUTE);
    apply.getStyle().setFont(FontRegistry.MATERIAL_DESIGN_ICONS);
  }

  private static Button createMenuButton(String text, SimpleLineBorder border) {
    Button menuButton = new Button(text);
    menuButton.getStyle().setPosition(RELATIVE);
    menuButton.getStyle().setMinHeight(40F);
    menuButton.getStyle().setBorder(border);
    menuButton.getStyle().setShadow(null);
    return menuButton;
  }

  private static Panel createPersonPanel() {
    Panel personPanel = new Panel();
    personPanel.getStyle().setPosition(RELATIVE);
    personPanel.getStyle().setMinHeight(80F);

    Label personIcon = new Label("\uF009", 10, 10, 60, 60);
    personIcon.getStyle().setFont(FontRegistry.MATERIAL_DESIGN_ICONS);
    personIcon.getStyle().setFontSize(60F);
    personIcon.getStyle().setBorder(new SimpleLineBorder(ColorConstants.black(), 1));
    personPanel.add(personIcon);

    Label username = new Label("John Doe", 80, 20, 100, 20);
    username.getStyle().setFontSize(20f);
    username.getStyle().setFont(FontRegistry.ROBOTO_BOLD);
    personPanel.add(username);

    Label position = new Label("Alien Protector", 80, 40, 100, 20);
    position.getStyle().setFontSize(14f);
    personPanel.add(position);
    return personPanel;
  }

  private static void setWidgetStyles(Widget widget) {

    float fontSize = 20F;

    widget.getResizeButton().setIntersector(RESIZE_BUTTON_INTERSECTOR);
    widget.getResizeButton().getStyle().getBackground().setIcon(null);
    widget.getResizeButton().getTextState().setText("\uF45D");
    widget.getResizeButton().getStyle().setTextColor(ColorConstants.white());
    widget.getResizeButton().getStyle().setFont(FontRegistry.MATERIAL_DESIGN_ICONS);
    widget.getResizeButton().getStyle().setFontSize(fontSize);
    widget.getResizeButton().getStyle().setWidth(fontSize);
    widget.getResizeButton().getStyle().setHeight(fontSize);

    widget.getContainer().getStyle().getBackground().setColor(ColorUtil.rgba(0x42, 0x42, 0x42, 1));
  }
}
