package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.theme.Themes;

/**
 * This class represents image view component.
 */
public class ImageView extends Component {

    /**
     * BufferedImage (or image data).
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

    /**
     * This method used to initialize image view component.
     */
    private void initialize() {
        Themes.getDefaultTheme().getThemeManager().getComponentTheme(ImageView.class).applyAll(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageView imageView = (ImageView) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(image, imageView.image)
            .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(image)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("image", image)
            .toString();
    }
}
