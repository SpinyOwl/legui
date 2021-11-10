package com.spinyowl.legui.intersection;

import com.spinyowl.legui.component.Component;
import org.joml.Vector2f;


public abstract class Intersector {

  public abstract boolean intersects(Component component, Vector2f vector2f);
}
