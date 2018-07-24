package org.liquidengine.legui.listener.impl;

import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

public abstract class AbstractMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle {@link MouseClickEvent}
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        switch (event.getAction()) {
            case CLICK:
                onClick(event);
                break;
            case PRESS:
                onPress(event);
                break;
            case RELEASE:
                onRelease(event);
                break;
        }
    }

    protected void onClick(MouseClickEvent event) {
        // should be implemented in child classes
    }

    protected void onPress(MouseClickEvent event) {
        // should be implemented in child classes
    }

    protected void onRelease(MouseClickEvent event) {
        // should be implemented in child classes
    }
}
