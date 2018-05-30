package org.liquidengine.legui.image;

/**
 * Created by ShchAlexander on 30.05.2018.
 */
public class FBOImage extends Image {

    private int textureId;
    private int width;
    private int height;

    public FBOImage(int textureId, int width, int height) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns image width.
     *
     * @return image width.
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Returns image height.
     *
     * @return image height.
     */
    @Override
    public int getHeight() {
        return height;
    }

    public int getTextureId() {
        return textureId;
    }
}
