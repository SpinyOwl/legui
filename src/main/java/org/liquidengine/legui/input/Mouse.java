package org.liquidengine.legui.input;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public class Mouse {
    private static Vector2f cursorPosition     = new Vector2f();
    private static Vector2f cursorPositionPrev = new Vector2f();

    public static Vector2f getCursorPosition() {
        return new Vector2f(cursorPosition);
    }

    public static void setCursorPosition(Vector2f cursorPosition) {
        Mouse.cursorPosition = cursorPosition != null ? cursorPosition : new Vector2f();
    }

    public static Vector2f getCursorPositionPrev() {
        return new Vector2f(cursorPositionPrev);
    }

    public static void setCursorPositionPrev(Vector2f cursorPositionPrev) {
        Mouse.cursorPositionPrev = cursorPositionPrev != null ? cursorPositionPrev : new Vector2f();
    }

    /**
     * Created by Aliaksandr_Shcherbin on 2/9/2017.
     */
    public enum MouseButton {
        /**
         * LEFT BUTTON
         */
        MOUSE_BUTTON_1(GLFW_MOUSE_BUTTON_1),     //= 0,
        /**
         * RIGHT BUTTON
         */
        MOUSE_BUTTON_2(GLFW_MOUSE_BUTTON_2),     //= 1,
        /**
         * MIDDLE BUTTON
         */
        MOUSE_BUTTON_3(GLFW_MOUSE_BUTTON_3),     //= 2,
        MOUSE_BUTTON_4(GLFW_MOUSE_BUTTON_4),     //= 3,
        MOUSE_BUTTON_5(GLFW_MOUSE_BUTTON_5),     //= 4,
        MOUSE_BUTTON_6(GLFW_MOUSE_BUTTON_6),     //= 5,
        MOUSE_BUTTON_7(GLFW_MOUSE_BUTTON_7),     //= 6,
        MOUSE_BUTTON_8(GLFW_MOUSE_BUTTON_8),     //= 7,
        MOUSE_BUTTON_UNKNOWN(-1),     //= UNKNOWN,

        ;
        public static final MouseButton MOUSE_BUTTON_LEFT   = MOUSE_BUTTON_1;
        public static final MouseButton MOUSE_BUTTON_RIGHT  = MOUSE_BUTTON_2;
        public static final MouseButton MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
        private int      code;
        private boolean  pressed;
        private Vector2f pressPosition;
        private Vector2f releasePosition;

        MouseButton(int code) {
            this.code = code;
        }

        public static MouseButton getByCode(int code) {
            for (MouseButton mouseButton : MouseButton.values()) {
                if (mouseButton.getCode() == code) return mouseButton;
            }
            return MOUSE_BUTTON_UNKNOWN;
        }

        public int getCode() {
            return code;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public Vector2f getReleasePosition() {
            return releasePosition;
        }

        public void setReleasePosition(Vector2f releasePosition) {
            this.releasePosition = releasePosition;
        }

        public Vector2f getPressPosition() {
            return pressPosition;
        }

        public void setPressPosition(Vector2f pressPosition) {
            this.pressPosition = pressPosition;
        }
    }
}
