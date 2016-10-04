package org.liquidengine.legui.listener.component;

import org.joml.Vector2f;

/**
 * Created by Alexander on 05.10.2016.
 */
public interface CursorEnterListener {
    void onCursorOut(Vector2f mousePosition);

    void onCursorIn(Vector2f mousePosition);
}
