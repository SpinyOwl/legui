package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.intersection.LeguiIntersector;
import org.liquidengine.legui.intersection.RectangleIntersector;
import org.liquidengine.legui.listener.LeguiEventListenerMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base of any ui component.
 * Created by Alexander on 13.01.2017.
 */
public abstract class Component {
    protected Map<String, Object>   metadata    = new ConcurrentHashMap<>();
    protected LeguiEventListenerMap listenerMap = new LeguiEventListenerMap();
    protected LeguiIntersector      intersector = new RectangleIntersector();

    protected Border border = null;
    protected Vector2f position;
    protected Vector2f size;

    /**
     * Returns border
     *
     * @return border
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Used to set border
     *
     * @param border border
     */
    public void setBorder(Border border) {
        this.border = border;
    }

    /**
     * Returns listener map
     *
     * @return listener map
     */
    public LeguiEventListenerMap getListenerMap() {
        return listenerMap;
    }

    /**
     * Used to set listener map
     *
     * @param listenerMap listener map
     */
    public void setListenerMap(LeguiEventListenerMap listenerMap) {
        this.listenerMap = listenerMap;
    }

    /**
     * Returns intersector
     *
     * @return intersector
     */
    public LeguiIntersector getIntersector() {
        return intersector;
    }

    /**
     * Used to set intersector
     *
     * @param intersector intersector
     */
    public void setIntersector(LeguiIntersector intersector) {
        this.intersector = intersector;
    }

    /**
     * Returns metadata map
     *
     * @return metadata map
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Used to set metadata map
     *
     * @param metadata metadata map
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
