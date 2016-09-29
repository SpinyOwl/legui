package org.liquidengine.legui.listener.component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class LeguiComponentListenerHolder {
    private List<MouseDragEventListener> mouseDragEventListeners = new CopyOnWriteArrayList<>();

    public boolean addMouseDragEventListener(MouseDragEventListener listener) {
        return mouseDragEventListeners.add(listener);
    }

    public boolean removeMouseDragEventListener(MouseDragEventListener listener) {
        return mouseDragEventListeners.remove(listener);
    }

    public List<MouseDragEventListener> getMouseDragEventListeners() {
        return mouseDragEventListeners;
    }
}
