package org.liquidengine.legui.system;

import org.lwjgl.glfw.GLFW;

/**
 * @author ShchAlexander.
 */
public abstract class Clipboard {

    /**
     * Clipboard access instance.
     */
    private static Clipboard instance = new ClipboardImpl();

    /**
     * Returns clipboard access instance.
     *
     * @return clipboard access instance.
     */
    public static Clipboard getInstance() {
        return instance;
    }

    /**
     * Used to set clipboard access instance.
     *
     * @param clipboard clipboard access instance.
     */
    public static void setInstance(Clipboard clipboard) {
        instance = clipboard;
    }

    /**
     * Used to get string from clipboard.
     *
     * @return string from clipboard.
     */
    public abstract String getClipboardString();

    /**
     * Used to set string to clipboard.
     *
     * @param string string to set to clipboard.
     */
    public abstract void setClipboardString(String string);

    /**
     * Default clipboard implementation.
     */
    private static class ClipboardImpl extends Clipboard {

        /**
         * Used to get string from clipboard.
         *
         * @return string from clipboard.
         */
        @Override
        public String getClipboardString() {
            return GLFW.glfwGetClipboardString(-1);
        }

        /**
         * Used to set string to clipboard.
         *
         * @param string string to set to clipboard.
         */
        @Override
        public void setClipboardString(String string) {
            GLFW.glfwSetClipboardString(-1, string);
        }
    }
}
