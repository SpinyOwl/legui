package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Controller;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class CursorEnterEvent extends AbstractEvent {
    public static final int ENTER = 0;
    public static final int EXIT  = 1;
    private final int      enter;
    private final Vector2f negate;
    private final Vector2f cursorPosition;

    public CursorEnterEvent(Controller controller, int enter, Vector2f negate, Vector2f cursorPosition) {
        super(controller);
        this.enter = enter;
        this.negate = negate;
        this.cursorPosition = cursorPosition;
    }

    public int getEnter() {
        return enter;
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
                .append("enter", enter)
                .append("negate", negate)
                .append("cursorPosition", cursorPosition)
                .toString();
    }
}
