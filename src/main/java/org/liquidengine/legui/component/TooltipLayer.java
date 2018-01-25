package org.liquidengine.legui.component;

/**
 * Created by ShchAlexander on 2/9/2017.
 */
public class TooltipLayer extends Layer<Tooltip> {

    public TooltipLayer() {

    }

    @Override
    public boolean isEventPassable() {
        return true;
    }

    @Override
    public boolean isEventReceivable() {
        return false;
    }
}
