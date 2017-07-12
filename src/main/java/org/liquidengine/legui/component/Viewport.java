package org.liquidengine.legui.component;

import java.io.Serializable;

public interface Viewport extends Serializable {

    /**
     * Used to update viewport content position.
     */
    void updateViewport();
}