package org.liquidengine.legui.component.optional.align;

import java.io.Serializable;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public enum HorizontalAlign implements Serializable {
    LEFT(0),
    CENTER(1),
    RIGHT(2);

    public final int index;

    HorizontalAlign(int index) {
        this.index = index;
    }
}