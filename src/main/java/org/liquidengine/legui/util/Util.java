package org.liquidengine.legui.util;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;

import java.nio.ByteBuffer;

import static org.lwjgl.system.MemoryUtil.memUTF8;

/**
 * Created by Shcherbin Alexander on 6/16/2016.
 */
public final class Util {
    private Util() {
    }

    public static boolean pointIntersectRect(Vector2f position, Vector2f size, Vector2f point) {
        if (point.x >= position.x && point.x <= position.x + size.x) {
            if (point.y >= position.y && point.y <= position.y + size.y) {
                return true;
            }
        }
        return false;
    }

    public static Vector2f calculatePosition(Component gui) {
        Vector2f pos = new Vector2f();
        Component current = gui;
        Component parent = current.getParent();
        while (parent != null) {
            pos.add(current.getPosition());
            current = parent;
            parent = current.getParent();
        }
        pos.add(current.getPosition());
        return pos;
    }

    public static ByteBuffer utf8(int cp) {
        return memUTF8(new String(Character.toChars(cp)), true);
    }

    public static String cpToStr(int cp) {
        return new String(Character.toChars(cp));
    }

    public static ByteBuffer cpToUTF8(int cp) {
        return memUTF8(new String(Character.toChars(cp)), true);
    }

    public static Vector4f negativeColor(Vector4f color) {
        Vector4f dest = new Vector4f(1).sub(color);
        dest.w = color.w;
        return dest;
    }

    public static void negativeColor(Vector4f color, Vector4f dest) {
        dest.zero().set(1).sub(color);
        dest.w = color.w;
    }


    public static Vector4f negativeColorRGB(Vector4f color) {
        Vector4f dest = new Vector4f(1).sub(color);
        dest.w = 1;
        return dest;
    }

    public static void negativeColorRGB(Vector4f color, Vector4f dest) {
        dest.zero().set(1).sub(color);
        dest.w = 1;
    }

    public static Vector4f half(Vector4f color){
        return new Vector4f(color).div(2);
    }

}
