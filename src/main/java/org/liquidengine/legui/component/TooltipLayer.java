package org.liquidengine.legui.component;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class TooltipLayer extends Layer<Tooltip> {
    @Override
    public boolean isEventPassable() {
        return true;
    }

    @Override
    public boolean isEventReceivable() {
        return false;
    }
}
