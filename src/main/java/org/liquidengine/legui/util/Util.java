package org.liquidengine.legui.util;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ContainerHolder;

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
        return calculateParentOffset(gui).add(gui.getPosition());
    }

    public static Vector2f calculateParentOffset(Component component) {
        Vector2f offset = new Vector2f();
        Component parent = component.getParent();
        while (parent != null) {
            offset.add(parent.getPosition());
            if (parent instanceof ContainerHolder) {
                offset.add(((ContainerHolder) parent).getContainerPosition());
            }
            parent = parent.getParent();
        }
        return offset;
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

    public static Vector4f half(Vector4f color) {
        return new Vector4f(color).div(2);
    }

    public static Vector4f blackOrWhite(Vector4f color) {
        return blackOrWhite(color, new Vector4f(1));
    }

    public static Vector4f blackOrWhite(Vector4f color, Vector4f targetColor) {
        if ((color.x * 0.299f + color.y * 0.587f + color.z * 0.114f) > 170f / 255f) {
            targetColor.x = 0;
            targetColor.y = 0;
            targetColor.z = 0;
        } else {
            targetColor.x = 1;
            targetColor.y = 1;
            targetColor.z = 1;
        }
        return targetColor;
    }

}
