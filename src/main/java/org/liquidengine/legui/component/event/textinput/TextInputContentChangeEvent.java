package org.liquidengine.legui.component.event.textinput;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class TextInputContentChangeEvent<T extends TextInput> extends Event<T> {

    public TextInputContentChangeEvent(T component, Context context, Frame frame, String oldValue, String newValue) {
        super(component, context, frame);
    }
}
