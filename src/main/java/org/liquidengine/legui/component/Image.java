package org.liquidengine.legui.component;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.util.ColorConstants;
import org.liquidengine.legui.util.IOUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class Image extends Component {
    private String path;
    private ByteBuffer imageData;


    public Image(String path, ByteBuffer imageData) {
        this.path = path;
        this.imageData = imageData;
        initialize();
    }

    public static Image createImage(String path) {
        Image image = null;
        try {
            ByteBuffer data = IOUtil.ioResourceToByteBuffer(path, 32 * 1024);
            image = new Image(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void initialize() {
        border = new SimpleLineBorder(this, ColorConstants.darkGray(), 1);
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public String getPath() {
        return path;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
