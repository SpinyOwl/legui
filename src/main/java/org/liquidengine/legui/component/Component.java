package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.listener.ListenerMap;
import org.liquidengine.legui.util.ColorConstants;

public abstract class Component {

    protected ComponentContainer parent;
    protected ListenerMap listenerMap     = new ListenerMap();
    private   Vector2f    position        = new Vector2f();
    private   Vector2f    size            = new Vector2f();
    private   Vector4f    backgroundColor = ColorConstants.transparent();
    private   boolean     visible         = true;

    public ComponentContainer getParent() {
        return parent;
    }

    public void setParent(ComponentContainer parent) {
        this.parent = parent;
    }

    public ListenerMap getListenerMap() {
        return listenerMap;
    }

    public void setListenerMap(ListenerMap listenerMap) {
        this.listenerMap = listenerMap;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        if (position != null) {
            this.position = position;
        } else {
            this.position = new Vector2f();
        }
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        if (size != null) {
            this.size = size;
        } else {
            this.size = new Vector2f();
        }
    }

    public Vector2f getScreenPosition() {
        Vector2f screenPos = new Vector2f(this.position);
        for (ComponentContainer parent = this.getParent(); parent != null; parent = parent.getParent()) {
            screenPos.add(parent.getPosition());
        }
        return screenPos;
    }

    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isVisible() {
        return visible;
    }
}
