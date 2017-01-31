package org.liquidengine.legui.component;

/**
 * Created by ShchAlexander on 01.02.2017.
 */
public abstract class Controller extends Component {
    protected Tooltip tooltip;

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }
}
