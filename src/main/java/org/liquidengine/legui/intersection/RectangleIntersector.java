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
        Vector2f pos = component.getScreenPosition();
        float    x   = pos.x;
        float    y   = pos.y;
        float    w   = component.getSize().x;
        float    h   = component.getSize().y;
        float verticies[] = {
                x, y,
                x + w, y,
                x + w, y + h,
                x, y + h
        };
        int                  start[]     = {0};
        int                  count       = 4;
        PolygonsIntersection intersector = new PolygonsIntersection(verticies, start, count);
        return intersector.testPoint(point.x, point.y);
    }

}
