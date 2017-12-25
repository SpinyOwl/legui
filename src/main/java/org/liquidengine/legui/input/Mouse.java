package org.liquidengine.legui.input;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_4;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_5;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_6;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_7;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_8;

import org.joml.Vector2f;

/**
 * This class used to hold mouse state.
 * <p>
 * Created by ShchAlexander on 2/9/2017.
 */
public class Mouse {

    private static Vector2f cursorPosition = new Vector2f();
    private static Vector2f cursorPositionPrev = new Vector2f();

    /**
     * Gets cursor position.
     *
     * @return the cursor position
     */
    public static Vector2f getCursorPosition() {
        return new Vector2f(cursorPosition);
    }

    /**
     * Sets cursor position.
     *
     * @param cursorPosition the cursor position
     */
    public static void setCursorPosition(Vector2f cursorPosition) {
        Mouse.cursorPosition = cursorPosition != null ? cursorPosition : new Vector2f();
    }

    /**
     * Gets previous cursor position.
     *
     * @return the previous cursor position.
     */
    public static Vector2f getCursorPositionPrev() {
        return new Vector2f(cursorPositionPrev);
    }

    /**
     * Sets previous cursor position.
     *
     * @param cursorPositionPrev the previous cursor position.
     */
    public static void setCursorPositionPrev(Vector2f cursorPositionPrev) {
        Mouse.cursorPositionPrev = cursorPositionPrev != null ? cursorPositionPrev : new Vector2f();
    }

    /**
     * Created by ShchAlexander on 2/9/2017.
     */
    public enum MouseButton {
        /**
         * Mouse button 1.
         */
        MOUSE_BUTTON_1(GLFW_MOUSE_BUTTON_1),     //= 0,
        /**
         * Mouse button 2.
         */
        MOUSE_BUTTON_2(GLFW_MOUSE_BUTTON_2),     //= 1,
        /**
         * Mouse button 3.
         */
        MOUSE_BUTTON_3(GLFW_MOUSE_BUTTON_3),     //= 2,
        /**
         * Mouse button 4.
         */
        MOUSE_BUTTON_4(GLFW_MOUSE_BUTTON_4),     //= 3,
        /**
         * Mouse button 5.
         */
        MOUSE_BUTTON_5(GLFW_MOUSE_BUTTON_5),     //= 4,
        /**
         * Mouse button 6.
         */
        MOUSE_BUTTON_6(GLFW_MOUSE_BUTTON_6),     //= 5,
        /**
         * Mouse button 7.
         */
        MOUSE_BUTTON_7(GLFW_MOUSE_BUTTON_7),     //= 6,
        /**
         * Mouse button 8.
         */
        MOUSE_BUTTON_8(GLFW_MOUSE_BUTTON_8),     //= 7,
        /**
         * Unknown mouse button.
         */
        MOUSE_BUTTON_UNKNOWN(-1),     //= UNKNOWN,

        ;
        /**
         * The constant MOUSE_BUTTON_LEFT.
         */
        public static final MouseButton MOUSE_BUTTON_LEFT = MOUSE_BUTTON_1;
        /**
         * The constant MOUSE_BUTTON_RIGHT.
         */
        public static final MouseButton MOUSE_BUTTON_RIGHT = MOUSE_BUTTON_2;
        /**
         * The constant MOUSE_BUTTON_MIDDLE.
         */
        public static final MouseButton MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
        private int code;
        private boolean pressed;
        private Vector2f pressPosition;
        private Vector2f releasePosition;

        MouseButton(int code) {
            this.code = code;
        }

        /**
         * Gets by code.
         *
         * @param code the code
         *
         * @return the by code
         */
        public static MouseButton getByCode(int code) {
            for (MouseButton mouseButton : MouseButton.values()) {
                if (mouseButton.getCode() == code) {
                    return mouseButton;
                }
            }
            return MOUSE_BUTTON_UNKNOWN;
        }

        /**
         * Gets code.
         *
         * @return the code
         */
        public int getCode() {
            return code;
        }

        /**
         * Is pressed boolean.
         *
         * @return the boolean
         */
        public boolean isPressed() {
            return pressed;
        }

        /**
         * Sets pressed.
         *
         * @param pressed the pressed
         */
        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        /**
         * Gets release position.
         *
         * @return the release position
         */
        public Vector2f getReleasePosition() {
            return releasePosition;
        }

        /**
         * Sets release position.
         *
         * @param releasePosition the release position
         */
        public void setReleasePosition(Vector2f releasePosition) {
            this.releasePosition = releasePosition;
        }

        /**
         * Gets press position.
         *
         * @return the press position
         */
        public Vector2f getPressPosition() {
            return pressPosition;
        }

        /**
         * Sets press position.
         *
         * @param pressPosition the press position
         */
        public void setPressPosition(Vector2f pressPosition) {
            this.pressPosition = pressPosition;
        }
    }
}
