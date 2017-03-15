package org.liquidengine.legui.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.image.Image;

/**
 * Created by Aliaksandr_Shcherbin on 3/10/2017.
 */
public class ImageIcon extends Icon {
    private Image image;

    public ImageIcon() {
    }

    public ImageIcon(Vector2f size, Image image) {
        super(size);
        this.image = image;
    }

    public ImageIcon(Image image) {
        super(new Vector2f(image.getWidth(), image.getHeight()));
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
