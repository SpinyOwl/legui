package org.liquidengine.legui.component.optional.align;

import java.io.Serializable;

/**
 * Created by Shcherbin Alexander on 5/17/2016.
 */
public enum VerticalAlign implements Serializable {
    TOP(0),
    MIDDLE(1),
    BOTTOM(2),
    BASELINE(1);

    public final int index;

    VerticalAlign(int i) {
        index = i;
    }
}
