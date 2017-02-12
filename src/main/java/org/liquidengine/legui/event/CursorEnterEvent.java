package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class CursorEnterEvent extends AbstractEvent {
    private final boolean  entered;
    private final Vector2f negate;
    private final Vector2f cursorPosition;

    public CursorEnterEvent(Component controller, Frame frame, boolean entered, Vector2f negate, Vector2f cursorPosition) {
        super(controller, frame);
        this.entered = entered;
        this.negate = negate;
        this.cursorPosition = cursorPosition;
    }

    public boolean isEntered() {
        return entered;
    }

    public Vector2f getNegate() {
        return negate;
    }

    public Vector2f getCursorPosition() {
        return cursorPosition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("entered", entered)
                .append("negate", negate)
                .append("cursorPosition", cursorPosition)
                .toString();
    }
}
