package org.liquidengine.legui.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSLASH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_CAPS_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_EQUAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F10;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F12;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F13;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F14;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F15;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F16;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F17;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F18;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F19;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F20;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F21;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F22;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F23;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F24;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F25;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DIVIDE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_EQUAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_MULTIPLY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_MENU;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_MINUS;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_NUM_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAUSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PRINT_SCREEN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SCROLL_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SEMICOLON;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SLASH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_WORLD_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_WORLD_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

/**
 * This class used to allow use last states of keyboard keys.
 */
public final class Keyboard {


    public enum Key {
        KEY_UNKNOWN(GLFW_KEY_UNKNOWN),
        KEY_SPACE(GLFW_KEY_SPACE),
        KEY_APOSTROPHE(GLFW_KEY_APOSTROPHE),
        KEY_COMMA(GLFW_KEY_COMMA),
        KEY_MINUS(GLFW_KEY_MINUS),
        KEY_PERIOD(GLFW_KEY_PERIOD),
        KEY_SLASH(GLFW_KEY_SLASH),
        KEY_0(GLFW_KEY_0),
        KEY_1(GLFW_KEY_1),
        KEY_2(GLFW_KEY_2),
        KEY_3(GLFW_KEY_3),
        KEY_4(GLFW_KEY_4),
        KEY_5(GLFW_KEY_5),
        KEY_6(GLFW_KEY_6),
        KEY_7(GLFW_KEY_7),
        KEY_8(GLFW_KEY_8),
        KEY_9(GLFW_KEY_9),
        KEY_SEMICOLON(GLFW_KEY_SEMICOLON),
        KEY_EQUAL(GLFW_KEY_EQUAL),
        KEY_A(GLFW_KEY_A),
        KEY_B(GLFW_KEY_B),
        KEY_C(GLFW_KEY_C),
        KEY_D(GLFW_KEY_D),
        KEY_E(GLFW_KEY_E),
        KEY_F(GLFW_KEY_F),
        KEY_G(GLFW_KEY_G),
        KEY_H(GLFW_KEY_H),
        KEY_I(GLFW_KEY_I),
        KEY_J(GLFW_KEY_J),
        KEY_K(GLFW_KEY_K),
        KEY_L(GLFW_KEY_L),
        KEY_M(GLFW_KEY_M),
        KEY_N(GLFW_KEY_N),
        KEY_O(GLFW_KEY_O),
        KEY_P(GLFW_KEY_P),
        KEY_Q(GLFW_KEY_Q),
        KEY_R(GLFW_KEY_R),
        KEY_S(GLFW_KEY_S),
        KEY_T(GLFW_KEY_T),
        KEY_U(GLFW_KEY_U),
        KEY_V(GLFW_KEY_V),
        KEY_W(GLFW_KEY_W),
        KEY_X(GLFW_KEY_X),
        KEY_Y(GLFW_KEY_Y),
        KEY_Z(GLFW_KEY_Z),
        KEY_LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET),
        KEY_BACKSLASH(GLFW_KEY_BACKSLASH),
        KEY_RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),
        KEY_GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT),
        KEY_WORLD_1(GLFW_KEY_WORLD_1),
        KEY_WORLD_2(GLFW_KEY_WORLD_2),
        KEY_ESCAPE(GLFW_KEY_ESCAPE),
        KEY_ENTER(GLFW_KEY_ENTER),
        KEY_TAB(GLFW_KEY_TAB),
        KEY_BACKSPACE(GLFW_KEY_BACKSPACE),
        KEY_INSERT(GLFW_KEY_INSERT),
        KEY_DELETE(GLFW_KEY_DELETE),
        KEY_RIGHT(GLFW_KEY_RIGHT),
        KEY_LEFT(GLFW_KEY_LEFT),
        KEY_DOWN(GLFW_KEY_DOWN),
        KEY_UP(GLFW_KEY_UP),
        KEY_PAGE_UP(GLFW_KEY_PAGE_UP),
        KEY_PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
        KEY_HOME(GLFW_KEY_HOME),
        KEY_END(GLFW_KEY_END),
        KEY_CAPS_LOCK(GLFW_KEY_CAPS_LOCK),
        KEY_SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),
        KEY_NUM_LOCK(GLFW_KEY_NUM_LOCK),
        KEY_PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),
        KEY_PAUSE(GLFW_KEY_PAUSE),
        KEY_F1(GLFW_KEY_F1),
        KEY_F2(GLFW_KEY_F2),
        KEY_F3(GLFW_KEY_F3),
        KEY_F4(GLFW_KEY_F4),
        KEY_F5(GLFW_KEY_F5),
        KEY_F6(GLFW_KEY_F6),
        KEY_F7(GLFW_KEY_F7),
        KEY_F8(GLFW_KEY_F8),
        KEY_F9(GLFW_KEY_F9),
        KEY_F10(GLFW_KEY_F10),
        KEY_F11(GLFW_KEY_F11),
        KEY_F12(GLFW_KEY_F12),
        KEY_F13(GLFW_KEY_F13),
        KEY_F14(GLFW_KEY_F14),
        KEY_F15(GLFW_KEY_F15),
        KEY_F16(GLFW_KEY_F16),
        KEY_F17(GLFW_KEY_F17),
        KEY_F18(GLFW_KEY_F18),
        KEY_F19(GLFW_KEY_F19),
        KEY_F20(GLFW_KEY_F20),
        KEY_F21(GLFW_KEY_F21),
        KEY_F22(GLFW_KEY_F22),
        KEY_F23(GLFW_KEY_F23),
        KEY_F24(GLFW_KEY_F24),
        KEY_F25(GLFW_KEY_F25),
        KEY_KP_0(GLFW_KEY_KP_0),
        KEY_KP_1(GLFW_KEY_KP_1),
        KEY_KP_2(GLFW_KEY_KP_2),
        KEY_KP_3(GLFW_KEY_KP_3),
        KEY_KP_4(GLFW_KEY_KP_4),
        KEY_KP_5(GLFW_KEY_KP_5),
        KEY_KP_6(GLFW_KEY_KP_6),
        KEY_KP_7(GLFW_KEY_KP_7),
        KEY_KP_8(GLFW_KEY_KP_8),
        KEY_KP_9(GLFW_KEY_KP_9),
        KEY_KP_DECIMAL(GLFW_KEY_KP_DECIMAL),
        KEY_KP_DIVIDE(GLFW_KEY_KP_DIVIDE),
        KEY_KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
        KEY_KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT),
        KEY_KP_ADD(GLFW_KEY_KP_ADD),
        KEY_KP_ENTER(GLFW_KEY_KP_ENTER),
        KEY_KP_EQUAL(GLFW_KEY_KP_EQUAL),
        KEY_LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
        KEY_LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL),
        KEY_LEFT_ALT(GLFW_KEY_LEFT_ALT),
        KEY_LEFT_SUPER(GLFW_KEY_LEFT_SUPER),
        KEY_RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
        KEY_RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
        KEY_RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
        KEY_RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER),
        KEY_MENU(GLFW_KEY_MENU);

        private final int code;
        private boolean pressed;

        Key(int code) {
            this.code = code;
        }

        public static Key getByCode(int code) {
            for (Key key : values()) {
                if (key.getCode() == code) {
                    return key;
                }
            }
            return null;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public int getCode() {
            return code;
        }
    }
}
