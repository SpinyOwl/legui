package org.liquidengine.legui.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by Aliaksandr_Shcherbin on 2/10/2017.
 */
public class FocusEvent<T extends Component> extends AbstractEvent<T> {

    private final boolean   focused;
    private final Component nextFocus;

    public FocusEvent(T component, Context context, Component nextFocus, boolean focused) {
        super(component, context);
        this.focused = focused;
        this.nextFocus = nextFocus;
    }

    public boolean isFocused() {
        return focused;
    }

    public Component getNextFocus() {
        return nextFocus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("focused", focused)
                .append("nextFocus", nextFocus)
                .toString();
    }
}
