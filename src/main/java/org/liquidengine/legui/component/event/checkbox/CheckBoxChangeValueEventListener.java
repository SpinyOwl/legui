package org.liquidengine.legui.component.event.checkbox;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface CheckBoxChangeValueEventListener extends EventListener<CheckBoxChangeValueEvent> {

    void process(CheckBoxChangeValueEvent event);
}
