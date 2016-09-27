package org.liquidengine.legui.component;

import org.joml.Vector2f;

/**
 * Created by Shcherbin Alexander on 9/27/2016.
 */
public interface ContainerHolder {
    ComponentContainer getContainer();
    Vector2f getContainerPosition();
}
