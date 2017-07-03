package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.LeguiContext;

/**
 * Created by Aliaksandr_Shcherbin on 2/14/2017.
 */
public class LeguiCharEvent<T extends Component> extends LeguiEvent<T> {

    private final int codepoint;

    public LeguiCharEvent(T component, LeguiContext context, int codepoint) {
        super(component, context);
        this.codepoint = codepoint;
    }

    public int getCodepoint() {
        return codepoint;
    }
}
