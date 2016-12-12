package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class ImageView extends Component {
    private String path;
//    private ByteBuffer imageData;

    public ImageView(String path/*, ByteBuffer imageData*/) {
        this.path = path;
//        this.imageData = imageData;
        initialize();
    }
    public ImageView() {
        initialize();
    }

    private void initialize() {
        border = new SimpleRectangleLineBorder(ColorConstants.darkGray(), 1);
    }

//    public ByteBuffer getImageData() {
//        return imageData;
//    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ImageView imageView = (ImageView) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(path, imageView.path)
//                .append(imageData, imageView.imageData)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(path)
//                .append(imageData)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("path", path)
//                .append("imageData", imageData)
                .toString();
    }

    public void setPath(String path) {
        this.path = path;
    }
}
