package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 2/14/2017.
 */
public class CharEvent<T extends Component> extends Event<T> {

    private final int codepoint;

    public CharEvent(T component, Context context, Frame frame, int codepoint) {
        super(component, context, frame);
        this.codepoint = codepoint;
    }

    public int getCodepoint() {
        return codepoint;
    }
}
