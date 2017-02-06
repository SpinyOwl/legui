package org.liquidengine.legui.component.optional.align;

import java.io.Serializable;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
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