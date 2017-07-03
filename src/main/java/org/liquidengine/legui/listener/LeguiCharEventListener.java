package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiCharEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/14/2017.
 */
public interface LeguiCharEventListener extends LeguiEventListener<LeguiCharEvent> {

    /**
     * Used to handle {@link LeguiCharEvent}.
     *
     * @param event event to handle.
     */
    void process(LeguiCharEvent event);
}
