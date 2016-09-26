package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.ProgressBarChangeEvent;

/**
 * Created by Shcherbin Alexander on 9/26/2016.
 */
public interface ProgressBarChangeEventListener {
    void beforeUpdate(ProgressBarChangeEvent event);

    void afterUpdate(ProgressBarChangeEvent event);
}
