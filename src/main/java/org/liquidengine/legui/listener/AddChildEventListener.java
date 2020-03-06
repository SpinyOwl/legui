package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.AddChildEvent;

public interface AddChildEventListener extends EventListener<AddChildEvent> {

    @Override
    void process(AddChildEvent event);

}
