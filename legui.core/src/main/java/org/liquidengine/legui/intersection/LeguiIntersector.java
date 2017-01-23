package org.liquidengine.legui.intersection;

import org.joml.Vector2f;

/**
 * Interface of intersector.
 */
public interface LeguiIntersector {
    /**
     * Used to determine if point in shape or not in shape
     *
     * @param point point to test
     * @param shape shape to test
     * @return true if point belong to shape
     */
    boolean intersects(Vector2f point, Vector2f[] shape);

    /**
     * Used to determine if shapes intersects each other or not
     *
     * @param firstShape  first shape to test
     * @param secondShape second shape to test
     * @return true if shapes intersects each other
     */
    boolean intersects(Vector2f[] firstShape, Vector2f[] secondShape);
}
