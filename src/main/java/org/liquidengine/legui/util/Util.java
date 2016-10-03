package org.liquidengine.legui.util;

import org.joml.Vector2f;
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
        return calculateParentOffset(gui).add(gui.getPosition());
    }

    public static Vector2f calculateParentOffset(Component component) {
        Vector2f offset = new Vector2f();
        Component parent = component.getParent();
        while (parent != null) {
            offset.add(parent.getPosition());
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

}
