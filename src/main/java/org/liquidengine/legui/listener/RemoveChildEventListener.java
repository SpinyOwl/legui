package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.RemoveChildEvent;

public interface RemoveChildEventListener extends EventListener<RemoveChildEvent> {

    @Override
    void process(RemoveChildEvent event);

}
