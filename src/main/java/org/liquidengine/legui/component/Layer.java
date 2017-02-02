package org.liquidengine.legui.component;

import org.apache.commons.collections4.list.SetUniqueList;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Layer<T extends Component> extends ComponentContainer<T> {
    protected List<T> components = SetUniqueList.setUniqueList(new CopyOnWriteArrayList<>());

    protected Layer bottomLayer;
    protected Layer topLayer;
    protected Frame display;

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

}
