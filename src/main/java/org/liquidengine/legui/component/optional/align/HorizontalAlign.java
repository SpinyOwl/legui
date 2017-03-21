package org.liquidengine.legui.component.optional.align;

import java.io.Serializable;

/**
 * Alignment states.
 */
public enum HorizontalAlign implements Serializable {
    /**
     * Content should be aligned to left border.
     */
    LEFT(0),
    /**
     * Content should be aligned to center.
     */
    CENTER(1),
    /**
     * Content should be aligned to right border.
     */
    RIGHT(2);

    /**
     * Used to keep alignment index.
     */
    public final int index;

    /**
     * Used to initialize align with index.
     * Index could be used to calculate some different offsets or something else by renderers and etc.
     *
     * @param index index to set.
     */
    HorizontalAlign(int index) {
        this.index = index;
    }
}