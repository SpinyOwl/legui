package org.liquidengine.legui.component.optional.align;

import java.io.Serializable;

/**
 * Vertical alignment states.
 */
public enum VerticalAlign implements Serializable {
    /**
     * Content should be aligned to top border of container.
     */
    TOP(0),
    /**
     * Content should be aligned to middle of container.
     */
    MIDDLE(1),
    /**
     * Content should be aligned to bottom border of container.
     */
    BOTTOM(2),
    /**
     * Content should be aligned to baseline of container.
     */
    BASELINE(1);

    /**
     * Used to keep alignment index.
     */
    public final int index;

    /**
     * Used to initialize align with index. Index could be used to calculate some different offsets or something else by renderers and etc.
     *
     * @param index index to set.
     */
    VerticalAlign(int index) {
        this.index = index;
    }
}