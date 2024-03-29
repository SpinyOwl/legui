package com.spinyowl.legui.util;

import com.spinyowl.legui.component.Component;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;

/**
 * Utility class which store some common methods..
 */
public final class Utilites {

  private Utilites() {
    // private constructor
  }

  /**
   * Used to determine if component is visible in parent components.
   *
   * @param component component to check.
   * @return true if component is visible in all chain of parent components.
   */
  public static boolean visibleInParents(Component component) {
    List<Component> parentList = new ArrayList<>();
    for (Component parent = component.getParent(); parent != null; parent = parent.getParent()) {
      parentList.add(parent);
    }

    if (parentList.size() > 0) {
      Vector2f pos = new Vector2f(0, 0);
      Vector2f rect = new Vector2f(0, 0);
      Vector2f absolutePosition = component.getAbsolutePosition();

      Vector2f cSize = component.getSize();
      Vector2f cPos = component.getPosition();

      float lx = absolutePosition.x;
      float rx = absolutePosition.x + cSize.x;
      float ty = absolutePosition.y;
      float by = absolutePosition.y + cSize.y;

      // check top parent

      if (cPos.x > component.getParent().getSize().x ||
          cPos.x + cSize.x < 0 ||
          cPos.y > component.getParent().getSize().y ||
          cPos.y + cSize.y < 0
      ) {
        return false;
      }
      if (parentList.size() != 1) {
        // check from bottom parent to top parent
        for (int i = parentList.size() - 1; i >= 1; i--) {
          Component parent = parentList.get(i);
          pos.add(parent.getPosition());
          rect.set(pos).add(parent.getSize());

          if (lx > rect.x || rx < pos.x || ty > rect.y || by < pos.y) {
            return false;
          }
        }
      }
    }
    return true;
  }
}
