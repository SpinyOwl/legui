package org.liquidengine.legui.system.layout;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.event.component.ChangePositionEvent;
import org.liquidengine.legui.component.event.component.ChangeSizeEvent;
import org.liquidengine.legui.system.context.Context;

/**
 * Layout interface.
 *
 * @author ShchAlexander.
 */
public interface Layout {

    /**
     * Used to lay out child components for parent component.
     *
     * Do not generate {@link ChangePositionEvent} and {@link ChangeSizeEvent} events.
     *
     * @param parent component to lay out.
     */
    void layout(Component parent);

    /**
     * Used to lay out child components for parent component.
     *
     * Generates {@link ChangePositionEvent} and {@link ChangeSizeEvent} events.
     *
     * @param parent component to lay out.
     * @param frame component frame (for event generation).
     * @param context context (used for event generation).
     */
    void layout(Component parent, Frame frame, Context context);

}
