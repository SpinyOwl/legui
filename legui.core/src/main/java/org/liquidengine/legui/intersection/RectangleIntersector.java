package org.liquidengine.legui.intersection;

import org.joml.Vector2f;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Simple implementation of intersector
 */
public class RectangleIntersector implements LeguiIntersector {
    @Override
    public boolean intersects(Vector2f point, Vector2f[] shape) {
        throw new NotImplementedException();
    }

    @Override
    public boolean intersects(Vector2f[] firstShape, Vector2f[] secondShape) {
        throw new NotImplementedException();
    }
}
