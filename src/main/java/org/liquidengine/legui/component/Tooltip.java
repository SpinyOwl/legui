package org.liquidengine.legui.component;

import org.liquidengine.legui.component.optional.TextState;

/**
 * Created by Aliaksandr_Shcherbin on 1/27/2017.
 */
public class Tooltip extends Component {
    protected TextState textState;

    public Tooltip() {
        textState = new TextState();
        size.set(100, 20);
        cornerRadius = 3f;
    }

    @Override
    public String getTooltip() {
        return null;
    }

    @Override
    public void setTooltip(String tooltip) {
    }

    @Override
    public Tooltip getTooltipComponent() {
        return this;
    }

    public TextState getTextState() {
        return textState;
    }
}
