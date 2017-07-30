package org.liquidengine.legui.component.optional.align;

import java.io.Serializable;

/**
 * Horizontal alignment states.
 */
public enum HorizontalAlign implements Serializable {
    /**
     * Content should be aligned to left border of container.
     */
    LEFT(0),
    /**
     * Content should be aligned to center of container.
     */
    CENTER(1),
    /**
     * Content should be aligned to right border of container.
     */
    RIGHT(2);

    /**
     * Used to keep alignment index.
     */
    public final int index;

    /**
     * Used to initialize align with index. Index could be used to calculate some different offsets or something else by renderers and etc.
     *
     * @param index index to set.
     */
    HorizontalAlign(int index) {
        this.index = index;
    }
}