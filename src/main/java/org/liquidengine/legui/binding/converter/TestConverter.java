package org.liquidengine.legui.binding.converter;

import org.joml.Vector2f;
import org.liquidengine.legui.binding.model.AbstractClassConverter;

/**
 * Created by ShchAlexander on 02.12.2017.
 */
public class TestConverter extends AbstractClassConverter<Vector2f> {

    @Override
    public String convertFromJava(Vector2f o) {
        return o.x + " " + o.y;
    }

    @Override
    public Vector2f convertToJava(String o) {
        Vector2f vector2f = new Vector2f();
        if (o != null) {
            String[] split = o.split(" ");
            vector2f.x = Float.valueOf(split[0]);
            vector2f.y = Float.valueOf(split[1]);
        }
        return vector2f;
    }
}
