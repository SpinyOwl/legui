package com.spinyowl.legui.system.context;

import com.spinyowl.cbchain.IChainCharCallback;
import com.spinyowl.cbchain.IChainCharModsCallback;
import com.spinyowl.cbchain.IChainCursorEnterCallback;
import com.spinyowl.cbchain.IChainCursorPosCallback;
import com.spinyowl.cbchain.IChainDropCallback;
import com.spinyowl.cbchain.IChainFramebufferSizeCallback;
import com.spinyowl.cbchain.IChainKeyCallback;
import com.spinyowl.cbchain.IChainMouseButtonCallback;
import com.spinyowl.cbchain.IChainScrollCallback;
import com.spinyowl.cbchain.IChainWindowCloseCallback;
import com.spinyowl.cbchain.IChainWindowFocusCallback;
import com.spinyowl.cbchain.IChainWindowIconifyCallback;
import com.spinyowl.cbchain.IChainWindowPosCallback;
import com.spinyowl.cbchain.IChainWindowRefreshCallback;
import com.spinyowl.cbchain.IChainWindowSizeCallback;
import com.spinyowl.cbchain.impl.ChainCharCallback;
import com.spinyowl.cbchain.impl.ChainCharModsCallback;
import com.spinyowl.cbchain.impl.ChainCursorEnterCallback;
import com.spinyowl.cbchain.impl.ChainCursorPosCallback;
import com.spinyowl.cbchain.impl.ChainDropCallback;
import com.spinyowl.cbchain.impl.ChainFramebufferSizeCallback;
import com.spinyowl.cbchain.impl.ChainKeyCallback;
import com.spinyowl.cbchain.impl.ChainMouseButtonCallback;
import com.spinyowl.cbchain.impl.ChainScrollCallback;
import com.spinyowl.cbchain.impl.ChainWindowCloseCallback;
import com.spinyowl.cbchain.impl.ChainWindowFocusCallback;
import com.spinyowl.cbchain.impl.ChainWindowIconifyCallback;
import com.spinyowl.cbchain.impl.ChainWindowPosCallback;
import com.spinyowl.cbchain.impl.ChainWindowRefreshCallback;
import com.spinyowl.cbchain.impl.ChainWindowSizeCallback;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

/**
 * Used to hold all callbacks for specified window.
 */
public class DefaultCallbackKeeper implements CallbackKeeper {

  private IChainCharCallback chainCharCallback = new ChainCharCallback();
  private IChainDropCallback chainDropCallback = new ChainDropCallback();
  private IChainKeyCallback chainKeyCallback = new ChainKeyCallback();
  private IChainScrollCallback chainScrollCallback = new ChainScrollCallback();
  private IChainCharModsCallback chainCharModsCallback = new ChainCharModsCallback();
  private IChainCursorEnterCallback chainCursorEnterCallback = new ChainCursorEnterCallback();
  private IChainFramebufferSizeCallback chainFramebufferSizeCallback = new ChainFramebufferSizeCallback();
  private IChainMouseButtonCallback chainMouseButtonCallback = new ChainMouseButtonCallback();
  private IChainCursorPosCallback chainCursorPosCallback = new ChainCursorPosCallback();
  private IChainWindowCloseCallback chainWindowCloseCallback = new ChainWindowCloseCallback();
  private IChainWindowFocusCallback chainWindowFocusCallback = new ChainWindowFocusCallback();
  private IChainWindowIconifyCallback chainWindowIconifyCallback = new ChainWindowIconifyCallback();
  private IChainWindowPosCallback chainWindowPosCallback = new ChainWindowPosCallback();
  private IChainWindowRefreshCallback chainWindowRefreshCallback = new ChainWindowRefreshCallback();
  private IChainWindowSizeCallback chainWindowSizeCallback = new ChainWindowSizeCallback();

  /**
   * Used to bind callbacks to OpenGL window.
   *
   * @param window window to bind.
   */
  public void registerCallbacks(long window) {
    CallbackKeeper.registerCallbacks(window, this);
  }

  /**
   * Returns chain callback used to keep {@link GLFWCharCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainCharCallback getChainCharCallback() {
    return chainCharCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWCharCallbackI} instances.
   *
   * @param chainCharCallback callback to set.
   */
  public void setChainCharCallback(IChainCharCallback chainCharCallback) {
    this.chainCharCallback = chainCharCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWDropCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainDropCallback getChainDropCallback() {
    return chainDropCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWDropCallbackI} instances.
   *
   * @param chainDropCallback callback to set.
   */
  public void setChainDropCallback(IChainDropCallback chainDropCallback) {
    this.chainDropCallback = chainDropCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWKeyCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainKeyCallback getChainKeyCallback() {
    return chainKeyCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWKeyCallbackI} instances.
   *
   * @param chainKeyCallback callback to set.
   */
  public void setChainKeyCallback(IChainKeyCallback chainKeyCallback) {
    this.chainKeyCallback = chainKeyCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWScrollCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainScrollCallback getChainScrollCallback() {
    return chainScrollCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWScrollCallbackI} instances.
   *
   * @param chainScrollCallback callback to set.
   */
  public void setChainScrollCallback(IChainScrollCallback chainScrollCallback) {
    this.chainScrollCallback = chainScrollCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWCharModsCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainCharModsCallback getChainCharModsCallback() {
    return chainCharModsCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWCharModsCallbackI} instances.
   *
   * @param chainCharModsCallback callback to set.
   */
  public void setChainCharModsCallback(IChainCharModsCallback chainCharModsCallback) {
    this.chainCharModsCallback = chainCharModsCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWCursorEnterCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainCursorEnterCallback getChainCursorEnterCallback() {
    return chainCursorEnterCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWCursorEnterCallbackI} instances.
   *
   * @param chainCursorEnterCallback callback to set.
   */
  public void setChainCursorEnterCallback(IChainCursorEnterCallback chainCursorEnterCallback) {
    this.chainCursorEnterCallback = chainCursorEnterCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWFramebufferSizeCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainFramebufferSizeCallback getChainFramebufferSizeCallback() {
    return chainFramebufferSizeCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWFramebufferSizeCallbackI} instances.
   *
   * @param chainFramebufferSizeCallback callback to set.
   */
  public void setChainFramebufferSizeCallback(
      IChainFramebufferSizeCallback chainFramebufferSizeCallback) {
    this.chainFramebufferSizeCallback = chainFramebufferSizeCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWMouseButtonCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainMouseButtonCallback getChainMouseButtonCallback() {
    return chainMouseButtonCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWMouseButtonCallbackI} instances.
   *
   * @param chainMouseButtonCallback callback to set.
   */
  public void setChainMouseButtonCallback(IChainMouseButtonCallback chainMouseButtonCallback) {
    this.chainMouseButtonCallback = chainMouseButtonCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWCursorPosCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainCursorPosCallback getChainCursorPosCallback() {
    return chainCursorPosCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWCursorPosCallbackI} instances.
   *
   * @param chainCursorPosCallback callback to set.
   */
  public void setChainCursorPosCallback(IChainCursorPosCallback chainCursorPosCallback) {
    this.chainCursorPosCallback = chainCursorPosCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWWindowCloseCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainWindowCloseCallback getChainWindowCloseCallback() {
    return chainWindowCloseCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWWindowCloseCallbackI} instances.
   *
   * @param chainWindowCloseCallback callback to set.
   */
  public void setChainWindowCloseCallback(IChainWindowCloseCallback chainWindowCloseCallback) {
    this.chainWindowCloseCallback = chainWindowCloseCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWWindowFocusCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainWindowFocusCallback getChainWindowFocusCallback() {
    return chainWindowFocusCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWWindowFocusCallbackI} instances.
   *
   * @param chainWindowFocusCallback callback to set.
   */
  public void setChainWindowFocusCallback(IChainWindowFocusCallback chainWindowFocusCallback) {
    this.chainWindowFocusCallback = chainWindowFocusCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWWindowIconifyCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainWindowIconifyCallback getChainWindowIconifyCallback() {
    return chainWindowIconifyCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWWindowIconifyCallbackI} instances.
   *
   * @param chainWindowIconifyCallback callback to set.
   */
  public void setChainWindowIconifyCallback(
      IChainWindowIconifyCallback chainWindowIconifyCallback) {
    this.chainWindowIconifyCallback = chainWindowIconifyCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWWindowPosCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainWindowPosCallback getChainWindowPosCallback() {
    return chainWindowPosCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWWindowPosCallbackI} instances.
   *
   * @param chainWindowPosCallback callback to set.
   */
  public void setChainWindowPosCallback(IChainWindowPosCallback chainWindowPosCallback) {
    this.chainWindowPosCallback = chainWindowPosCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWWindowRefreshCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainWindowRefreshCallback getChainWindowRefreshCallback() {
    return chainWindowRefreshCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWWindowRefreshCallbackI} instances.
   *
   * @param chainWindowRefreshCallback callback to set.
   */
  public void setChainWindowRefreshCallback(
      IChainWindowRefreshCallback chainWindowRefreshCallback) {
    this.chainWindowRefreshCallback = chainWindowRefreshCallback;
  }

  /**
   * Returns chain callback used to keep {@link GLFWWindowSizeCallbackI} instances.
   *
   * @return chain char callback.
   */
  @Override
  public IChainWindowSizeCallback getChainWindowSizeCallback() {
    return chainWindowSizeCallback;
  }

  /**
   * Sets chain callback used to keep {@link GLFWWindowSizeCallbackI} instances.
   *
   * @param chainWindowSizeCallback callback to set.
   */
  public void setChainWindowSizeCallback(IChainWindowSizeCallback chainWindowSizeCallback) {
    this.chainWindowSizeCallback = chainWindowSizeCallback;
  }
}
