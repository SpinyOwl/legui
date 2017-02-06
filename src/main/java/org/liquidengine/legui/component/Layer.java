package org.liquidengine.legui.component;

import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;

public class Layer<T extends Component> extends Container<T> {
    private Frame frame;
    private boolean eventPassable = true;

    public Layer() {
        initialize();
    }

    private void initialize() {
        getListenerMap().addListener(WindowSizeEvent.class, (WindowSizeEventListener) event -> getSize().set(event.getWidth(), event.getHeight()));
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
}
