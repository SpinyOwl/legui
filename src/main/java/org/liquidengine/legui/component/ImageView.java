package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class ImageView extends Component {
    private Image image;

    public ImageView(Image image) {
        this.image = image;
        initialize();
    }

    public ImageView() {
        initialize();
    }

    private void initialize() {
        border = new SimpleRectangleLineBorder(ColorConstants.darkGray(), 1);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(image)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("image", image)
                .toString();
    }
}
