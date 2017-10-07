package org.liquidengine.legui.component.event.textinput;

import org.liquidengine.legui.listener.EventListener;

/**
 * @author Aliaksandr_Shcherbin.
 */
public interface TextInputContentChangeEventListener extends EventListener<TextInputContentChangeEvent> {

    /**
     * Used to handle {@link TextInputContentChangeEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(TextInputContentChangeEvent event);
}
