package com.spinyowl.legui.system.context;

import static org.lwjgl.glfw.GLFW.GLFW_ICONIFIED;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorContentScale;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowFrameSize;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.config.Configuration;
import com.spinyowl.legui.event.FocusEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.event.SystemWindowScaleEvent;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;

public class Context {

  public static final Vector2f NO_SCALE = new Vector2f(1F, 1F);

  static {
    Configuration.getInstance();
  }

  private final long glfwWindow;
  private final SystemEventProcessor systemEventProcessor;

  private final Map<String, Object> contextData = new ConcurrentHashMap<>();
  private Vector2f windowPosition;
  private Vector2i windowSize;
  private Vector2i framebufferSize;
  private Vector2f scale = NO_SCALE;
  private transient float pixelRatio;
  private Component mouseTargetGui;
  private Component focusedGui;
  private boolean debugEnabled;
  private boolean iconified;

  private final AtomicLong currentMonitor = new AtomicLong();

  // technical fields used during update
  int[] windowWidth = {0}, windowHeight = {0};
  int[] frameBufferWidth = {0}, frameBufferHeight = {0};
  int[] frameLeft = {0}, frameTop = {0}, frameRight = {0}, frameBottom = {0};
  int[] xpos = {0}, ypos = {0};
  int[] monitorX = {0}, monitorY = {0};
  float[] scaleX = {0}, scaleY = {0};

  /**
   * Instantiates a new Context.
   *
   * @param glfwWindow the glfw window
   * @param systemEventProcessor
   */
  public Context(long glfwWindow, SystemEventProcessor systemEventProcessor) {
    this.glfwWindow = glfwWindow;
    this.systemEventProcessor = systemEventProcessor;
  }

//  public Context(long glfwWindow) {
//    this(glfwWindow, null);
//  }

  public static void setFocusedGui(Component toGainFocus, Context context, Frame frame) {
    Component current = context == null ? null : context.focusedGui;
    if (current != null) {
      current.setFocused(false);
      EventProcessorProvider.getInstance()
          .pushEvent(new FocusEvent<>(current, context, frame, toGainFocus, false));
    }
    if (toGainFocus != null) {
      toGainFocus.setFocused(true);
      EventProcessorProvider.getInstance()
          .pushEvent(new FocusEvent<>(toGainFocus, context, frame, toGainFocus, true));
      if (context != null) {
        context.setFocusedGui(toGainFocus);
      }
    }
  }

  /**
   * Is debug enabled boolean.
   *
   * @return the boolean
   */
  public boolean isDebugEnabled() {
    return debugEnabled;
  }

  /**
   * Sets debug enabled.
   *
   * @param debugEnabled the debug enabled
   */
  public void setDebugEnabled(boolean debugEnabled) {
    this.debugEnabled = debugEnabled;
  }

  /**
   * Gets context data.
   *
   * @return the context data
   */
  public Map<String, Object> getContextData() {
    return contextData;
  }

  /** Update glfw window. */
  public void updateGlfwWindow() {
    glfwGetWindowSize(glfwWindow, windowWidth, windowHeight);
    glfwGetFramebufferSize(glfwWindow, frameBufferWidth, frameBufferHeight);
    glfwGetWindowPos(glfwWindow, xpos, ypos);
    glfwGetWindowFrameSize(glfwWindow, frameLeft, frameTop, frameRight, frameBottom);

    Vector2f winPosition = new Vector2f(xpos[0], ypos[0]);
    Vector2i winSize = new Vector2i(windowWidth[0], windowHeight[0]);

    updateCurrentMonitorAndScale(winPosition, winSize);

    setWindowSize(winSize);
    setFramebufferSize(new Vector2i(frameBufferWidth[0], frameBufferHeight[0]));
    setPixelRatio((float) frameBufferWidth[0] / (float) windowWidth[0]);
    setWindowPosition(winPosition);
    setIconified(glfwGetWindowAttrib(glfwWindow, GLFW_ICONIFIED) == GLFW_TRUE);
  }

  private void updateCurrentMonitorAndScale(Vector2f windowPosition, Vector2i windowSize) {
    PointerBuffer monitors = glfwGetMonitors();
    int monitorsCount = monitors.remaining();
    float maxSurface = 0;

    for (int i = 0; i < monitorsCount; i++) {
      long monitor = monitors.get(i);
      if (monitor == 0) {
        continue;
      }

      glfwGetMonitorPos(monitor, monitorX, monitorY);
      Vector2f monitorPosition = new Vector2f(monitorX[0], monitorY[0]);

      GLFWVidMode videoMode = glfwGetVideoMode(monitor);
      Vector2f monitorSize = new Vector2f(videoMode.width(), videoMode.height());

      float surface =
          getIntersectionSurface(
              new Vector2f(windowPosition).sub(frameLeft[0], frameTop[0]),
              new Vector2i(windowSize)
                  .add(frameLeft[0], frameTop[0])
                  .add(frameRight[0], frameBottom[0]),
              monitorPosition,
              monitorSize);

      if (maxSurface < surface) {
        maxSurface = surface;
        currentMonitor.set(monitor);
        glfwGetMonitorContentScale(monitor, scaleX, scaleY);

        Vector2f newScale = new Vector2f(scaleX[0], scaleY[0]);
        if (systemEventProcessor != null && this.scale.distance(newScale) > 0.1f) {
          systemEventProcessor.pushEvent(
              new SystemWindowScaleEvent(
                  glfwWindow, newScale.x, newScale.y, this.scale.x, this.scale.y));
        }
        setScale(newScale);
      }
    }
  }

  private static float getIntersectionSurface(
      Vector2f windowPosition,
      Vector2i windowSize,
      Vector2f monitorPosition,
      Vector2f monitorSize) {
    float surface = 0f;
    if (windowPosition.x < monitorPosition.x + monitorSize.x
        && windowPosition.x + windowSize.x > monitorPosition.x
        && windowPosition.y < monitorPosition.y + monitorSize.y
        && windowPosition.y + windowSize.y > monitorPosition.y) {
      float x1 = Math.max(windowPosition.x, monitorPosition.x);
      float y1 = Math.max(windowPosition.y, monitorPosition.y);
      float x2 = Math.min(windowPosition.x + windowSize.x, monitorPosition.x + monitorSize.x);
      float y2 = Math.min(windowPosition.y + windowSize.y, monitorPosition.y + monitorSize.y);
      surface = (x2 - x1) * (y2 - y1);
    }
    return surface;
  }

  /**
   * Update.
   *
   * @param targetWidth the target width
   * @param targetHeight the target height
   * @param framebufferWidth the framebuffer width
   * @param framebufferHeight the framebuffer height
   * @param targetPosX the target pos x
   * @param targetPosY the target pos y
   * @param iconified the iconified
   */
  public void update(
      int targetWidth,
      int targetHeight,
      int framebufferWidth,
      int framebufferHeight,
      int targetPosX,
      int targetPosY,
      boolean iconified) {
    setWindowSize(new Vector2i(targetWidth, targetHeight));
    setFramebufferSize(new Vector2i(framebufferWidth, framebufferHeight));
    setPixelRatio((float) framebufferWidth / (float) targetWidth);
    setWindowPosition(new Vector2f(targetPosX, targetPosY));
    setIconified(iconified);
  }

  /**
   * Update context.
   *
   * @param windowSize window size.
   * @param framebufferSize framebuffer size.
   * @param pixelRatio pixel ratio.
   * @param windowPosition window position.
   * @param iconified iconified.
   */
  public void update(
      Vector2i windowSize,
      Vector2i framebufferSize,
      float pixelRatio,
      Vector2f windowPosition,
      boolean iconified) {
    setWindowSize(windowSize);
    setFramebufferSize(framebufferSize);
    setPixelRatio(pixelRatio);
    setWindowPosition(windowPosition);
    setIconified(iconified);
  }

  /**
   * Gets pixel ratio.
   *
   * @return the pixel ratio
   */
  public float getPixelRatio() {
    return pixelRatio;
  }

  /**
   * Sets pixel ratio.
   *
   * @param pixelRatio the pixel ratio
   */
  public void setPixelRatio(float pixelRatio) {
    this.pixelRatio = pixelRatio;
  }

  /**
   * Gets glfw window.
   *
   * @return the glfw window
   */
  public long getGlfwWindow() {
    return glfwWindow;
  }

  /**
   * Gets window position.
   *
   * @return the window position
   */
  public Vector2f getWindowPosition() {
    return windowPosition;
  }

  /**
   * Sets window position.
   *
   * @param windowPosition the window position
   */
  public void setWindowPosition(Vector2f windowPosition) {
    this.windowPosition = windowPosition;
  }

  /**
   * Gets window size.
   *
   * @return the window size
   */
  public Vector2i getWindowSize() {
    return windowSize;
  }

  /**
   * Sets window size.
   *
   * @param windowSize the window size
   */
  public void setWindowSize(Vector2i windowSize) {
    this.windowSize = windowSize;
  }

  /**
   * Gets framebuffer size.
   *
   * @return the framebuffer size
   */
  public Vector2i getFramebufferSize() {
    return framebufferSize;
  }

  /**
   * Sets framebuffer size.
   *
   * @param framebufferSize the framebuffer size
   */
  public void setFramebufferSize(Vector2i framebufferSize) {
    this.framebufferSize = framebufferSize;
  }

  /**
   * Gets focused gui.
   *
   * @return the focused gui
   */
  public Component getFocusedGui() {
    return focusedGui;
  }

  /**
   * Sets focused gui.
   *
   * @param focusedGui the focused gui
   */
  public void setFocusedGui(Component focusedGui) {
    this.focusedGui = focusedGui;
  }

  /**
   * Returns current mouse target component.
   *
   * @return current mouse target component.
   */
  public Component getMouseTargetGui() {
    return mouseTargetGui;
  }

  /**
   * Used to update current mouse target component.
   *
   * @param mouseTargetGui new mouse target component.
   */
  public void setMouseTargetGui(Component mouseTargetGui) {
    this.mouseTargetGui = mouseTargetGui;
  }

  /**
   * Returns window iconified state.
   *
   * @return window iconified state.
   */
  public boolean isIconified() {
    return iconified;
  }

  /**
   * Used to update state of window (in case of window iconified).
   *
   * @param iconified window state.
   */
  public void setIconified(boolean iconified) {
    this.iconified = iconified;
  }

  /**
   * Returns window scale.
   *
   * @return window scale.
   */
  public Vector2f getScale() {
    return scale;
  }

  /**
   * Used to update window scale.
   *
   * @param scale window scale.
   */
  public void setScale(Vector2f scale) {
    this.scale = scale;
  }

  public long getGlfwCurrentMonitor() {
    return currentMonitor.get();
  }
}
