package org.liquidengine.legui.component.optional.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.image.Image;

/**
 * Created by Aliaksandr_Shcherbin on 3/10/2017.
 */
public class ImageIcon implements Icon {
    private Vector2f size;
    private Image    image;

    public ImageIcon(Vector2f size, Image image) {
        this.size = size;
        this.image = image;
    }

    @Override
    public Vector2f getSize() {
        return size;
    }

    @Override
    public void setSize(Vector2f size) {
        this.size = size;
    }
}
