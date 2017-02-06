package org.liquidengine.legui.component;

import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;

public class Layer<T extends Component> {
    private Frame frame;
    private boolean eventPassable = true;
    protected LayerContainer<T> container = new LayerContainer<T>();


    public Layer() {
        initialize();
    }

    private void initialize() {
        container.getListenerMap().addListener(WindowSizeEvent.class, (WindowSizeEventListener) event -> container.getSize().set(event.getWidth(), event.getHeight()));
    }

    public Frame getFrame() {
        return frame;
    }

    protected void setFrame(Frame frame){
        this.frame = frame;
    }

    public boolean isEventPassable() {
        return eventPassable;
    }

    public void setEventPassable(boolean eventPassable) {
        this.eventPassable = eventPassable;
    }

    public LayerContainer<T> getContainer() {
        return container;
    }
}
