package org.liquidengine.legui.component;

import org.joml.Vector2f;

/**
 * Created by Shcherbin Alexander on 8/26/2016.
 */
public interface Viewport {

    /**
     * Returns visible size of viewport
     *
     * @return visible size
     */
    Vector2f getVisibleSize();

    /**
     * Returns whole size of viewport
     *
     * @return whole size
     */
    Vector2f getWholeSize();

    /**
     * Returns offset of visible size relative to top left corner
     * @return visible size
     */
    Vector2f getCurrentPosition();

    /**
     * Sets offset of visible size
     * @param inViewportPosition offset
     */
    void moveTo(Vector2f inViewportPosition);
}
