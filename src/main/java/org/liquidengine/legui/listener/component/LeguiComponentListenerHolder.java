package org.liquidengine.legui.listener.component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class LeguiComponentListenerHolder {
    private List<MouseDragEventListener> mouseDragEventListeners = new CopyOnWriteArrayList<>();
    private List<CursorEnterListener> cursorEnterListeners = new CopyOnWriteArrayList<>();

    public boolean addMouseDragEventListener(MouseDragEventListener listener) {
        return mouseDragEventListeners.add(listener);
    }

    public boolean removeMouseDragEventListener(MouseDragEventListener listener) {
        return mouseDragEventListeners.remove(listener);
    }

    public List<MouseDragEventListener> getMouseDragEventListeners() {
        return mouseDragEventListeners;
    }

    public boolean addCursorEnterListener(CursorEnterListener listener) {
        return cursorEnterListeners.add(listener);
    }

    public boolean removeCursorEnterListener(CursorEnterListener listener) {
        return cursorEnterListeners.remove(listener);
    }


    public List<CursorEnterListener> getCursorEnterListeners() {
        return cursorEnterListeners;
    }
}
