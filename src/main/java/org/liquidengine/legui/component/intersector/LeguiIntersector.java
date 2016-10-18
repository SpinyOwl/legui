package org.liquidengine.legui.component.intersector;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public interface LeguiIntersector extends Serializable {
    boolean intersects(Component component, Vector2f point);
}
