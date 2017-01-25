package org.liquidengine.legui.component;

import org.liquidengine.legui.listener.ListenerMap;

public abstract class Component {

    protected ComponentContainer parent;
    private ListenerMap listenerMap = new ListenerMap();

    public ComponentContainer getParent() {
        return parent;
    }

    public ListenerMap getListenerMap() {
        return listenerMap;
    }
}
