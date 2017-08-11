package org.liquidengine.legui.event;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/13/2017.
 */
public class KeyEvent<T extends Component> extends Event<T> {

    private final int action;
    private final int key;
    private final int mods;
    private final int scancode;

    public KeyEvent(T component, Context context, Frame frame, int action, int key, int mods, int scancode) {
        super(component, context, frame);
        this.action = action;
        this.key = key;
        this.mods = mods;
        this.scancode = scancode;
    }

    public int getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }

    public int getMods() {
        return mods;
    }

    public int getScancode() {
        return scancode;
    }
}
