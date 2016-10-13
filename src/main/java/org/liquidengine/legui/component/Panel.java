package org.liquidengine.legui.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.border.SimpleLineBorder;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class Panel extends ComponentContainer {

    public Panel() {
        initialize();
    }

    public Panel(float x, float y, float width, float height) {
        super(x, y, width, height);
        initialize();
    }

    public Panel(Vector2f position, Vector2f size) {
        super(position, size);
        initialize();
    }

    private void initialize() {
        border = new SimpleLineBorder();
    }

}
