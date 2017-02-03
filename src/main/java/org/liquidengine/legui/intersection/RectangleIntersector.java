package org.liquidengine.legui.intersection;

import org.joml.PolygonsIntersection;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class RectangleIntersector implements Intersector {
    @Override
    public boolean intersects(Component component, Vector2f point) {
        boolean  intersects = false;
        Vector2f p          = component.getScreenPosition();
        Vector2f s          = component.getSize();
        //@formatter:off
        float[] points = new float[]{
                p.x,       p.y,
                p.x + s.x, p.y,
                p.x + s.x, p.y + s.y,
                p.x,       p.y + s.y
        };
        //@formatter:on
        int                  start[]      = {0};
        int                  count        = 4;
        PolygonsIntersection intersection = new PolygonsIntersection(points, start, count);
        intersects = intersection.testPoint(point.x, point.y);
        return intersects;
    }
}
