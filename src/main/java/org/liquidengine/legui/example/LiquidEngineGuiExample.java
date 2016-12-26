package org.liquidengine.legui.example;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.event.component.KeyboardKeyEvent;
import org.liquidengine.legui.listener.component.KeyboardKeyEventListener;
import org.liquidengine.legui.util.ColorConstants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

/**
 * Created by Alexander on 14.10.2016.
 */
public class LiquidEngineGuiExample extends Demo {


    private static TextInput legui;
    private static Label     debug;
    private static Vector4f second = ColorConstants.red();
    private static Vector4f first  = ColorConstants.darkBlue();
    private static GLFWWindowSizeCallbackI windowSizeCallbackI;


    public LiquidEngineGuiExample(int width, int height, String title, Frame frame, boolean resizable) {
        super(width, height, title, frame, resizable);
    }

    public static void main(String[] args) {
        int       width     = 400;
        int       height    = 150;
        Frame     frame     = new Frame(width, height);
        Component component = createGui(width, height);
        frame.addComponent(component);
        frame.getLeguiEventListeners().addListener(KeyboardKeyEvent.class, (KeyboardKeyEventListener) event -> {

        });
        debug = new Label(10, 10, 100, 20);
        debug.getTextState().setPadding(10, 5, 10, 5);
        debug.getTextState().setTextColor(ColorConstants.white());
        debug.setBackgroundColor(ColorConstants.black());
        frame.addComponent(debug);

        LiquidEngineGuiExample example = new LiquidEngineGuiExample(width, height, "Liquid Engine GUI", frame, true);
        example.start();
        windowSizeCallbackI = (window, wid, hei) -> {
            legui.setSize(wid, hei);
            legui.getTextState().setFontSize(hei);
        };
        example.callbackKeeper.getChainWindowSizeCallback().add(windowSizeCallbackI);
        example.callbackKeeper.getChainKeyCallback().add((window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_RELEASE) {
                debug.setVisible(!debug.isVisible());
            }
        });
    }

    private static Component createGui(int width, int height) {
        legui = new TextInput(0, 0, width, height);
        legui.setBackgroundColor(ColorConstants.transparent());
        legui.getTextState().setTextColor(ColorConstants.darkGray());
        legui.getTextState().setText("LE GUI");
        legui.getTextState().setFontSize(height);
        legui.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        legui.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        legui.setSelectionColor(ColorConstants.lightBlue());
        legui.getLeguiEventListeners().addListener(KeyboardKeyEvent.class, (KeyboardKeyEventListener) event -> {
            if (event.getKey() == GLFW.GLFW_KEY_F1 && event.getAction() == GLFW.GLFW_RELEASE)
                legui.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
            else if (event.getKey() == GLFW.GLFW_KEY_F2 && event.getAction() == GLFW.GLFW_RELEASE)
                legui.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
            else if (event.getKey() == GLFW.GLFW_KEY_F3 && event.getAction() == GLFW.GLFW_RELEASE)
                legui.getTextState().setHorizontalAlign(HorizontalAlign.RIGHT);
        });
        return legui;
    }

    @Override
    public void update() {
        legui.getTextState().setTextColor(new Vector4f(second).sub(first).mul((float) ((Math.sin(GLFW.glfwGetTime()) + 1) / 2f)).add(first));
        debug.getSize().x = debug.getParent().getSize().x - 20;
        debug.getTextState().setText("" +
                "mcp : " + legui.getMouseCaretPosition() +
                "");
    }
}

