package org.liquidengine.legui.cursor;

public class Cursor {
    public static final Cursor ARROW = new Cursor();
    public static final Cursor H_RESIZE = new Cursor();
    public static final Cursor V_RESIZE = new Cursor();
    public static final Cursor CROSSHAIR = new Cursor();
    public static final Cursor HAND = new Cursor();
    public static final Cursor IBEAM = new Cursor();

    private final long imageReference;
    private final int xHot;
    private final int yHot;

    private Cursor() {
        xHot = 0;
        yHot = 0;
        imageReference = 0;
    }

    /**
     * @param imageReference the desired cursor image
     * @param xHot           the desired x-coordinate, in pixels, of the cursor hotspot
     * @param yHot           the desired y-coordinate, in pixels, of the cursor hotspot
     */
    public Cursor(long imageReference, int xHot, int yHot) {
        this.imageReference = imageReference;
        this.xHot = xHot;
        this.yHot = yHot;
    }

    public long getImageReference() {
        return imageReference;
    }

    public int getxHot() {
        return xHot;
    }

    public int getyHot() {
        return yHot;
    }
}
