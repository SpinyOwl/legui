package org.liquidengine.legui.icon;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.image.Image;

/**
 * Created by ShchAlexander on 3/10/2017.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageIcon imageIcon = (ImageIcon) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(getImage(), imageIcon.getImage())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(getImage())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("image", image)
            .toString();
    }
}
