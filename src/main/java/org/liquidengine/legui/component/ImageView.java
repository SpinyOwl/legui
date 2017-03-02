package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.image.Image;

/**
 * This class represents image view component.
 */
public class ImageView extends Controller {
    /**
     * BufferedImage (or image data)
     */
    private Image image;

    /**
     * Creates image view with specified image.
     *
     * @param image image to set.
     */
    public ImageView(Image image) {
        this.image = image;
        initialize();
    }

    /**
     * Default constructor of image view.
     */
    public ImageView() {
        initialize();
    }

    private void initialize() {
    }

    /**
     * Returns image of image view.
     *
     * @return image of image view or null.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Used to set image.
     *
     * @param image image to set.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ImageView imageView = (ImageView) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(image, imageView.image)
                .isEquals();
    }


    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(image)
                .toHashCode();
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("image", image)
                .toString();
    }
}
