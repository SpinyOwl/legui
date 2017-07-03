package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/14/2017.
 */
public class CharEvent<T extends Component> extends Event<T> {

    private final int codepoint;

    public CharEvent(T component, Context context, int codepoint) {
        super(component, context);
        this.codepoint = codepoint;
    }

    public int getCodepoint() {
        return codepoint;
    }
}
