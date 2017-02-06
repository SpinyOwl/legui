package org.liquidengine.legui.component;

import org.liquidengine.legui.component.optional.TextState;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public class Tooltip extends Component {
    private TextState textState;

    public Tooltip() {
        initialize("");
    }

    public Tooltip(String tooltip) {
        initialize(tooltip);
    }

    private void initialize(String text) {
        this.textState = new TextState(text);
    }

    public TextState getTextState() {
        return textState;
    }
}
