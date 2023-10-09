package com.spinyowl.legui.event;

import static org.lwjgl.glfw.GLFW.GLFW_MOD_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CAPS_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_NUM_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SUPER;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.input.Mouse.MouseButton;
import com.spinyowl.legui.system.context.Context;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joml.Vector2f;

@Getter
@EqualsAndHashCode
@ToString
public class MouseClickEvent<T extends Component> extends Event<T> {

  private final MouseClickAction action;
  private final Mouse.MouseButton button;
  /** Cursor position in button coordinates. */
  private final Vector2f position;
  /** Cursor position in frame coordinates. */
  private final Vector2f absolutePosition;
  private final int mods;

  public MouseClickEvent(T component, Context context, Frame frame, MouseClickAction action,
      MouseButton button, Vector2f position,
      Vector2f absolutePosition, int mods) {
    super(component, context, frame);
    this.action = action;
    this.button = button;
    this.position = position;
    this.absolutePosition = absolutePosition;
    this.mods = mods;
  }

  public boolean isModShift() {
    return (mods & GLFW_MOD_SHIFT) != 0;
  }

  public boolean isModControl() {
    return (mods & GLFW_MOD_CONTROL) != 0;
  }

  public boolean isModAlt() {
    return (mods & GLFW_MOD_ALT) != 0;
  }

  public boolean isModSuper() {
    return (mods & GLFW_MOD_SUPER) != 0;
  }

  public boolean isModCapsLock() {
    return (mods & GLFW_MOD_CAPS_LOCK) != 0;
  }

  public boolean isModNumLock() {
    return (mods & GLFW_MOD_NUM_LOCK) != 0;
  }

  public enum MouseClickAction {
    PRESS,
    CLICK,
    RELEASE,
  }
}
