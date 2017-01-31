package org.liquidengine.legui.example;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;

import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by ShchAlexander on 27.01.2017.
 */
public class TestGlfw {
    public static void main(String[] args) {
        if (GLFW.glfwInit()) {
            GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint());
            long window = GLFW.glfwCreateWindow(100, 100, "Hello", NULL, NULL);
            while (!GLFW.glfwWindowShouldClose(window)) {
                GLFW.glfwPollEvents();
                GLFW.glfwSwapBuffers(window);
            }
            GLFW.glfwDestroyWindow(window);
            GLFW.glfwTerminate();
        }
    }
}
