package org.liquidengine.legui.component.event.textarea;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.system.context.Context;

/**
 * Generated when any key combination is pressed.
 *
 * @author ShchAlexander.
 */
public class TextAreaFieldUpdateEvent extends Event<TextAreaField> {

    public TextAreaFieldUpdateEvent(TextAreaField component, Context context, Frame frame) {
        super(component, context, frame);
    }

}
