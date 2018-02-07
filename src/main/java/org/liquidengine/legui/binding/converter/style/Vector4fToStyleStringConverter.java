package org.liquidengine.legui.binding.converter.style;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector4f;
import org.liquidengine.legui.binding.converter.AbstractClassConverter;

/**
 * Created by ShchAlexander on 27.12.2017.
 */
public class Vector4fToStyleStringConverter extends AbstractClassConverter<Vector4f> {

    /**
     * Used to convert java object to string representation.
     *
     * @param o object to convert.
     * @return string representation of object.
     */
    @Override
    public String convertFromJava(Vector4f o) {
        return new StringBuilder()
            .append(o.x).append(", ")
            .append(o.y).append(", ")
            .append(o.z).append(", ")
            .append(o.w).toString();
    }

    /**
     * Used to convert string representation to java object.
     *
     * @param o string representation of object to convert.
     * @return object of string representation.
     */
    @Override
    public Vector4f convertToJava(String o) {
        String[] split = o.split(",", -1);
        List<String> nonEmpty = new ArrayList<>();
        for (String s : split) {
            String trim = s.trim();
            if (!trim.isEmpty()) {
                nonEmpty.add(trim);
            }
        }

        int size = nonEmpty.size();
        if (size == 0) {
            return new Vector4f(0, 0, 0, 0);
        }
        if (size == 1) {
            int v = getValue(nonEmpty.get(0));
            return new Vector4f(v, v, v, v);
        } else if (size == 2) {
            int f = getValue(nonEmpty.get(0));
            int s = getValue(nonEmpty.get(1));
            return new Vector4f(f, s, f, s);
        } else if (size == 3) {
            int f = getValue(nonEmpty.get(0));
            int s = getValue(nonEmpty.get(1));
            int t = getValue(nonEmpty.get(2));
            return new Vector4f(f, s, t, s);
        } else {
            int _1 = getValue(nonEmpty.get(0));
            int _2 = getValue(nonEmpty.get(1));
            int _3 = getValue(nonEmpty.get(2));
            int _4 = getValue(nonEmpty.get(3));
            return new Vector4f(_1, _2, _3, _4);
        }
    }

    private int getValue(String s) {
        int v = 0;
        try {
            v = Integer.valueOf(s);
        } catch (NumberFormatException ignored) {
        }
        return v;
    }
}
