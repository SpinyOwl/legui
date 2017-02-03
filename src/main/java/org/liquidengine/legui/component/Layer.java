package org.liquidengine.legui.component;

import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;

public class Layer<T extends Component> extends ComponentContainer<T> {
    protected Layer bottomLayer;
    protected Layer topLayer;
    protected Frame display;
    protected boolean eventPassable = true;

    public Layer() {
        initialize();
    }

    private void initialize() {
        listenerMap.addListener(WindowSizeEvent.class, (WindowSizeEventListener) event -> getSize().set(event.getWidth(), event.getHeight()));
    }

    public Frame getDisplay() {
        return display;
    }

    public Layer getBottomLayer() {
        return bottomLayer;
    }

    public Layer getTopLayer() {
        return topLayer;
    }

    public boolean isEventPassable() {
        return eventPassable;
    }

    public void setEventPassable(boolean eventPassable) {
        this.eventPassable = eventPassable;
    }
}
